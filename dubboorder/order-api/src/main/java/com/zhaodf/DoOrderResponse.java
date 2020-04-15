package com.zhaodf;

import java.io.Serializable;

/**
 * 类：DoOrderResponse
 *
 * @author zhaodf
 * @date 2019/8/26
 */
public class DoOrderResponse implements Serializable {
    private static final long serialVersionUID = 241160114537623147L;

    private Object data;
    private String code;
    private String memo;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String toString() {
        return "DoOrderResponse{" +
                "data=" + data +
                ", code='" + code + '\'' +
                ", memo='" + memo + '\'' +
                '}';
    }
}
