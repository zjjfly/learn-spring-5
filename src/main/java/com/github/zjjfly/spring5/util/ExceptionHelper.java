package com.github.zjjfly.spring5.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * a helper class to get error message by error code from i18n properties
 *
 * @author zjjfly[https://github.com/zjjfly]
 * @date 2021-02-02
 */
@Service
@Slf4j
public class ExceptionHelper {

    private static MessageSource MESSAGE_SOURCE;

    public ExceptionHelper(MessageSource messageSource) {
        MESSAGE_SOURCE = messageSource;
    }

    public static String getExceptionMessage(String key) {
        try {
            return MESSAGE_SOURCE.getMessage(key, null, Locale.getDefault());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return Strings.EMPTY;
    }

    public static String getExceptionMessage(String key, Object[] params) {
        try {
            return MESSAGE_SOURCE.getMessage(key, params, Locale.getDefault());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return Strings.EMPTY;
    }

}
