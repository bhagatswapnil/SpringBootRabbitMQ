package com.spring.rabbitmq;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.impl.AMQImpl;
import com.spring.rabbitmq.async.Person;
import com.spring.rabbitmq.async.Processor;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.ReceiveAndReplyCallback;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Controller
public class RabbitMqController {

    /*@Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ConnectionFactory factory;

    @Autowired
    private Channel channel;*/

    @Autowired
    private Processor processor;

    /*@Autowired
    private Exchange exchangeObj;*/

    @Value("${q.rabbitmq.exchange}")
    private String exchange;

    @Value("${q.rabbitmq.routingkey}")
    private String routingkey;

    @PostMapping(value = "/send")
    public ResponseEntity<String> doPost(@RequestBody Map<String, String> reqBody) throws IOException {
        /*rabbitTemplate.receiveAndReply("queue", payload -> {
            System.out.println(payload);
            return payload;
        }, exchange, routingkey);*/
        /*for (int i = 0; i < 20; i++) {
            AMQP.Queue.DeclareOk declareOk = channel.queueDeclarePassive("queue");
            System.out.println("Msgs count: " + declareOk.getMessageCount());
            reqBody.put("count", String.valueOf(10));
            rabbitTemplate.convertAndSend(exchange, routingkey, reqBody);
        }*/
        return ResponseEntity.ok("Success");
    }

    private int getDelay(int count) {
        return count * 5000;
    }

    private int getDelay() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:15672/api/exchanges/%2F/exchange?msg_rates_age=1&msg_rates_incr=1"))
                    .GET()
                    .header("authorization", "Basic Z3Vlc3Q6Z3Vlc3Q=")
                    .build();
            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            JsonNode res = new ObjectMapper().readTree(response.body());
            System.out.println(res);
            int delay = res.get("messages_delayed").asInt();
            return delay != 0 ? delay * 5000 : 5000;
//            return 5000;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @GetMapping(value = "/test")
    public ResponseEntity<String> async() {
        Executor executor = Executors.newFixedThreadPool(4);
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        List<Person> people = new ArrayList<>();
        for (int i=1; i<=200; i++) {
            people.add(new Person("John", "Doe "+i));
        }
        people.forEach(s -> {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                try {
                    processor.initialize(s);
                    processor.process();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, executor);
            futures.add(future);
        });
        try {
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()])).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("Success");
    }

}
