package ch.hsr.smartmanager.presentation.controller.webcontroller.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.security.Principal;
import java.util.TreeSet;

import org.eclipse.leshan.server.registration.Registration;
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

import ch.hsr.smartmanager.data.Device;
import ch.hsr.smartmanager.data.DeviceGroup;
import ch.hsr.smartmanager.data.repositories.DeviceGroupRepository;
import ch.hsr.smartmanager.data.repositories.DeviceRepository;
import ch.hsr.smartmanager.presentation.webcontroller.DeviceWebController;
import ch.hsr.smartmanager.service.applicationservices.DeviceService;
import ch.hsr.smartmanager.service.applicationservices.GroupService;

@WebAppConfiguration
@ActiveProfiles("dev")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:WebContent/WEB-INF/smartmanager-servlet.xml" })
public class DeviceWebControllerTest {

	@Autowired
	private DeviceWebController deviceWebController;
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

		groupService.addNewRootGroup("Test");
		group = deviceGroupRepository.findByName("Test");

		InetAddress addr = InetAddress.getByName("127.0.0.1");
		registration = new Registration.Builder("wef", "127.0.0.1", addr, 2131, new InetSocketAddress(112)).build();
		device = new Device("Tester", registration.getId(), "", new TreeSet<String>(), registration.getLastUpdate(),
				false);
		device = deviceService.createOrUpdateDevice(device, registration);
		String[] deviceIds = { device.getId() };
		deviceService.addToManagement(deviceIds, group.getId(), "none");

		principal = new Principal() {
			@Override
			public String getName() {
				return "admin";
			}
		};

		mockMvc = MockMvcBuilders.standaloneSetup(deviceWebController).build();
	}

	@After
	public void tearDown() {
		deviceRepository.deleteAll();
	}

	@Test
	public void devices() throws Exception {
		mockMvc.perform(get("/devices").principal(principal)).andExpect(status().isOk());
	}
	
	@Test
	public void removeNullDevices() throws Exception {
		groupService.addDeviceToGroup(group.getId(), device.getId());
		deviceRepository.delete(device);
		mockMvc.perform(get("/devices/" + device.getId()).principal(principal)).andExpect(status().isOk());
	}

	@Test
	public void devicedetails() throws Exception {
		mockMvc.perform(get("/devices/" + device.getId()).principal(principal)).andExpect(status().isOk());
	}

	@Test
	public void devicedetailserror() throws Exception {
		mockMvc.perform(get("/devices/59417fcd3f56e97dd75cdcd4").principal(principal)).andExpect(status().isOk())
				.andExpect(view().name("devices/deviceError"));
	}

	@Test
	public void memberships() throws Exception {
		mockMvc.perform(get("/devices/" + device.getId() + "/memberships").principal(principal))
				.andExpect(status().isOk());
	}

}
