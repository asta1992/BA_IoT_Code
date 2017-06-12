package ch.hsr.smartmanager.service.lwm2mservices;

import org.eclipse.leshan.server.californium.impl.LeshanServer;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class ServerTaskExecutor {

	private class StartTask implements Runnable {

		private final LeshanServer server;

		public StartTask(LeshanServer server) {
			this.server = server;
		}
		public void run() {
			server.start();
		}
	}

	private TaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();

	public void doIt(LeshanServer server) {
		taskExecutor.execute(new StartTask(server));
	}
}