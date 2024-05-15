package com.tsfn.JobsScheduler.config;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic NotificationSMS()
    {
        return TopicBuilder.name("TEST1").build();
    }

    @Bean
    public NewTopic NotSMS()
    {
        return TopicBuilder.name("NOTSMS").build();
    }

    @Bean
    public NewTopic NOTWHATSAPP()
    {
        return TopicBuilder.name("NOTWHATSAPP").build();
    }

    @Bean
    public NewTopic NOTMAIL()
    {
        return TopicBuilder.name("NOTMAIL").build();
    }

}
