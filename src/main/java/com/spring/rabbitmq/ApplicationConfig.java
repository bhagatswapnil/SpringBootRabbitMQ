package com.spring.rabbitmq;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.support.DefaultMessagePropertiesConverter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class ApplicationConfig {

    /*@Value("${q.rabbitmq.queue}")
    private String queueName;

    @Value("${q.rabbitmq.exchange}")
    private String exchange;

    @Value("${q.rabbitmq.routingkey}")
    private String routingkey;

    private String dlq = "dlqueue";
    private String dlx = "dlexchange";

    @Bean("queue")
    Queue queue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", 10000);
        args.put("x-dead-letter-exchange", dlx);
        args.put("x-dead-letter-routing-key", routingkey);
        return QueueBuilder.durable(queueName).withArguments(args).build();
    }

    @Bean("dlq")
    Queue dlq() {
        return QueueBuilder
                .durable(dlq)
                .build();
    }

    @Bean("dlx")
    Exchange dlx() {
        return ExchangeBuilder.topicExchange(dlx).build();
        *//*Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange(exchange, "x-delayed-message", true, false, args);*//*
    }

    @Bean
    Channel channel(ConnectionFactory factory) {
        Channel channel = factory.createConnection().createChannel(false);
        return channel;
    }

    @Bean("exchange")
    Exchange exchange() {
        return ExchangeBuilder.topicExchange(exchange).build();
        *//*Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange(exchange, "x-delayed-message", true, false, args);*//*
    }

    @Bean("binding")
    Binding binding(Queue queue, Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingkey).noargs();
    }

    @Bean("dlBinding")
    Binding dlBinding(Queue dlq, Exchange dlx) {
        return BindingBuilder.bind(dlq).to(dlx).with(routingkey).noargs();
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setUsername("guest");
        return factory;
    }

    @Bean
    RabbitAdmin rabbitAdmin(ConnectionFactory factory) {
        return new RabbitAdmin(factory);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory factory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(factory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    *//*@Bean
    public SimpleMessageListenerContainer listenerContainer(ConnectionFactory factory) {
        SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer();
        listenerContainer.setConnectionFactory(factory);
        listenerContainer.setMessagePropertiesConverter(new DefaultMessagePropertiesConverter());
        listenerContainer.setMessageListener(new Consumer());
        listenerContainer.setAcknowledgeMode(AcknowledgeMode.AUTO);
        return listenerContainer;
    }*//*
*/
}
