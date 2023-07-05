package com.chatx.useradmin.entity.params;

import com.chatx.commons.constrait.Digital;
import com.chatx.commons.entity.ChatxContext;
import com.chatx.commons.validate.Create;
import com.chatx.commons.validate.Update;
import com.chatx.useradmin.entity.po.UserPO;
import com.chatx.useradmin.validate.SignIn;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * 用户接口入参
 *
 * @author Jun
 * @since 1.0.0
 */
@Data
@Accessors(chain = true)
public class UserParams {

    @NotNull(message = "用户 id 不能为空", groups = {SignIn.class})
    private Integer id;

    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)[\\s\\S]{8,20}$", message = "密码格式异常",
            groups = {Create.class, SignIn.class})
    private String passwd;

    @NotEmpty(message = "昵称不能为空", groups = {Create.class})
    @Size(message = "昵称长度 1~50 个字符", min = 1, max = 50, groups = {Create.class})
    private String nickname;

    @Size(message = "非法的区划代码", min = 6, max = 6, groups = {Create.class, Update.class})
    private String areaCode;

    @Pattern(regexp = "^1[0-9]{10}$", message = "非法的手机号码", groups = {Create.class})
    private String mobile;

    /** @see com.chatx.useradmin.constants.GenderEnum */
    @Digital(include = {1, 2}, message = "非法的性别", groups = {Create.class})
    private Integer gender;

    @NotNull(message = "生日不能为空", groups = Create.class)
    private LocalDate birthday;

    public UserPO toPO() {
        return new UserPO()
                .setNickname(nickname)
                .setMobile(mobile)
                .setPasswd(passwd)
                .setGender(gender)
                .setBirthday(birthday)
                .setAreaCode(areaCode)
                .setCreateAt(LocalDate.now());
    }
}
