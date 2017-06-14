package ch.hsr.smartmanager.presentation.controller.restcontroller.test;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.security.Principal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ch.hsr.smartmanager.data.DeviceGroup;
import ch.hsr.smartmanager.data.repositories.DeviceGroupRepository;
import ch.hsr.smartmanager.presentation.restcontroller.GroupRestController;
import ch.hsr.smartmanager.service.applicationservices.GroupService;

@WebAppConfiguration
@ActiveProfiles("dev")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:WebContent/WEB-INF/smartmanager-servlet.xml" })
public class GroupRestControllerTest {

	@Autowired
	private GroupRestController groupRestController;
	@Autowired
	private DeviceGroupRepository deviceGroupRepository;
	@Autowired
	private GroupService groupService;
	private MockMvc mockMvc;
	private Principal principal;
	private DeviceGroup group1;
	private DeviceGroup subgroup1;
	private DeviceGroup subgroup2;


	@Before
	public void setUp() throws Exception {

		groupService.addNewRootGroup("group1");
		group1 = deviceGroupRepository.findByName("group1");

		groupService.addNewChildGroup(group1.getId(), "subgroup1");
		groupService.addNewChildGroup(group1.getId(), "subgroup2");
		subgroup1 = deviceGroupRepository.findByName("subgroup1");
		subgroup2 = deviceGroupRepository.findByName("subgroup2");
		

		principal = new Principal() {
			@Override
			public String getName() {
				return "admin";
			}
		};

		this.mockMvc = MockMvcBuilders.standaloneSetup(groupRestController).build();
	}

	@After
	public void tearDown() throws Exception {
		deviceGroupRepository.deleteAll();
	}

	@Test
	public void addRootGroupPositiv() throws Exception {
		MvcResult result = mockMvc.perform(
				post("/groups/add").principal(principal).accept(MediaType.APPLICATION_JSON).param("value", "testGroup"))
				.andReturn();
		assertEquals("true", result.getResponse().getContentAsString());
	}

	@Test
	public void addRootGroupNegativ() throws Exception {
		MvcResult result = mockMvc.perform(post("/groups/add").principal(principal).accept(MediaType.APPLICATION_JSON)
				.param("value", "<>BadGroup")).andReturn();
		assertEquals("false", result.getResponse().getContentAsString());
	}
 
	@Test
	public void listAllGroups() throws Exception {
		mockMvc.perform(get("/groups/list").principal(principal).accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].name", is("_unassigned")))
				.andExpect(jsonPath("$[1].name", is("group1")))
                .andExpect(jsonPath("$[2].name", is("subgroup1")));
	}
	
	@Test
	public void listAllGroupsAsJSON() throws Exception {
		mockMvc.perform(get("/groups/getAll").principal(principal).accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].text", is("group1")))
                .andExpect(jsonPath("$[0].children[*].text", hasItem("subgroup1")))
                .andExpect(jsonPath("$[0].children[*].text", hasItem("subgroup2")));
	}


}
