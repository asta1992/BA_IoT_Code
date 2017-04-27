package ch.hsr.smartmanager.service.lwm2m;

import java.util.Collection;

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
						"coap://" + registration.getAddress() + ";" + registration.getPort(), "", "", false);
				deviceService.createOrUpdateDevice(device, registration);
			}

			@Override
			public void unregistered(Registration registration, Collection<Observation> observerColl) {
				deviceService.deleteDevice(registration);

			}

			@Override
			public void updated(RegistrationUpdate registrationUpdate, Registration registration) {
				Device device = new Device(registration.getEndpoint(), registration.getId(),
						"coap://" + registration.getAddress() + ";" + registration.getPort(), "", "", false);
				deviceService.createOrUpdateDevice(device, registration);
			}

		};
	}
}
