package com.zn.springintegrationhttpdemo.http;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.GenericMessage;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;

public class ServiceActivator implements MessageHandler {

    private final static Log logger = LogFactory.getLog(ServiceActivator.class);
    private final static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        try {
            JSONObject payload = JSONObject.parseObject(new String((byte[]) message.getPayload(), "UTF-8"), JSONObject.class);

            System.out.println("消息头："+message.getHeaders().toString());
            logger.info("接收参数：" + payload.toJSONString());
            payload.put("test3", "你好789abc---新增的参数");
            logger.info("发送参数：" + payload.toJSONString());
            Message<?> toSend = new GenericMessage<>(payload.toJSONString(), message.getHeaders());
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            System.out.println(toSend.toString());
            activator(toSend);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void activator(Message<?> message) {

        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("http-outbound-config.xml");
        RequestGateway requestGateway = context.getBean("requestGateway", RequestGateway.class);
        String reply = requestGateway.echo(message);
        logger.info("发送回执：" + reply);
        context.close();
    }

}