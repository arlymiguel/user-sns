package com.qcaldwell.user.sns;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.CreateTopicRequest;
import com.amazonaws.services.sns.model.CreateTopicResult;
import com.amazonaws.services.sns.model.PublishRequest;
import com.qcaldwell.user.dto.UserMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserPublisher {
    private final AmazonSNS amazonSNS;
    private final HashMap<String, String> topicArn = new HashMap<>();
    @Value("${spring.cloud.aws.sns.user_publisher}")
    private String topicName;
    protected String getTopicARN(String topicName) {
        return topicArn.computeIfAbsent(topicName, k -> {
            CreateTopicResult result = amazonSNS.createTopic(new CreateTopicRequest(topicName));
            return result.getTopicArn();
        }
        );
    }
    public void sendMessage(UserMessageDto message){
        String topic = getTopicARN(topicName);
        PublishRequest publishRequest = new PublishRequest(topic, message.getMessage());
        amazonSNS.publish(publishRequest);
        log.info("message sent");
    }
}
