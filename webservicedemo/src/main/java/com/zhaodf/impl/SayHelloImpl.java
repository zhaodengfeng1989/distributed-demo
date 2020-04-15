package com.zhaodf.impl;

import com.zhaodf.ISayHello;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import javax.jws.WebService;

/**
 * 类：SayHelloImpl
 *
 * @author zhaodf
 * @date 2019/7/19
 */
@WebService
public class SayHelloImpl implements ISayHello {
    @Override
    public String sayHello(String name) {
        System.out.println("call sayHello()");
        return "hello," + name;
    }

    @Override
    public String talk(String name) {
        System.out.println("call talk()");
        return name + "talk with me";
    }
}
