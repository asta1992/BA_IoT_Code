package ch.hsr.smartmanager.presentation.controller.restcontroller.test;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.security.Principal;
import java.util.Date;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ch.hsr.smartmanager.data.Device;
import ch.hsr.smartmanager.data.DeviceGroup;
import ch.hsr.smartmanager.data.repositories.DeviceGroupRepository;
import ch.hsr.smartmanager.data.repositories.DeviceRepository;
import ch.hsr.smartmanager.presentation.restcontroller.DeviceRestController;
import ch.hsr.smartmanager.service.applicationservices.DeviceService;
import ch.hsr.smartmanager.service.applicationservices.GroupService;

@WebAppConfiguration
@ActiveProfiles("dev")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:WebContent/WEB-INF/smartmanager-servlet.xml" })
public class DeviceRestControllerTest {

	@Autowired
	private DeviceRestController deviceRestController;
	private MockMvc mockMvc;
	private Principal principal;
	@Autowired
	private GroupService groupService;
	@Autowired
	private DeviceService deviceService;
	@Autowired
	private DeviceGroupRepository deviceGroupRepository;
	@Autowired
	private DeviceRepository deviceRepository;

	private DeviceGroup group;
	private Device device;

	Registration registration;

	@Before
	public void setUp() throws UnknownHostException {

		groupService.addNewRootGroup("_unassigned");

		groupService.addNewRootGroup("Test");
		group = deviceGroupRepository.findByName("Test");

		InetAddress addr = InetAddress.getByName("127.0.0.1");
		registration = new Registration.Builder("wef", "127.0.0.1", addr, 2131, new InetSocketAddress(112)).build();
		device = new Device("Tester", registration.getId(), "", new TreeSet<String>(), registration.getLastUpdate(),
				false);
		this.device = deviceRepository.insert(device);

		principal = new Principal() {
			@Override
			public String getName() {
				return "admin";
			}
		};

		mockMvc = MockMvcBuilders.standaloneSetup(deviceRestController).build();
	}

	@After
	public void tearDown() {
		deviceRepository.deleteAll();
		deviceGroupRepository.deleteAll();

	}

	@Test
	public void addDeviceToManagement() throws Exception {
		String[] deviceIds = { device.getId() };

		assertEquals(1, deviceService.getAllDiscoveredDevice().size());

		mockMvc.perform(post("/devices/add").principal(principal).accept(MediaType.APPLICATION_JSON)
				.param("deviceIds[]", deviceIds).param("configId", "none").param("groupId", "_unassigned"));

		assertEquals(0, deviceService.getAllDiscoveredDevice().size());
		assertEquals(true, deviceService.getDevice(device.getId()).isAdded());
	}

	@Test
	public void deleteDeviceToManagement() throws Exception {
		assertEquals(1, deviceService.getAllDevices().size());

		mockMvc.perform(delete("/devices/" + device.getId() + "/delete").principal(principal)
				.accept(MediaType.APPLICATION_JSON));
		assertEquals(0, deviceService.getAllDevices().size());
		assertNull(deviceRepository.findOne(device.getId()));

	}

	@Test
	public void deleteAllUnreachableDevices() throws Exception {

		device.setLastRegistrationUpdate(new Date(1));
		deviceRepository.save(device);
		assertEquals(1, deviceService.getUnreachableDevices().size());

		mockMvc.perform(delete("/devices/deleteAll").principal(principal).accept(MediaType.APPLICATION_JSON));
		assertEquals(0, deviceService.getUnreachableDevices().size());

	}

	@Test
	public void changeMembershipAdd() throws Exception {
		JSONArray jsonArray = new JSONArray();
		jsonArray.put(group.getId());

		mockMvc.perform(post("/devices/" + device.getId() + "/changeMembership").principal(principal)
				.param("value", jsonArray.toString()).accept(MediaType.APPLICATION_JSON));

		assertTrue(deviceGroupRepository.findOne(group.getId()).getChildren().contains(device));

	}

	@Test
	public void changeMembershipRemove() throws Exception {
		JSONArray firstArray = new JSONArray();
		firstArray.put(group.getId());

		mockMvc.perform(post("/devices/" + device.getId() + "/changeMembership").principal(principal)
				.param("value", firstArray.toString()).accept(MediaType.APPLICATION_JSON));

		assertFalse(deviceGroupRepository.findByName("_unassigned").getChildren().contains(device));

		JSONArray secondArray = new JSONArray();

		mockMvc.perform(post("/devices/" + device.getId() + "/changeMembership").principal(principal)
				.param("value", secondArray.toString()).accept(MediaType.APPLICATION_JSON));

		assertFalse(deviceGroupRepository.findOne(group.getId()).getChildren().contains(device));
		assertTrue(deviceGroupRepository.findByName("_unassigned").getChildren().contains(device));

	}

	@Test
	public void addDeviceTwice() throws Exception {
		deviceService.createOrUpdateDevice(device, registration);
		deviceService.createOrUpdateDevice(device, registration);
		assertEquals(1, deviceService.getAllDevices().size());

	}

}
