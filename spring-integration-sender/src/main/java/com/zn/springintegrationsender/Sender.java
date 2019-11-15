package com.zn.springintegrationsender;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;

public class Sender {

    public static void main(String[] args) {
        JSONObject params = new JSONObject();
        params.put("test1", "aaaaa");
        Student student = new Student();
        student.setAge(18);
        student.setName("张三");
        params.put("studnet", student);
        System.out.println(params.toJSONString());
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost("http://localhost:8080/receiveGateway");
//        HttpPost post = new HttpPost("http://localhost:8080/http/receiveGateway");
//        HttpPost post = new HttpPost("http://localhost:8081/test");
        try {
            post.setHeader("Content-Type", "application/json;charset=UTF-8");
            post.setHeader("Accept", "application/json");
            post.setEntity(new StringEntity(params.toJSONString(), Charset.forName("UTF-8")));
            CloseableHttpResponse httpResponse = httpClient.execute(post);
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                System.out.println("响应================================================================");
                System.out.println(EntityUtils.toString(httpEntity));
            }
            httpResponse.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}