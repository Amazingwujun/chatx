package com.chatx.useradmin.service.impl;

import com.chatx.commons.entity.ChatxResult;
import com.chatx.useradmin.UserAdminApplicationTests;
import com.chatx.useradmin.constants.GenderEnum;
import com.chatx.useradmin.entity.params.UserParams;
import com.chatx.useradmin.entity.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@Transactional
class UserServiceImplTest extends UserAdminApplicationTests {

    @Autowired
    private UserServiceImpl userService;

    @Test
    void signUp() {
        UserParams u = new UserParams()
                .setNickname("心悦君兮")
                .setGender(GenderEnum.male.getVal())
                .setBirthday(LocalDate.now())
                .setPasswd("nani");
        var result = userService.signUp(u);
        Assertions.assertTrue(result.isOk());
    }

    @Test
    void signIn(){
        var passwd = "nani";
        UserParams u = new UserParams()
                .setNickname("心悦君兮")
                .setGender(GenderEnum.male.getVal())
                .setBirthday(LocalDate.now())
                .setPasswd(passwd);
        var result = userService.signUp(u);
        Assertions.assertTrue(result.isOk());
        var id = result.getData();

        ChatxResult<String> result1 = userService.signIn(new UserParams().setId(id).setPasswd(passwd));
        Assertions.assertTrue(result1.isOk());
        log.info("token: {}", result1.getData());
    }
}
