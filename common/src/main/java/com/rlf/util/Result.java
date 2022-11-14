package com.rlf.util;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yingyongzhi
 */
@Data
@AllArgsConstructor
public class Result<T> implements Serializable {

    private static final long serialVersionUID = -4156089123068116518L;

    /**
     * 是否成功
     */
    private boolean success = true;

    /**
     * code编码
     */
    private int code = 200;
    /**
     * 返回描述
     */
    private String message = "";
    /**
     * 数据
     */
    private T data;

    public boolean isSuccess() {
        return this.success;
    }

    public static Result success() {
        return new Result();
    }

    public static <T> Result<T> success(T data) {
        return new Result<T>(data);
    }

    public static <T> Result<T> error() {
        return new Result(false,ResultEnum.SERVER_ERROR_CODE);
    }

    public static <T> Result<T> error(String message) {
        return new Result<T>(false, ResultEnum.SERVER_ERROR_CODE.getCode(),message);
    }

    public static <T> Result<T> error(String message, int code) {
        return new Result<T>(false, code,message);
    }

    public Result() {
        super();
    }

    public Result(T data) {
        super();
        this.data = data;
    }

    public Result(boolean success,ResultEnum resultEnum) {
        super();
        this.message = resultEnum.getMsg();
        this.code = resultEnum.getCode();
        this.success = success;
    }

    public Result(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }
}
