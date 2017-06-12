package ch.hsr.smartmanager.service.applicationservices;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.hsr.smartmanager.data.Device;
import ch.hsr.smartmanager.data.DeviceGroup;

@Service
public class LocationService {

	@Autowired
	private DeviceService deviceService;
	@Autowired
	private GroupService groupService;

	public List<List<String>> getAllLocation() {
		return getLocationMap(deviceService.getAllDevices());
	}

	public List<List<String>> getDeviceLocationById(Device device) {
		List<Device> deviceLocations = new ArrayList<Device>();
		deviceLocations.add(deviceService.getDevice(device.getId()));
		return getLocationMap(deviceLocations);
	}

	public List<List<String>> getAllLocationByGroup(DeviceGroup group) {
		return getLocationMap(groupService.findAllChildren(group.getId()));
	}

	private List<List<String>> getLocationMap(List<Device> devices) {
		List<List<String>> list = new ArrayList<>();

		for (Device device : devices) {
			List<String> devValue = new ArrayList<>();

			devValue.add(device.getName());
			devValue.add(device.getLatitude());
			devValue.add(device.getLongitude());
			devValue.add("0");

			list.add(devValue);
		}
		return list;
	}

}
