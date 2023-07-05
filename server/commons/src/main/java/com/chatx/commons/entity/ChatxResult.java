package com.chatx.commons.entity;

import com.chatx.commons.utils.MessageUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 通用响应对象
 *
 * @author Jun
 * @since 1.1.0
 */
public class ChatxResult<T> {
    //@formatter:off

    /** 项目通用成功代码 */
    private static final String SUCCESS = "00000000";

    /** 响应编码 */
    private String code;
    /** 响应数据 */
    private T data;
    /** 响应消息 */
    private String msg;

    //@formatter:on

    public ChatxResult() {
    }

    private ChatxResult(String code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public static <T> ChatxResult<T> ok() {
        return new ChatxResult<>(SUCCESS, null, "ok");
    }

    public static <T> ChatxResult<T> ok(T data) {
        return new ChatxResult<>(SUCCESS, data, "ok");
    }

    public static <T> ChatxResult<T> fail(String code) {
        return new ChatxResult<>(code, null, MessageUtils.getMessage(code));
    }

    public static <T> ChatxResult<T> fail(String code, String msg) {
        return new ChatxResult<>(code, null, msg);
    }

    public static <T> ChatxResult<T> error(String code) {
        return new ChatxResult<>(code, null, MessageUtils.getMessage(code));
    }

    public static <T> ChatxResult<T> fromFailureResult(ChatxResult<?> failureResult) {
        return ChatxResult.fail(failureResult.getCode(), failureResult.getMsg());
    }

    /**
     * 返回当前响应是否成功
     */
    @JsonIgnore
    public boolean isOk() {
        return SUCCESS.equals(code);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ChatxResult{" +
                "code='" + code + '\'' +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }
}
