package ch.hsr.smartmanager.service;

public interface IConnector {
	public String read(String endpoint);
	public String write(String endpoint);

}
