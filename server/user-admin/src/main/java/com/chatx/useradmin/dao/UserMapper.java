package com.chatx.useradmin.dao;

import com.chatx.useradmin.entity.po.UserPO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 用户 dao
 *
 * @author Jun
 * @since 1.0.0
 */
@Mapper
public interface UserMapper {

    int insertOne(UserPO createPO);

    @Select("""
            select 
            id, nickname, mobile, passwd, gender, birthday, area_code, create_at
            from t_user
            where id = #{uid}
            """)
    UserPO selectById(Integer uid);
}
