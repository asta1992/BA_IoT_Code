package ch.hsr.smartmanager.service.applicationservices;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ch.hsr.smartmanager.data.DeviceGroup;
import ch.hsr.smartmanager.data.ManagementUser;
import ch.hsr.smartmanager.data.repositories.DeviceGroupRepository;
import ch.hsr.smartmanager.data.repositories.DeviceRepository;
import ch.hsr.smartmanager.data.repositories.ManagementUserRepository;

@Service
public class InfrastructureService {

	@Autowired
	private DeviceRepository deviceRepo;
	@Autowired
	private DeviceGroupRepository groupRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private ManagementUserRepository managementUserRepository;

	@PostConstruct
	public void startUpClean() {
		if (!groupRepo.existsByName("_unassigned")) {
			DeviceGroup unassigned = new DeviceGroup("_unassigned");
			groupRepo.save(unassigned);
		}
		if (!managementUserRepository.existsByUsername("admin")) {
			ManagementUser admin = new ManagementUser("admin", passwordEncoder.encode("adminadmin"));
			managementUserRepository.save(admin);
		}

		deviceRepo.removeDeviceByAddedIsFalse();
	}

	public long getServerUptime() {
		RuntimeMXBean rb = ManagementFactory.getRuntimeMXBean();
		return rb.getUptime() / 1000 / 3600;
	}

}
