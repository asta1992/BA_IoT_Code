package ch.hsr.smartmanager.service.lwm2mservices;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import org.eclipse.leshan.core.model.ObjectLoader;
import org.eclipse.leshan.core.model.ObjectModel;
import org.eclipse.leshan.core.node.codec.DefaultLwM2mNodeDecoder;
import org.eclipse.leshan.core.node.codec.DefaultLwM2mNodeEncoder;
import org.eclipse.leshan.core.node.codec.LwM2mNodeDecoder;
import org.eclipse.leshan.server.californium.LeshanServerBuilder;
import org.eclipse.leshan.server.californium.impl.LeshanServer;
import org.eclipse.leshan.server.model.LwM2mModelProvider;
import org.eclipse.leshan.server.model.StaticModelProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class LwM2MManagementServer {

	@Autowired
	private RegistrationListenerImpl registrationListenerImpl;
	@Autowired
	private ServerTaskExecutor serverTaskExecutor;

	private final String ADDRESS = "127.0.0.1";
	private final int PORT = 5683;
	
	private LeshanServer server;
	private List<ObjectModel> models = ObjectLoader.loadDefault();
	private Resource resource = new ClassPathResource("ch/hsr/smartmanager/resources/models/");
	
	public LwM2MManagementServer() {}

	@PostConstruct
	public void createServer() {
		LeshanServerBuilder builder = new LeshanServerBuilder();

		builder.setLocalAddress(ADDRESS, PORT);
		
		builder.setEncoder(new DefaultLwM2mNodeEncoder());
		LwM2mNodeDecoder decoder = new DefaultLwM2mNodeDecoder();
		builder.setDecoder(decoder);

		File file;
		try {
			file = resource.getFile();
		} catch (IOException e) {
			file = null;
			e.printStackTrace();
		}

		models.addAll(ObjectLoader.load(file));
		LwM2mModelProvider modelProvider = new StaticModelProvider(models);
		builder.setObjectModelProvider(modelProvider);

		this.server = builder.build();
		
		server.getRegistrationService().addListener(registrationListenerImpl.getRegistrationListener());
		serverTaskExecutor.doIt(this.server);
	}

	public LeshanServer getServer() {
		return server;
	}

	public List<ObjectModel> getModels() {
		return models;
	}
}
