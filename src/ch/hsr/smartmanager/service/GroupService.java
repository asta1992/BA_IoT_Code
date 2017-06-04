package ch.hsr.smartmanager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import ch.hsr.smartmanager.data.Device;
import ch.hsr.smartmanager.data.DeviceComponent;
import ch.hsr.smartmanager.data.DeviceGroup;
import ch.hsr.smartmanager.data.repository.DeviceGroupRepository;
import ch.hsr.smartmanager.data.repository.DeviceRepository;

@Service
public class GroupService {
	
	
	@Autowired
	private DeviceRepository deviceRepo;
	@Autowired
	private DeviceGroupRepository groupRepo;
	@Autowired
	private DeviceService deviceService;
	
	public void addDeviceToGroup(String groupId, String deviceId) {
		DeviceGroup group = groupRepo.findOne(groupId);
		Device device = deviceRepo.findOne(deviceId);
		group.add(device);
		groupRepo.save(group);
	}
	
	public void addGroupToGroup(String parent, String child) {
		DeviceGroup grpParent = groupRepo.findOne(parent);
		DeviceGroup grpChild = groupRepo.findOne(child);

		if (grpChild.getName().equals("_unassigned") || grpParent.getName().equals("_unassigned")) {
			return;
		}

		if (isAncestors(grpParent.getName(), grpChild.getName())
				|| isAncestors(grpChild.getName(), grpParent.getName())) {
			return;
		}

		if (!groupRepo.existsByChildrenId(new ObjectId(grpChild.getId()))) {
			grpParent.add(grpChild);
			groupRepo.save(grpParent);
			groupRepo.save(grpChild);
		} else {
			List<DeviceGroup> group = groupRepo.findAllByChildrenId(new ObjectId(grpChild.getId()));
			for (DeviceGroup grp : group) {
				grp.remove(grpChild);
				groupRepo.save(grp);
			}
			grpParent.add(grpChild);
			groupRepo.save(grpParent);
			groupRepo.save(grpChild);
		}

	}
	
	public DeviceGroup findByName(String name) {
		return groupRepo.findByName(name);
	}
	
	public DeviceGroup getGroup(String id) {
		return groupRepo.findOne(id);
	}

	public List<DeviceGroup> getAllGroups() {
		return groupRepo.findAll();
	}

	public boolean isRoot(String id) {
		return groupRepo.existsByChildrenId(new ObjectId(id));
	}

	public DeviceGroup insertGroup(String groupName) {
		if(validateGroupname(groupName)) {
			System.out.println("Hier");
			return null;
		}
		DeviceGroup group = new DeviceGroup(HtmlUtils.htmlEscape(groupName));
		if (groupRepo.existsByName(group.getName())) {
			return groupRepo.findByName(group.getName());
		} else {
			return groupRepo.insert(group);
		}
	}


	
	public boolean deleteGroup(String id) {
		DeviceGroup group = groupRepo.findOne(id);
		if (!group.getChildren().isEmpty() || group.getName().equals("_unassigned")) {
			return false;
		}

		List<DeviceGroup> parents = groupRepo.findAllByChildrenId(new ObjectId(id));
		for (DeviceGroup parent : parents) {
			removeGroupFromGroup(parent.getId(), group.getId());
		}

		groupRepo.delete(id);
		return true;
	}
	
	private boolean validateGroupname(String groupname) {
		final String regex = "(?=^.{1,30}$)[a-zA-Z0-9_.-]*$";
		final Pattern pattern = Pattern.compile(regex);
		final Matcher matcher = pattern.matcher(groupname);
		while (matcher.find()) {
			return false;
		}
		return true;
	}
	
	public void removeDeviceFromGroup(String groupId, String deviceId) {
		DeviceGroup group = groupRepo.findOne(groupId);
		Device device = deviceRepo.findOne(deviceId);
		if (group == null || device == null)
			return;
		group.remove(device);
		groupRepo.save(group);
		deviceRepo.save(device);
	}

	public void removeGroupFromGroup(String parent, String child) {
		DeviceGroup grpParent = groupRepo.findOne(parent);
		DeviceGroup grpChild = groupRepo.findOne(child);
		if (grpParent == null || grpChild == null)
			return;
		grpParent.remove(grpChild);
		groupRepo.save(grpParent);
		groupRepo.save(grpChild);
	}
	
	private boolean isAncestors(String parent, String child) {
		List<String> anchestors = groupRepo.findAllAncestors(child);
		if (anchestors.contains(parent))
			return true;
		return false;
	}
	

	public List<Device> findAllChildren(String id) {
		List<Device> allSubDevices = new ArrayList<>();
		DeviceGroup mainGroup = groupRepo.findOne(id);
		List<String> childrenGroup = groupRepo.findAllChildren(mainGroup.getName());
		childrenGroup.add(mainGroup.getName());
		for (String name : childrenGroup) {
			DeviceGroup group = groupRepo.findByName(name);
			for (DeviceComponent dev : group.getChildren()) {
				if (dev instanceof Device)
					allSubDevices.add((Device) dev);
			}
		}
		return allSubDevices;
	}
	
	public List<DeviceComponent> getAllComponents() {
		List<DeviceComponent> allComponents = new ArrayList<DeviceComponent>();
		for (DeviceComponent component : deviceService.getAllDevices()) {
			allComponents.add(component);
		}
		for (DeviceComponent component : getAllGroups()) {
			allComponents.add(component);
		}

		return allComponents;
	}
	
	public List<DeviceGroup> listAllGroupsForGroup(String id) {
		return groupRepo.findAllByChildrenId(new ObjectId(id));
	}

}
