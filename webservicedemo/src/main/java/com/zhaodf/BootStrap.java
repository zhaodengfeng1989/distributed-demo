package com.zhaodf;

import com.zhaodf.impl.SayHelloImpl;
import com.zhaodf.impl.SpeakImpl;

import javax.xml.ws.Endpoint;

/**
 * 类：BootStrap
 *
 * @author zhaodf
 * @date 2019/7/19
 */
public class BootStrap {
    public static void main(String[] args) {
        Endpoint.publish("http://localhost:8888/hello",new SayHelloImpl());
        Endpoint.publish("http://localhost:8888/speak",new SpeakImpl());
        System.out.println("publish success!");
    }
}
