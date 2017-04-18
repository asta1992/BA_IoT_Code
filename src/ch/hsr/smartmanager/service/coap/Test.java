package ch.hsr.smartmanager.service.coap;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.text.AbstractDocument.Content;

import org.eclipse.californium.core.CoapResponse;
import org.eclipse.leshan.ResponseCode;
import org.eclipse.leshan.client.californium.LeshanClient;
import org.eclipse.leshan.client.californium.LeshanClientBuilder;
import org.eclipse.leshan.client.californium.impl.LwM2mClientResponseBuilder;
import org.eclipse.leshan.client.resource.LwM2mObjectEnabler;
import org.eclipse.leshan.client.resource.ObjectEnabler;
import org.eclipse.leshan.client.resource.ObjectsInitializer;
import org.eclipse.leshan.core.model.ResourceModel.Type;
import org.eclipse.leshan.core.node.LwM2mNodeVisitor;
import org.eclipse.leshan.core.node.LwM2mResource;
import org.eclipse.leshan.core.request.ContentFormat;
import org.eclipse.leshan.core.request.DiscoverRequest;
import org.eclipse.leshan.core.request.DownlinkRequest;
import org.eclipse.leshan.core.request.LwM2mRequest;
import org.eclipse.leshan.core.request.ReadRequest;
import org.eclipse.leshan.core.request.WriteRequest;
import org.eclipse.leshan.core.response.DiscoverResponse;
import org.eclipse.leshan.core.response.LwM2mResponse;
import org.eclipse.leshan.server.californium.LeshanServerBuilder;
import org.eclipse.leshan.server.californium.impl.LeshanServer;
import org.eclipse.leshan.server.registration.Registration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;

import ch.hsr.smartmanager.data.AuthType;
import ch.hsr.smartmanager.data.Device;
import ch.hsr.smartmanager.data.ProtocolType;

public class Test {

	public static void main(String[] args) {

		CoapConnector conn = new CoapConnector();

		Device dev = new Device("Device1", ProtocolType.COAP, AuthType.NONE, "coap://127.0.0.1:49594/1/0/0/", "", "");

		CoapHandlerImpl handler = (CoapHandlerImpl) conn.connectToDevice(dev);

		LeshanServer lwServer = new LeshanServerBuilder().build();
		
		lwServer.start();
		
		DiscoverRequest dis = new DiscoverRequest(3);
		LeshanClient client = new LeshanClientBuilder("127.0.0.1:49594").build();
		WriteRequest w = new WriteRequest(3, 0, 14, "Test");
		
		DiscoverResponse dis = new DiscoverResponse(ResponseCode., [3, 6], "")
		client.
		ClientHttpResponse response = lwServer.send(client, w);
	}

}
