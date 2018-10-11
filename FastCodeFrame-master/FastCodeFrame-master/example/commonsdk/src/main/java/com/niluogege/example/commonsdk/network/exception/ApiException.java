package com.niluogege.example.commonsdk.network.exception;

/**
 * Created by niluogege on 2018/8/24.
 * <p>
 * 处理服务器返回数据异常情况
 */

public class ApiException extends RuntimeException {
    private int code;

    private String message;

    public ApiException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
