package es.codeurjc.mastercloudapps.grpc.client;


import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.codeurjc.mastercloudapps.grpc.Request;
import es.codeurjc.mastercloudapps.grpc.Response;
import es.codeurjc.mastercloudapps.grpc.ServiceGrpc.ServiceBlockingStub;
import net.devh.boot.grpc.client.inject.GrpcClient;

@Controller
public class SampleClient {

	@GrpcClient("textServer")
	private ServiceBlockingStub client;

	@Autowired
	private Producer producer;

	@RabbitListener(queues = "newTasks", ackMode = "AUTO")
	public void received(String text) {
		System.out.println();
		System.out.println(" --- SPRING BOOT WORKER  --- ");
		System.out.println("Message from RabbitMQ queue..." + text);
		System.out.println("Sending message to External Service through gRPC Protocol...");

		this.processTask();

		Request request = Request.newBuilder().setText(text).build();
		Response response = client.toUpperCase(request);

		System.out.println("Response received from External Service:\n" + response);

		JSONObject json = this.createJson(100, true, response.getResult());
		producer.sendMessage(json);

	}

	private void processTask() {
		for (int i = 0; i <= 100; i++) {
			try {
				Thread.sleep(1000);
				JSONObject json = this.createJson(i, false, null);
				producer.sendMessage(json);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private JSONObject createJson(int progress, Boolean completed, String result) {
		JSONObject json = new JSONObject();
		json.put("id", 0);
		json.put("progress", progress);
		json.put("completed", completed);
		if(completed){
			json.put("result", result);
		}
		return json;
	}
}
