package ch.hsr.smartmanager.service.lwm2m;

import java.util.Collection;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.leshan.Link;
import org.eclipse.leshan.core.observation.Observation;
import org.eclipse.leshan.server.registration.Registration;
import org.eclipse.leshan.server.registration.RegistrationListener;
import org.eclipse.leshan.server.registration.RegistrationUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.hsr.smartmanager.data.Device;
import ch.hsr.smartmanager.service.DeviceService;

@Service
public class RegistrationListenerImpl {

	@Autowired
	private DeviceService deviceService;

	public RegistrationListener getRegistrationListener() {

		return new RegistrationListener() {

			public void registered(Registration registration) {
				
				
				
				Device device = new Device(registration.getEndpoint(), registration.getId(),
						"coap://" + registration.getAddress() + ":" + registration.getPort(), "", "",getObjectLinks(registration.getObjectLinks()), false);
				deviceService.createOrUpdateDevice(device, registration);
			}

			@Override
			public void unregistered(Registration registration, Collection<Observation> observerColl) {
				deviceService.deleteDeviceByRegistration(registration);

			}

			@Override
			public void updated(RegistrationUpdate registrationUpdate, Registration registration) {
				Device device = new Device(registration.getEndpoint(), registration.getId(),
						"coap://" + registration.getAddress() + ";" + registration.getPort(), "", "",getObjectLinks(registration.getObjectLinks()), false);
				deviceService.createOrUpdateDevice(device, registration);
			}

		};
	}
	
	private TreeSet<Integer> getObjectLinks(Link[] links) {
		TreeSet<Integer> objectId = new TreeSet<Integer>();

		final String regex = "\\/([0-9]*)\\/";
		final Pattern pattern = Pattern.compile(regex);
		Matcher matcher;

		for (Link linkId : links) {
			matcher = pattern.matcher(linkId.getUrl());
			if (matcher.find()) {
				objectId.add(Integer.parseInt(matcher.group(1)));
			}
		}
		return objectId;
	}
}

