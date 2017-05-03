package ch.hsr.smartmanager.data;

public interface DeviceComponent {
	
	public void add(DeviceComponent deviceComponent);
	public void remove(DeviceComponent deviceComponent);
	public String getName();
	public void print(String abstand);
	public void addParent(DeviceComponent deviceComponent);
	public void removeParent(DeviceComponent deviceComponent);

}
