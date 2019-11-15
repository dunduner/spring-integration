package com.zn.kafka.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * kafka消费者测试
 */
@Component
public class TestConsumer {
    private static Logger logger = LoggerFactory.getLogger(TestConsumer.class);

    @KafkaListener(topics = {"test_topic"})
    public void listen(ConsumerRecord<?, ?> record,String message) throws Exception {
//        String topic = record.topic();
//        long offset = record.offset();
//        Object value = record.value();
//        System.out.printf("topic = %s, offset = %d, value = %s \n", topic, offset, value);
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object msg = kafkaMessage.get();
            System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------");
            logger.info("----------------- record =" + record);
            logger.info("------------------ message =" + msg);
            System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------");
            logger.info("------------------ message直接输出的 =" + message);
        }
    }

    /**
     * 监听test主题,有消息就读取
     *
     * @param message
     */
    @KafkaListener(topics = {"topic-seazen"})
    public void consumer(String message) {
        System.out.println("topic-seazen的消息 message : " + message);
    }
}