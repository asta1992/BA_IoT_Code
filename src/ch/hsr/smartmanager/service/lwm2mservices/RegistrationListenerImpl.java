package ch.hsr.smartmanager.service.lwm2mservices;

import java.util.Collection;
import java.util.TreeSet;

import org.eclipse.leshan.Link;
import org.eclipse.leshan.core.observation.Observation;
import org.eclipse.leshan.server.registration.Registration;
import org.eclipse.leshan.server.registration.RegistrationListener;
import org.eclipse.leshan.server.registration.RegistrationUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.hsr.smartmanager.data.Device;
import ch.hsr.smartmanager.service.applicationservices.DeviceService;

@Service
public class RegistrationListenerImpl {

	@Autowired
	private DeviceService deviceService;

	public RegistrationListener getRegistrationListener() {

		return new RegistrationListener() {
			@Override
			public void registered(Registration registration) {
				updateOrAddDevice(registration);
			}

			@Override
		    public void unregistered(Registration registration, Collection<Observation> observations, boolean expired) { }

			@Override
			public void updated(RegistrationUpdate update, Registration updatedRegistration, Registration previousRegistration) {
				updateOrAddDevice(updatedRegistration);
				
			}

		};
	}

	private void updateOrAddDevice(Registration registration) {
		Device device = new Device(registration.getEndpoint(), registration.getId(),
				"coap:/" + registration.getAddress() + ":" + registration.getPort(),
				getObjectLinks(registration.getObjectLinks()), registration.getLastUpdate(), false);
		deviceService.createOrUpdateDevice(device, registration);

	}

	private TreeSet<String> getObjectLinks(Link[] links) {
		TreeSet<String> objectId = new TreeSet<String>();

		for (Link linkId : links) {
			if (linkId.getUrl().length() > 2)
				objectId.add(linkId.getUrl());
		}
		return objectId;
	}
}
