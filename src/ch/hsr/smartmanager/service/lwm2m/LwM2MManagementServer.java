package ch.hsr.smartmanager.service.lwm2m;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.annotation.PostConstruct;
import org.eclipse.leshan.core.model.ObjectLoader;
import org.eclipse.leshan.core.model.ObjectModel;
import org.eclipse.leshan.core.node.codec.DefaultLwM2mNodeDecoder;
import org.eclipse.leshan.core.node.codec.DefaultLwM2mNodeEncoder;
import org.eclipse.leshan.core.node.codec.LwM2mNodeDecoder;
import org.eclipse.leshan.server.californium.LeshanServerBuilder;
import org.eclipse.leshan.server.californium.impl.LeshanServer;
import org.eclipse.leshan.server.cluster.RedisRegistrationStore;
import org.eclipse.leshan.server.cluster.RedisSecurityStore;
import org.eclipse.leshan.server.impl.FileSecurityStore;
import org.eclipse.leshan.server.model.LwM2mModelProvider;
import org.eclipse.leshan.server.model.StaticModelProvider;
import org.eclipse.leshan.server.security.EditableSecurityStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.util.Pool;

@Service
public class LwM2MManagementServer {

	@Autowired
	private RegistrationListenerImpl registrationListenerImpl;

	@Autowired
	private ServerTaskExecutor serverTaskExecutor;
	private LeshanServer server;
	private String address;
	private int port;
	private List<ObjectModel> models = ObjectLoader.loadDefault();


	private String redisUrl = "redis://127.0.0.1:6379";
	private Resource resource = new ClassPathResource("ch/hsr/smartmanager/resources/models/");

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
		
		Pool<Jedis> jedis = null;
		if (redisUrl != null) {
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
