package com.niluogege.example.commonsdk.network.exception;

/**
 * 服务器返回的异常
 */
public class NoDataExceptionException extends RuntimeException {
    public NoDataExceptionException() {
        super("服务器没有返回对应的data字段", new Throwable("Server error"));
    }
}
