package com.github.zjjfly.spring5.domain.common;

import com.github.zjjfly.spring5.util.ExceptionHelper;
import lombok.Getter;

/**
 * common business  exception
 *
 * @author zjjfly[https://github.com/zjjfly]
 * @date 2021-02-02
 */
@Getter
public class BizException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final int errorCode;

    public BizException(int errorCode, Object... args) {
        this(null, errorCode, args);
    }

    public BizException(Throwable cause, int errorCode, Object... args) {
        super(ExceptionHelper.getExceptionMessage(String.valueOf(errorCode), args));
        if (cause != null) {
            setStackTrace(cause.getStackTrace());
        }
        this.errorCode = errorCode;
    }

}
