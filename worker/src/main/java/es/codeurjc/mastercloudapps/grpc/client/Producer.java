package es.codeurjc.mastercloudapps.grpc.client;

import org.json.JSONObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Producer {

    @Autowired
    RabbitTemplate rabbitTemplate;

    public void sendMessage(JSONObject json) {

        rabbitTemplate.convertAndSend("tasksProgress", json.toString());
    }
}
