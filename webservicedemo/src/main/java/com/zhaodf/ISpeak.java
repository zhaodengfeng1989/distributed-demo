package com.zhaodf;

import javax.jws.WebMethod;

/**
 * 类：ISayHello
 *
 * @author zhaodf
 * @date 2019/7/19
 */
public interface ISpeak {
    @WebMethod
    String speak(String name);
}
