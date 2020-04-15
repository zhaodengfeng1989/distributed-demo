package com.zhaodf.impl;

import com.zhaodf.ISayHello;
import com.zhaodf.ISpeak;

import javax.jws.WebService;

/**
 * 类：SayHelloImpl
 *
 * @author zhaodf
 * @date 2019/7/19
 */
@WebService
public class SpeakImpl implements ISpeak {
    @Override
    public String speak(String name) {
        System.out.println("call speak()");
        return "I speak " + name;
    }
}
