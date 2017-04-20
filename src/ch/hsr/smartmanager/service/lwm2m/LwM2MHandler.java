package ch.hsr.smartmanager.service.lwm2m;

import org.eclipse.leshan.Link;
import org.eclipse.leshan.core.node.LwM2mSingleResource;
import org.eclipse.leshan.core.request.DiscoverRequest;
import org.eclipse.leshan.core.request.ReadRequest;
import org.eclipse.leshan.core.response.DiscoverResponse;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.server.californium.impl.LeshanServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.hsr.smartmanager.service.IHandler;

@Service
public class LwM2MHandler implements IHandler{

	
	@Autowired
	private LwM2MManagementServer lwM2MManagementServer;
	
	private LeshanServer server;
	
	public LwM2MHandler() {}
	
	
	public ReadResponse read(String path, String id) {
		ReadRequest req = new ReadRequest(path);
		ReadResponse res;
		server = lwM2MManagementServer.getServer();

		try {
			res = server.send(server.getRegistrationService().getById(id), req);
		} catch (InterruptedException e) {
			res = null;
			e.printStackTrace();
		}
		return res;
	}
	
	@Override
	public String read() {
		return null;
	}

	@Override
	public String write(String body) {
		return null;
	}
	
	public Link[] discovery(String path, String id) {
		DiscoverRequest disReq = new DiscoverRequest(path);
		DiscoverResponse res;
		server = lwM2MManagementServer.getServer();

		try {
			res = server.send(server.getRegistrationService().getById(id), disReq);
		} catch (InterruptedException e) {
			res = null;
			e.printStackTrace();
		}
		return res.getObjectLinks();
		
	}
	public String execute(String body) {
		return null;
	}
}
