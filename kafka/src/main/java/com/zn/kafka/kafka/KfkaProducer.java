package com.zn.kafka.kafka;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zn.kafka.entity.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;


@Component
public class KfkaProducer {

    private static Logger logger = LoggerFactory.getLogger(KfkaProducer.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    private Gson gson = new GsonBuilder().create();

    public void send(){
        for (int i = 0; i < 5; i++) {
            Message message = new Message();
            message.setId(System.currentTimeMillis());
            message.setMsg(UUID.randomUUID().toString()+"----"+i);
            message.setSendTime(new Date());
            logger.info("发送消息给topic-seazen ----->>>>>  message = {}",gson.toJson(message));
            kafkaTemplate.send("topic-seazen",gson.toJson(message));
        }
    }
}
