package com.github.zjjfly.spring5.domain.common;

import com.github.zjjfly.spring5.util.ExceptionHelper;
import lombok.Getter;
import lombok.Setter;

/**
 * common restful result
 *
 * @author zjjfly[https://github.com/zjjfly]
 * @date 2021-02-02
 */
@Setter
@Getter
public class RestResult<T> {

    public static final int SUCCESS = 0;

    public static final String SUCCESS_MSG = "Operation success.";

    private int code;

    private String message;

    private T data;

    private RestResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private RestResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> RestResult<T> success() {
        return new RestResult<>(SUCCESS, SUCCESS_MSG, null);
    }

    public static <T> RestResult<T> success(T data) {
        return new RestResult<>(SUCCESS, SUCCESS_MSG, data);
    }

    public static <T> RestResult<T> success(T data, String message) {
        return new RestResult<>(SUCCESS, message, data);
    }

    public static RestResult<Void> error(int code, String message) {
        return new RestResult<>(code, message);
    }

    public static RestResult<Void> error(int code) {
        return error(code, ExceptionHelper.getExceptionMessage(String.valueOf(code)));
    }

    public static RestResult<Void> errorWithParams(int code, Object... arguments) {
        return error(code, ExceptionHelper.getExceptionMessage(String.valueOf(code), arguments));
    }

}
