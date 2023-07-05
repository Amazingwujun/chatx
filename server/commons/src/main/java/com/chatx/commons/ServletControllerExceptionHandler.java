package com.chatx.commons;

import com.chatx.commons.constants.CommonResponseCode;
import com.chatx.commons.entity.ChatxResult;
import com.chatx.commons.exception.ChatxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.DispatcherServlet;

import java.util.List;

/**
 * spring web controller 异常全局处理器
 *
 * @author Jun
 * @since 1.0.0
 */
@RestControllerAdvice
@ConditionalOnClass(DispatcherServlet.class)
@ConditionalOnProperty(value = "chatx.controller-exception-handler.enabled", matchIfMissing = true)
public class ServletControllerExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ServletControllerExceptionHandler.class);

    /**
     * 自定义异常处理器
     *
     * @param e {@link com.chatx.commons.exception.ChatxException}
     */
    @ExceptionHandler(ChatxException.class)
    public ChatxResult<Void> ChatxExceptionHandle(ChatxException e) {
        String msg = e.getMessage();

        log.info(msg, e);
        return ChatxResult.fail(CommonResponseCode.SERVER_ERROR, msg);
    }

    /**
     * 未知异常捕获,未知的异常直接返回告知客户服务忙,以免暴露内部过多细节
     *
     * @return 服务忙
     */
    @ExceptionHandler(Throwable.class)
    public ChatxResult<Void> throwableHandle(Throwable e) {
        String msg = e.getMessage();

        log.error(msg, e);
        return ChatxResult.error(CommonResponseCode.SERVER_ERROR);
    }

    /**
     * 处理请求体为空的异常
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ChatxResult<Void> HttpMessageNotReadableExceptionHandle(HttpMessageNotReadableException e) {
        log.info(e.getMessage(), e);

        return ChatxResult.fail(CommonResponseCode.ILLEGAL_ARGS);
    }

    /**
     * 请求入参格式异常
     *
     * @param e HttpMediaTypeNotSupportedException
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ChatxResult<Void> httpMediaTypeNotSupportedExceptionHandle(HttpMediaTypeNotSupportedException e) {
        log.info(e.getMessage(), e);

        return ChatxResult.fail(CommonResponseCode.ILLEGAL_ARGS, e.getMessage());
    }

    /**
     * Http方法异常，目前全局使用POST
     *
     * @param e {@link HttpRequestMethodNotSupportedException}
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ChatxResult<Void> httpRequestMethodNotSupportedExceptionHandle(HttpRequestMethodNotSupportedException e) {
        log.info(e.getMessage(), e);

        //用户使用的方法
        String method = e.getMethod();
        String supportMethods = "";

        //目前系统支持的方法
        String[] methods = e.getSupportedMethods();

        if (methods != null && methods.length > 0) {
            for (int i = 0; i < methods.length; i++) {
                if (i == methods.length - 1) {
                    supportMethods = supportMethods.concat(methods[i]);
                } else {
                    supportMethods = supportMethods.concat(methods[i] + ",");
                }
            }
        }

        return ChatxResult.fail(CommonResponseCode.HTTP_METHOD_ERR,
                String.format("不支持 [%s] 方法, 当前支持方法：[%s]", method, supportMethods));
    }

    /**
     * 请求头缺失异常处理
     *
     * @param e {@link  MissingRequestHeaderException}
     */
    @ExceptionHandler(MissingRequestHeaderException.class)
    public ChatxResult<Void> MissingRequestHeaderExceptionHandle(MissingRequestHeaderException e) {
        String headerName = e.getHeaderName();

        return ChatxResult.fail(CommonResponseCode.ILLEGAL_ARGS, "缺少请求头: " + headerName);
    }

    /**
     * 数据校检异常
     *
     * @param e {@link BindException}
     */
    @ExceptionHandler(BindException.class)
    public ChatxResult<Void> validExceptionHandle(BindException e) {
        log.info(e.getMessage(), e);

        String msg = getMessage(e);

        return ChatxResult.fail(CommonResponseCode.ILLEGAL_ARGS, msg);
    }

    /**
     * 数据校检异常,当参数含有注解 {@link org.springframework.web.bind.annotation.ResponseBody} 时,
     * <p>
     * {@link org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor} 抛出的异常
     * {@link MethodArgumentNotValidException}
     *
     * @param e {@link MethodArgumentNotValidException}
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ChatxResult<Void> methodArgumentNotValidExceptionHandle(MethodArgumentNotValidException e) {
        log.info(e.getMessage());

        String msg = getMessage(e);

        return ChatxResult.fail(CommonResponseCode.ILLEGAL_ARGS, msg);
    }

    /**
     * 异常消息获取
     *
     * @param e {@link MethodArgumentNotValidException} or {@link BindException}
     * @return 异常消息
     */
    private String getMessage(Exception e) {
        if (e instanceof MethodArgumentNotValidException mane) {
            String message = "";
            List<FieldError> fieldErrors = mane.getBindingResult().getFieldErrors();
            for (FieldError error : fieldErrors) {
                message = error.getDefaultMessage();
            }
            return message;
        } else if (e instanceof BindException be) {
            String message = "";
            List<FieldError> fieldErrors = be.getBindingResult().getFieldErrors();
            for (FieldError error : fieldErrors) {
                message = error.getDefaultMessage();
            }
            return message;
        } else {
            log.error(e.getMessage(), e);
            return "异常类型无法捕获";
        }
    }
}
