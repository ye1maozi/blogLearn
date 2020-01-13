package com.learn.blog.util;

import java.io.Serializable;

public class Result implements Serializable {
    public static int SUCCESS_CODE = 0;
    public static int FAIL_CODE = 1;


    int code;
    String message;


    Object result;

    private Result(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.result = data;
    }

    public static Result success() {
        return new Result(SUCCESS_CODE,null,null);
    }
    public static Result success(Object data) {
        return new Result(SUCCESS_CODE,"",data);
    }
    public static Result fail(String message) {
        return new Result(FAIL_CODE,message,null);
    }
    public static Result fail(String message, Constant.ResultCode code) {
        return new Result(code.num,message,null);
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

}
