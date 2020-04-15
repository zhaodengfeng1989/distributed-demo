package com.zhaodf;

import com.zhaodf.impl.SayHelloImpl;
import com.zhaodf.impl.SayHelloImplService;
import com.zhaodf.impl.SpeakImpl;
import com.zhaodf.impl.SpeakImplService;

/**
 * 类：ClientDemo
 *
 * @author zhaodf
 * @date 2019/7/19
 */
public class ClientDemo {
    public static void main(String[] args) {
        SayHelloImplService service = new SayHelloImplService();
        SayHelloImpl impl = service.getSayHelloImplPort();
        System.out.println(impl.sayHello("zhaodf"));

        SpeakImplService speakImplService = new SpeakImplService();
        SpeakImpl speak = speakImplService.getSpeakImplPort();
        System.out.println(speak.speak("Chinese"));
    }
}
