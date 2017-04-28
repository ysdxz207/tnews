package com.puyixiaowo.tnews.bean;

/**
 * 错误异常
 * @author huangfeihong
 * @date 2017-03-07 22:22:06
 */
public class Error {
    public static void throwError( String message) {
        throwError("ERROR", message);
    }

    public static void throwError(String errorCode, String message) {
        throw new RuntimeException(errorCode, new Exception(message));
    }
}
