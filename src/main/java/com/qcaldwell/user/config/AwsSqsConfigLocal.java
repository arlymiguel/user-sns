package com.qcaldwell.user.config;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.config.QueueMessageHandlerFactory;
import org.springframework.cloud.aws.messaging.config.annotation.EnableSqs;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.annotation.support.PayloadMethodArgumentResolver;

import java.util.Collections;

@Slf4j
@EnableSqs
@Configuration
@Profile({"default", "local", "test"})
public class AwsSqsConfigLocal {
    @Value("${spring.cloud.localstack.sqs.url}")
    private String sqsUrl;

    @Value("${spring.cloud.aws.region.static}")
    private String awsRegion;

    // AmazonSQSAsync is an interface for accessing the SQS asynchronously.
    // Each asynchronous method will return a Java Future object representing the asynchronous operation.
    @Bean
    @Primary
    public AmazonSQSAsync amazonSQSAsync() {

        return AmazonSQSAsyncClientBuilder.standard()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(sqsUrl, awsRegion))
                .build();
    }

    @Bean
    public QueueMessagingTemplate queueMessagingTemplate(AmazonSQSAsync amazonSQSAsync) {

        return new QueueMessagingTemplate(amazonSQSAsync);
    }

    @Bean
    public QueueMessageHandlerFactory queueMessageHandlerFactory(
            final ObjectMapper mapper, final AmazonSQSAsync amazonSQSAsync) {


        log.info("SQS-queueMessageHandlerFactory");
        final QueueMessageHandlerFactory queueHandlerFactory =
                new QueueMessageHandlerFactory();
        queueHandlerFactory.setAmazonSqs(amazonSQSAsync);
        queueHandlerFactory.setArgumentResolvers(Collections.singletonList(
                new PayloadMethodArgumentResolver(jackson2MessageConverter(mapper))
        ));

        return queueHandlerFactory;
    }

    private MessageConverter jackson2MessageConverter(final ObjectMapper mapper) {
        log.info("SQS-jackson2MessageConverter");

        final MappingJackson2MessageConverter
                converter = new MappingJackson2MessageConverter();
        converter.setObjectMapper(mapper);

        return converter;
    }
}
