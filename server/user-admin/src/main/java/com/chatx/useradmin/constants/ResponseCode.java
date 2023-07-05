package com.chatx.useradmin.constants;

import com.chatx.commons.constants.CommonResponseCode;

/**
 * 响应代码
 *
 * @author Jun
 * @since 1.0.0
 */
public interface ResponseCode extends CommonResponseCode {
    //@formatter:off

    /*--------------------------------------------
    |                 用户模块消息                 |
    ============================================*/

    /** 账号或密码错误 */
    String SIGN_IN_ERR = "80010000";

    /** 用户已存在 */
    String USER_ALREADY_EXIST = "80010001";
}
