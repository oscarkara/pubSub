package com.oscarkara.pubSub.config;

import com.oscarkara.pubSub.model.Subscription;
import com.oscarkara.pubSub.model.Topic;
import com.oscarkara.pubSub.repository.SubscriptionRepository;
import com.oscarkara.pubSub.repository.TopicRepository;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class RabbitMQConfig {

    @Bean
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }

    @Bean
    public Declarables topicBindings(TopicRepository topicRepository,
                                     SubscriptionRepository subscriptionRepository) {
        List<Declarable> declarable = new ArrayList<>();

        List<Topic> topics = topicRepository.findAll();

        for (Topic topic : topics) {
            TopicExchange exchange = new TopicExchange("exchange.topic." + topic.getId());
            declarable.add(exchange);

            List<Subscription> subscriptions = subscriptionRepository.findAllByTopicId(topic.getId());
            for (Subscription sub : subscriptions) {
                String queueName = "queue.user." + sub.getUser().getId() ;
                Queue queue = new Queue(queueName, true);
                declarable.add(queue);
                declarable.add(BindingBuilder.bind(queue).to(exchange).with("*"));
            }
        }

        return new Declarables(declarable);
    }
}
