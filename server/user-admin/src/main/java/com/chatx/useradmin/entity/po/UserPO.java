package com.chatx.useradmin.entity.po;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.context.annotation.Primary;
import org.springframework.data.annotation.Id;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

/**
 * 用户 PO 对象
 *
 * @author Jun
 * @since 1.0.0
 */
@Data
@Accessors(chain = true)
public class UserPO {

    private Integer id;

    private String nickname;

    private String mobile;

    private String passwd;

    /** @see com.chatx.useradmin.constants.GenderEnum */
    private Integer gender;

    private LocalDate birthday;

    private String areaCode;

    private LocalDate createAt;

    /**
     * 使用 {@link PasswordEncoder} encode password, 如果 password 不为空的话.
     *
     * @param encoder {@link PasswordEncoder}
     */
    public UserPO encodePassword(PasswordEncoder encoder) {
        if (StringUtils.hasText(passwd)) {
            this.passwd = encoder.encode(passwd);
        }
        return this;
    }
}
