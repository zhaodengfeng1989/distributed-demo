package com.zhaodf;

import java.io.Serializable;

/**
 * 类：DoOrderRequest
 *
 * @author zhaodf
 * @date 2019/8/26
 */
public class DoOrderRequest implements Serializable {
    private static final long serialVersionUID = -112884354647770600L;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "DoOrderRequest{" +
                "name='" + name + '\'' +
                '}';
    }
}
