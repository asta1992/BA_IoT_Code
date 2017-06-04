package ch.hsr.smartmanager.service;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.hsr.smartmanager.data.DeviceGroup;
import ch.hsr.smartmanager.data.repository.DeviceGroupRepository;
import ch.hsr.smartmanager.data.repository.DeviceRepository;

@Service
public class InfrastructureService {
	
	@Autowired
	private DeviceRepository deviceRepo;
	@Autowired
	private DeviceGroupRepository groupRepo;
	
	@PostConstruct
	public void startUpClean() {
		if (!groupRepo.existsByName("_unassigned")) {
			DeviceGroup unassigned = new DeviceGroup("_unassigned");
			groupRepo.save(unassigned);
		}

		deviceRepo.removeDeviceByAddedIsFalse();
	}
	public long getServerUptime() {
		RuntimeMXBean rb = ManagementFactory.getRuntimeMXBean();
		return rb.getUptime() / 1000 / 3600;
	}
	


}
