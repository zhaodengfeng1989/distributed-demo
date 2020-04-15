package com.zhaodf.zkclient;

import java.io.Serializable;

/**
 * 类：UserCenter
 *
 * @author zhaodf
 * @date 2019/8/1
 */

public class UserCenter implements Serializable {

    private static final long serialVersionUID = 7699742123179632489L;

    //机器Id
    private int mcId;
    //机器名称
    private String mcName;


    public int getMcId() {
        return mcId;
    }

    public void setMcId(int mcId) {
        this.mcId = mcId;
    }

    public String getMcName() {
        return mcName;
    }

    public void setMcName(String mcName) {
        this.mcName = mcName;
    }
}
