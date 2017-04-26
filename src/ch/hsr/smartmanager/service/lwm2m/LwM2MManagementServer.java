package ch.hsr.smartmanager.service.lwm2m;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.eclipse.leshan.core.model.ObjectLoader;
import org.eclipse.leshan.core.model.ObjectModel;
import org.eclipse.leshan.core.node.codec.DefaultLwM2mNodeDecoder;
import org.eclipse.leshan.core.node.codec.DefaultLwM2mNodeEncoder;
import org.eclipse.leshan.core.node.codec.LwM2mNodeDecoder;
import org.eclipse.leshan.core.observation.Observation;
import org.eclipse.leshan.server.californium.CaliforniumRegistrationStore;
import org.eclipse.leshan.server.californium.LeshanServerBuilder;
import org.eclipse.leshan.server.californium.impl.InMemoryRegistrationStore;
import org.eclipse.leshan.server.californium.impl.LeshanServer;
import org.eclipse.leshan.server.cluster.RedisRegistrationEventPublisher;
import org.eclipse.leshan.server.cluster.RedisRegistrationStore;
import org.eclipse.leshan.server.cluster.RedisSecurityStore;
import org.eclipse.leshan.server.impl.FileSecurityStore;
import org.eclipse.leshan.server.model.LwM2mModelProvider;
import org.eclipse.leshan.server.model.StaticModelProvider;
import org.eclipse.leshan.server.registration.Deregistration;
import org.eclipse.leshan.server.registration.ExpirationListener;
import org.eclipse.leshan.server.registration.Registration;
import org.eclipse.leshan.server.registration.RegistrationListener;
import org.eclipse.leshan.server.registration.RegistrationService;
import org.eclipse.leshan.server.registration.RegistrationStore;
import org.eclipse.leshan.server.registration.RegistrationUpdate;
import org.eclipse.leshan.server.security.EditableSecurityStore;
import org.eclipse.leshan.server.security.SecurityInfo;
import org.eclipse.leshan.server.security.SecurityStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import ch.hsr.smartmanager.data.AuthType;
import ch.hsr.smartmanager.data.Device;
import ch.hsr.smartmanager.data.ProtocolType;
import ch.hsr.smartmanager.service.DeviceService;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.util.Pool;

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

		String redisUrl = "redis://localhost:6379";

		Pool<Jedis> jedis = null;
		if (redisUrl != null) {
			// TODO: support sentinel pool and make pool configurable
			try {
				jedis = new JedisPool(new URI(redisUrl));
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}

		EditableSecurityStore securityStore;
		if (jedis == null) {
			securityStore = new FileSecurityStore();
		} else {
			securityStore = new RedisSecurityStore(jedis);
			builder.setRegistrationStore(new RedisRegistrationStore(jedis));
		}
		builder.setSecurityStore(securityStore);

		this.server = builder.build();
		RegistrationService regService = server.getRegistrationService();
		regListener = getRegistrationListener();
		regService.addListener(regListener);
		serverTaskExecutor.doIt(this.server);
	}

	public RegistrationListener getRegistrationListener() {

		return new RegistrationListener() {

			public void registered(Registration registration) {
				deviceService.createDevice(new Device(registration.getId(),registration, AuthType.NONE, registration.getEndpoint(), "", ""));
			}

			@Override
			public void unregistered(Registration registration, Collection<Observation> observerColl) {
				deviceService.deleteDevice(registration.getEndpoint());

			}

			@Override
			public void updated(RegistrationUpdate registrationUpdate, Registration registration) {
				deviceService.updateDevice(registration , new Device(registration.getId(),registration, AuthType.NONE, registration.getEndpoint(), "", ""));
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
