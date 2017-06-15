package ch.hsr.smartmanager.presentation.controller.restcontroller.test;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.security.Principal;
import java.util.TreeSet;

import org.eclipse.leshan.server.registration.Registration;
import org.json.JSONArray;
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

import ch.hsr.smartmanager.data.Device;
import ch.hsr.smartmanager.data.DeviceGroup;
import ch.hsr.smartmanager.data.repositories.DeviceGroupRepository;
import ch.hsr.smartmanager.data.repositories.DeviceRepository;
import ch.hsr.smartmanager.presentation.restcontroller.GroupRestController;
import ch.hsr.smartmanager.service.applicationservices.DeviceService;
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
	private DeviceRepository deviceRepository;

	@Autowired
	private GroupService groupService;
	@Autowired
	private DeviceService deviceService;
	
	private MockMvc mockMvc;
	private Principal principal;
	private DeviceGroup group1;
	private DeviceGroup subgroup1;
	private DeviceGroup subgroup2;
	

	@Before
	public void setUp() throws Exception {

		groupService.addNewRootGroup("group1");
		groupService.addNewRootGroup("_unassigned");

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
		deviceRepository.deleteAll();

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
				.andExpect(jsonPath("$[*].name", hasItem("group1")))
				.andExpect(jsonPath("$[*].name", hasItem("subgroup1")))
				.andExpect(jsonPath("$[*].name", hasItem("subgroup2")));
	}

	@Test
	public void listAllGroupsAsJSON() throws Exception {
		Device device = createDevice();
		
		groupService.addDeviceToGroup(group1.getId(), device.getId());
		groupService.addDeviceToGroup(subgroup1.getId(), device.getId());

		mockMvc.perform(get("/groups/getAll").principal(principal).accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].text", is("group1")))
				.andExpect(jsonPath("$[0].children[*].text", hasItem("subgroup1")))
				.andExpect(jsonPath("$[0].children[*].text", hasItem("subgroup2")));

	}

	@Test
	public void addSubGroupPositiv() throws Exception {
		MvcResult result = mockMvc.perform(post("/groups/" + group1.getId() + "/add").principal(principal)
				.accept(MediaType.APPLICATION_JSON).param("value", "subgroupTest")).andReturn();
		assertEquals("true", result.getResponse().getContentAsString());
	}

	@Test
	public void addSubGroupNegativ() throws Exception {
		MvcResult result = mockMvc.perform(post("/groups/" + group1.getId() + "/add").principal(principal)
				.accept(MediaType.APPLICATION_JSON).param("value", "<>BadSubgroupTest")).andReturn();
		assertEquals("false", result.getResponse().getContentAsString());
	}

	@Test
	public void deleteGroupPositiv() throws Exception {
		mockMvc.perform(delete("/groups/" + subgroup2.getId() + "/delete").principal(principal)
				.accept(MediaType.APPLICATION_JSON));
		assertEquals(3, groupService.getAllGroups().size());
	}

	@Test
	public void deleteGroupWithSubgroups() throws Exception {

		groupService.addDeviceToGroup(group1.getId(), subgroup1.getId());

		mockMvc.perform(delete("/groups/" + group1.getId() + "/delete").principal(principal)
				.accept(MediaType.APPLICATION_JSON));
		assertEquals(4, groupService.getAllGroups().size());
	}

	@Test
	public void deleteGroupWithDeviceAsChildren() throws Exception {
		Device device = createDevice();
		groupService.addDeviceToGroup(group1.getId(), device.getId());
		mockMvc.perform(delete("/groups/" + group1.getId() + "/delete").principal(principal)
				.accept(MediaType.APPLICATION_JSON));
		assertEquals(4, groupService.getAllGroups().size());
	}

	@Test
	public void changeMembershipForSubgroup2() throws Exception {
		JSONArray jsonArray = new JSONArray();
		jsonArray.put(subgroup1.getId());
		mockMvc.perform(post("/groups/" + subgroup2.getId() + "/changeMembership").principal(principal)
				.accept(MediaType.APPLICATION_JSON).param("value", jsonArray.toString()));
		assertEquals(true, groupService.getGroup(subgroup1.getId()).getChildren().contains(subgroup2));
	}

	@Test
	public void changeMembershipForSubgroup2ToRoot() throws Exception {
		JSONArray jsonArray = new JSONArray();
		mockMvc.perform(post("/groups/" + subgroup2.getId() + "/changeMembership").principal(principal)
				.accept(MediaType.APPLICATION_JSON).param("value", jsonArray.toString()));
		assertEquals(0, groupService.listAllGroupsForGroup(subgroup2.getId()).size());
	}

	@Test
	public void changeMemberForSubgroup1() throws Exception {
		JSONArray jsonArray = new JSONArray();
		jsonArray.put(subgroup2.getId());
		mockMvc.perform(post("/groups/" + subgroup1.getId() + "/changeMembers").principal(principal)
				.accept(MediaType.APPLICATION_JSON).param("value", jsonArray.toString()));

		assertEquals(true, groupService.getGroup(subgroup1.getId()).getChildren().contains(subgroup2));
	}

	@Test
	public void changeMemberForSubgroup1WithDevice() throws Exception {
		Device device = createDevice();
		JSONArray jsonArray = new JSONArray();
		jsonArray.put(subgroup2.getId());
		jsonArray.put(device.getId());

		mockMvc.perform(post("/groups/" + subgroup1.getId() + "/changeMembers").principal(principal)
				.accept(MediaType.APPLICATION_JSON).param("value", jsonArray.toString()));

		assertEquals(true, groupService.getGroup(subgroup1.getId()).getChildren().contains(subgroup2));
		assertEquals(true, groupService.getGroup(subgroup1.getId()).getChildren().contains(device));

	}

	@Test
	public void changeMemberForSubgroup1RemoveDevice() throws Exception {
		Device device = createDevice();
		JSONArray firstJson = new JSONArray();
		firstJson.put(subgroup2.getId());
		firstJson.put(device.getId());

		mockMvc.perform(post("/groups/" + subgroup1.getId() + "/changeMembers").principal(principal)
				.accept(MediaType.APPLICATION_JSON).param("value", firstJson.toString()));

		JSONArray secondJson = new JSONArray();
		secondJson.put(subgroup2.getId());

		mockMvc.perform(post("/groups/" + subgroup1.getId() + "/changeMembers").principal(principal)
				.accept(MediaType.APPLICATION_JSON).param("value", secondJson.toString()));

		assertEquals(true, groupService.getGroup(subgroup1.getId()).getChildren().contains(subgroup2));
		assertEquals(false, groupService.getGroup(subgroup1.getId()).getChildren().contains(device));
		assertEquals(true, (deviceGroupRepository.findByName("_unassigned")).getChildren().contains(device));
	}
	
	@Test
	public void changeMemberForSubgroup1RemoveGroup() throws Exception {
		JSONArray firstJson = new JSONArray();
		firstJson.put(subgroup2.getId());

		mockMvc.perform(post("/groups/" + subgroup1.getId() + "/changeMembers").principal(principal)
				.accept(MediaType.APPLICATION_JSON).param("value", firstJson.toString()));

		JSONArray secondJson = new JSONArray();

		mockMvc.perform(post("/groups/" + subgroup1.getId() + "/changeMembers").principal(principal)
				.accept(MediaType.APPLICATION_JSON).param("value", secondJson.toString()));

		assertEquals(false, groupService.getGroup(subgroup1.getId()).getChildren().contains(subgroup2));
	}

	private Device createDevice() throws UnknownHostException {
		InetAddress addr = InetAddress.getByName("127.0.0.1");
		Registration registration = new Registration.Builder("wef", "127.0.0.1", addr, 2131, new InetSocketAddress(112))
				.build();
		Device device = new Device("device1", registration.getId(), "", new TreeSet<String>(),
				registration.getLastUpdate(), false);
		device = deviceService.createOrUpdateDevice(device, registration);

		String[] deviceIds = { device.getId() };

		deviceService.addToManagement(deviceIds, "_unassigned", "none");
		return deviceService.getDevice(device.getId());
	}

}
