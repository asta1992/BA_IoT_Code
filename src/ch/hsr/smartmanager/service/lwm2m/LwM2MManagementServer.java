package ch.hsr.smartmanager.service.lwm2m;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.eclipse.leshan.core.model.ObjectLoader;
import org.eclipse.leshan.core.model.ObjectModel;
import org.eclipse.leshan.core.node.codec.DefaultLwM2mNodeDecoder;
import org.eclipse.leshan.core.node.codec.DefaultLwM2mNodeEncoder;
import org.eclipse.leshan.core.node.codec.LwM2mNodeDecoder;
import org.eclipse.leshan.core.observation.Observation;
import org.eclipse.leshan.server.californium.LeshanServerBuilder;
import org.eclipse.leshan.server.californium.impl.LeshanServer;
import org.eclipse.leshan.server.model.LwM2mModelProvider;
import org.eclipse.leshan.server.model.StaticModelProvider;
import org.eclipse.leshan.server.registration.Registration;
import org.eclipse.leshan.server.registration.RegistrationListener;
import org.eclipse.leshan.server.registration.RegistrationService;
import org.eclipse.leshan.server.registration.RegistrationUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import ch.hsr.smartmanager.data.AuthType;
import ch.hsr.smartmanager.data.Device;
import ch.hsr.smartmanager.data.ProtocolType;
import ch.hsr.smartmanager.service.DeviceService;

@Service
public class LwM2MManagementServer {

	@Autowired
	private DeviceService deviceService;

	@Autowired
	private ServerTaskExecutor serverTaskExecutor;
	private LeshanServer server;
	private String address;
	private int port;
	private RegistrationListener regListener;
	private boolean isStarted = false;

	public LwM2MManagementServer() {
		this.address = "localhost";
		this.port = 5683;
		
	}

	public LwM2MManagementServer(String address, int port) {
		this.address = address;
		this.port = port;
	}

	@PostConstruct
	public void createServer() {

		LeshanServerBuilder builder = new LeshanServerBuilder();
		builder.setLocalAddress(address, port);
		builder.setEncoder(new DefaultLwM2mNodeEncoder());
		LwM2mNodeDecoder decoder = new DefaultLwM2mNodeDecoder();
		builder.setDecoder(decoder);

		List<ObjectModel> models = ObjectLoader.loadDefault();
		Resource resource = new ClassPathResource("ch/hsr/smartmanager/resources/models/");
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
		RegistrationService regService = server.getRegistrationService();
		regListener = getRegistrationListener();
		regService.addListener(regListener);
		serverTaskExecutor.doIt(this.server);
	}

	
	public RegistrationListener getRegistrationListener() {

		return new RegistrationListener() {

			@Override
			public void registered(Registration registration) {
				System.out.println("New Device");
				System.out.println(registration);
				deviceService.getAllDevice();
				deviceService.createOrUpdateDevice(new Device(registration.getEndpoint(),registration.getId(), ProtocolType.LwM2M,
						AuthType.NONE,
						"coap://" + registration.getAddress().getHostAddress() + ":" + registration.getPort(), "", ""));
			}

			@Override
			public void unregistered(Registration registration, Collection<Observation> observerColl) {
				System.out.println("Bye");
			}

			@Override
			public void updated(RegistrationUpdate registrationUpdate, Registration registration) {
				System.out.println("Update");
			}

		};
	}
	
	@PreDestroy
	public void destroy() {
		server.destroy();
		System.out.println("shutdown");
	}

	public boolean isStarted() {
		return isStarted;
	}

	public void setStarted(boolean isStarted) {
		this.isStarted = isStarted;
	}

	public LeshanServer getServer() {
		return server;
	}
}
