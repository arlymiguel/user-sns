package com.qcaldwell.user.sqs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qcaldwell.user.dto.UserMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserListener {

    //@SqsListener(value = "${spring.cloud.aws.sqs.topic_user_test}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void listener(UserMessageDto userMessageDto){
        log.info("listener/message:{}", userMessageDto.getMessage());
    }
}
