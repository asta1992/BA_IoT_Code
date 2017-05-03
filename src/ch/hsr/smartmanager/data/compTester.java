package ch.hsr.smartmanager.data;

public class compTester {

	public static void main(String[] args) {
		DeviceComponent dev1 = new Device("dev1");
		DeviceComponent dev2 = new Device("dev2");

		DeviceComponent group1 = new DeviceGroup("group1");
		
		DeviceComponent group2 = new DeviceGroup("group2");

		
		group1.add(dev1);
		group1.add(dev2);

		group2.add(dev2);
		
		group1.add(group2);
		
		group2.add(group1);
		
		
		group1.print("");

	}

}
