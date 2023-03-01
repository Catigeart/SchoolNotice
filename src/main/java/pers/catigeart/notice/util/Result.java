package pers.catigeart.notice.util;

import lombok.Data;

/**
 * @author Catigeart
 * GitHub: https://github.com/Catigeart
 * Time: 2022/5/16 23:10
 */
@Data
public class Result<T> {

    /**
     * 返回状态码
     */
    private Integer code;
    /**
     * 返回信息
     */
    private String message;

    /**
     * 返回数据
     */
    private T data;

    private Result() {
    }

    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.setCode(CodeUtil.SUCCESS.getCode());
        result.setMessage(CodeUtil.SUCCESS.getMsg());
        return result;
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = success();
        result.setData(data);
        return result;
    }

    public static <T> Result<T> success(String message, T data) {
        Result<T> result = success();
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> success(Integer code, String message, T data) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> fail() {
        Result<T> result = new Result<>();
        result.setCode(CodeUtil.FAILURE.getCode());
        result.setMessage(CodeUtil.FAILURE.getMsg());
        return result;
    }

    public static <T> Result<T> fail(T data) {
        Result<T> result = fail();
        result.setData(data);
        return result;
    }

    public static <T> Result<T> fail(String message, T data) {
        Result<T> result = fail();
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> fail(Integer code, String message) {
        Result<T> result = fail();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public static <T> Result<T> fail(Integer code, String message, T data) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }
}
