package com.zn.springintegrationhttpdemo.http;

import org.springframework.messaging.Message;

public interface RequestGateway {

    String echo(Message<?> message);

}