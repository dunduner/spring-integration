package com.zn.springintegrationreceiver.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Receiver {

    @RequestMapping(value = "test", method = RequestMethod.POST)
    @ResponseBody
    public String test(@RequestBody JSONObject params) {
        System.out.println(params.toJSONString());
        return    params.toJSONString();
    }

}