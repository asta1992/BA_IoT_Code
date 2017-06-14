package ch.hsr.smartmanager.presentation.controller.webcontroller.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.Principal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ch.hsr.smartmanager.data.DeviceGroup;
import ch.hsr.smartmanager.data.repositories.DeviceGroupRepository;
import ch.hsr.smartmanager.presentation.webcontroller.GroupWebController;
import ch.hsr.smartmanager.service.applicationservices.GroupService;

@WebAppConfiguration
@ActiveProfiles("dev")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:WebContent/WEB-INF/smartmanager-servlet.xml" })
public class GroupWebControllerTest {

	@Autowired
	private GroupWebController groupWebController;
	@Autowired
	private DeviceGroupRepository deviceGroupRepository;
	@Autowired
	private GroupService groupService;
	private MockMvc mockMvc;
	private Principal principal;
	private DeviceGroup group1;

	@Before
	public void setUp() throws Exception {

		groupService.addNewRootGroup("Test");
		group1 = deviceGroupRepository.findByName("Test");

		groupService.addNewChildGroup(group1.getId(), "Subgroup");

		principal = new Principal() {
			@Override
			public String getName() {
				return "admin";
			}
		};

		this.mockMvc = MockMvcBuilders.standaloneSetup(groupWebController).build();
	}

	@After
	public void tearDown() throws Exception {
		deviceGroupRepository.deleteAll();
	}

	@Test
	public void executeChildDevices() throws Exception {
		mockMvc.perform(get("/groups/" + group1.getId() + "/executeChildDevices/3/0/3").principal(principal))
				.andExpect(status().isOk());
	}

	@Test
	public void getMembership() throws Exception {
		mockMvc.perform(get("/groups/" + group1.getId() + "/memberships").principal(principal))
				.andExpect(status().isOk());
	}

	@Test
	public void getMembershipWithSubgroups() throws Exception {
		mockMvc.perform(get("/groups/" + group1.getId() + "/memberships").principal(principal))
				.andExpect(status().isOk());
	}

	@Test
	public void getMembers() throws Exception {
		mockMvc.perform(get("/groups/" + group1.getId() + "/members").principal(principal)).andExpect(status().isOk());
	}

	@Test
	public void executeCommandToChildsFragment() throws Exception {
		mockMvc.perform(get("/groups/executeCommandToChildsFragment").principal(principal)).andExpect(status().isOk());
	}

	@Test
	public void getGroup() throws Exception {
		mockMvc.perform(get("/groups/" + group1.getId()).principal(principal)).andExpect(status().isOk());
	}

	@Test
	public void writeChildDevices() throws Exception {
		mockMvc.perform(
				get("/groups/" + group1.getId() + "/writeChildDevices/1/2/3").principal(principal).param("value", ""))
				.andExpect(status().isOk());
	}

	@Test
	public void writeCommandToChildsFragment() throws Exception {
		mockMvc.perform(get("/groups/writeCommandToChildsFragment").principal(principal)).andExpect(status().isOk());
	}

	@Test
	public void writeConfigToChildsFragment() throws Exception {
		mockMvc.perform(get("/groups/writeConfigToChildsFragment").principal(principal)).andExpect(status().isOk());
	}

	@Test
	public void writeConfiguration() throws Exception {
		mockMvc.perform(
				get("/groups/" + group1.getId() + "/writeConfiguration").principal(principal).param("value", ""))
				.andExpect(status().isOk());
	}

}
