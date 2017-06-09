package ch.hsr.smartmanager.service.applicationservices;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bson.types.ObjectId;
import org.eclipse.leshan.ResponseCode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import ch.hsr.smartmanager.data.Device;
import ch.hsr.smartmanager.data.DeviceComponent;
import ch.hsr.smartmanager.data.DeviceGroup;
import ch.hsr.smartmanager.data.repositories.DeviceGroupRepository;
import ch.hsr.smartmanager.data.repositories.DeviceRepository;

@Service
public class GroupService {
	
	
	@Autowired
	private DeviceRepository deviceRepo;
	@Autowired
	private DeviceGroupRepository groupRepo;
	@Autowired
	private DeviceService deviceService;
	
	
	
	
	
	
	
	public Map<String, List<Map<String, ResponseCode>>> executeToAllChildren(String id, int objectId, int objectInstanceId, int resourceId) {
		List<Device> devices = findAllChildren(id);
		return deviceService.executeToAllChildren(devices, objectId, objectInstanceId, resourceId);
	}

	public Map<String, List<Map<String, ResponseCode>>> writeToAllChildren(String id, int objectId, int objectInstanceId, int resourceId, String value) {
		List<Device> devices = findAllChildren(id);
		return deviceService.writeToAllChildren(devices, objectId, objectInstanceId, resourceId, value);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void addDeviceToGroup(String groupId, String deviceId) {
		DeviceGroup group = groupRepo.findOne(groupId);
		Device device = deviceRepo.findOne(deviceId);
		group.add(device);
		groupRepo.save(group);
	}
	
	
	private void addGroupToGroup(String parent, String child) {
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

	private boolean isRoot(String id) {
		return groupRepo.existsByChildrenId(new ObjectId(id));
	}

	private DeviceGroup insertGroup(String groupName) {
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

	private void removeGroupFromGroup(String parent, String child) {
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

	public void removeDeviceFromGroups(List<String> value, String id) {
		for (String groupId : value) {
			removeDeviceFromGroup(groupId, id);			
		}
	}

	public boolean addNewRootGroup(String groupName) {
		return insertGroup(groupName) != null;
	}

	public boolean addNewChildGroup(String id, String groupName) {
		DeviceGroup devGroup = insertGroup(groupName);
		if (devGroup != null) {
			addGroupToGroup(id, devGroup.getId());
			return true;
		} 
		return false;
	}

	public void changeMembership(String id, JSONArray value) {
		if (value.length() == 1) {
			try {
				DeviceGroup newParentGroup = getGroup(value.getString(0));
				DeviceGroup group = getGroup(id);

				List<DeviceGroup> oldParentGroups = listAllGroupsForGroup(id);

				if (!oldParentGroups.isEmpty()) {
					removeGroupFromGroup(oldParentGroups.get(0).getId(), group.getId());
				}
				addGroupToGroup(newParentGroup.getId(), group.getId());

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		if (value.length() == 0) {
			DeviceGroup group = getGroup(id);
			List<DeviceGroup> subGroups = listAllGroupsForGroup(id);
			if (subGroups.isEmpty()) {
				return;
			}
			DeviceGroup oldParentGroup = subGroups.get(0);
			removeGroupFromGroup(oldParentGroup.getId(), group.getId());
		}
		
	}

	public void changeMembers(String id, JSONArray value) {
		Set<Device> editedDevices = new HashSet<>();

		List<DeviceComponent> postMembers = new ArrayList<>();
		for (int i = 0; i < value.length(); i++) {
			try {

				DeviceComponent item = getGroup((value.getString(i)));
				if (item == null) {
					item = deviceService.getDevice((value.getString(i)));
				}
				postMembers.add(item);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		postMembers.removeAll(Collections.singleton(null));

		DeviceGroup group = getGroup(id);
		List<DeviceComponent> preMembers = group.getChildren();

		for (DeviceComponent comp : preMembers) {
			if (!postMembers.contains(comp)) {
				if (comp instanceof Device) {
					editedDevices.add((Device) comp);
					removeDeviceFromGroup(group.getId(), comp.getId());
				}
				if (comp instanceof DeviceGroup) {
					removeGroupFromGroup(group.getId(), comp.getId());
				}
			}
		}

		for (DeviceComponent comp : postMembers) {
			if (!preMembers.contains(comp)) {
				if (comp instanceof Device) {
					editedDevices.add((Device) comp);
					addDeviceToGroup(group.getId(), comp.getId());
				}
				if (comp instanceof DeviceGroup) {
					addGroupToGroup(group.getId(), comp.getId());
				}
			}
		}

		DeviceGroup devGroup = findByName("_unassigned");

		for (Device device : editedDevices) {
			List<DeviceGroup> deviceGroups = listAllGroupsForGroup(device.getId());
			if (deviceGroups.isEmpty()) {
				addDeviceToGroup(devGroup.getId(), device.getId());
			} else {
				removeDeviceFromGroup(devGroup.getId(), device.getId());
			}
		}
	}

	public String getAllGroupsAsJSON() {
		List<JSONObject> allJson = new ArrayList<>();

		for (DeviceComponent item : getAllGroups()) {
			if (!isRoot(item.getId())) {
				JSONObject jsonObj = new JSONObject();
				try {
					jsonObj.put("id", "groups/" + item.getId());
					jsonObj.put("text", item.getName());
					jsonObj.put("children", deviceService.allChildren(item));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				allJson.add(jsonObj);
			}
		}

		return allJson.toString();
	}

}
