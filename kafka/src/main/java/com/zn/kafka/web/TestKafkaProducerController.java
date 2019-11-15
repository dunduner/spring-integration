package com.zn.kafka.web;


import com.zn.kafka.kafka.KfkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("kafka")
public class TestKafkaProducerController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private KfkaProducer producer;

    @RequestMapping("/send")
    public String send(){
        //主体是  test_topic
//        kafkaTemplate.send("test_topic", msg);
        kafkaTemplate.send("test_topic","你好,kafka，时间是："  + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));
        return "success";
    }


    @RequestMapping("/testSendMsg")
    @ResponseBody
    public String testSendMsg(){
        producer.send();
        return "success";
    }

}