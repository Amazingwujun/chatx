package com.chatx.commons.utils;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * 将 <code>code</code> 转为可读消息
 *
 * @author Jun
 * @since 1.0.0
 */
@Component
public class MessageUtils {

    private static MessageSource messageSource;
    private static MessageSource commonMessageSource;

    public MessageUtils(@Nullable @Qualifier("messageSource") MessageSource messageSource,
                        @Qualifier("commonMessageSource") MessageSource common) {
        MessageUtils.messageSource = messageSource;
        MessageUtils.commonMessageSource = common;
    }

    /**
     * 获取消息
     *
     * @param code 消息代码
     * @return 消息内容
     */
    public static String getMessage(String code) {
        if (messageSource == null) {
            return commonMessageSource.getMessage(code, null, Locale.SIMPLIFIED_CHINESE);
        } else {
            try {
                return messageSource.getMessage(code, null, Locale.SIMPLIFIED_CHINESE);
            } catch (NoSuchMessageException e) {
                return commonMessageSource.getMessage(code, null, Locale.SIMPLIFIED_CHINESE);
            }
        }
    }
}
