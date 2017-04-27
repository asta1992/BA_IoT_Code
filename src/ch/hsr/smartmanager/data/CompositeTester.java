package ch.hsr.smartmanager.data;

public class CompositeTester {

	public static void main(String[] args) {
		DeviceComponent device1 = new Device("Dev1");
		DeviceComponent device2 = new Device("Dev2");
		DeviceComponent group1 = new DeviceGroup("Group1");
		group1.add(device1);
		group1.add(device2);
		
		DeviceComponent device3=new Device("device3");
		DeviceGroup group2=new DeviceGroup("group2");
		
		DeviceGroup group3 =new DeviceGroup("group4");
		
		group2.add(device3);
		group2.add(group1);
		group3.add(device2);

	}

}
