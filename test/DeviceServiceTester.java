

import static org.junit.Assert.*;

import java.net.InetAddress;
import java.net.InetSocketAddress;
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

import ch.hsr.smartmanager.data.Device;
import ch.hsr.smartmanager.data.repositories.DeviceRepository;
import ch.hsr.smartmanager.service.applicationservices.DeviceService;

@WebAppConfiguration
@ActiveProfiles("dev")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:WebContent/WEB-INF/smartmanager-servlet.xml" })
public class DeviceServiceTester {

	@Autowired
	DeviceService deviceService;
	
	@Autowired
	DeviceRepository deviceRepository;

	Device device;
	Registration registration;

	@Before
	public void setUp() throws Exception {
		InetAddress addr = InetAddress.getByName("127.0.0.1");
		registration = new Registration.Builder("wef", "127.0.0.1", addr, 2131, new InetSocketAddress(112)).build();
		device = new Device("Tester", registration.getId(), "", new TreeSet<String>(), registration.getLastUpdate(),
				false);
	}

	@After
	public void tearDown() throws Exception {
		deviceRepository.deleteAll();
	}

	@Test
	public void countDiscoveredDevices() {
		assertEquals(0, deviceService.countDiscoveredDevices());
		deviceService.createOrUpdateDevice(device, registration);
		assertEquals(1, deviceService.countDiscoveredDevices());
	}

	@Test
	public void countAddedDevices() {
		device = deviceService.createOrUpdateDevice(device, registration);
        String[] deviceIDs = new String[]{device.getId()};
		deviceService.addToManagement(deviceIDs,"_unassigned", "none");
		assertEquals(1, deviceService.countAllDevices());
	}

}
