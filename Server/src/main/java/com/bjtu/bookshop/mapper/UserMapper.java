package com.bjtu.bookshop.mapper;

import com.bjtu.bookshop.entity.UserInfo;
import com.bjtu.bookshop.entity.UserLogin;
import com.bjtu.bookshop.entity.UserReg;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserMapper {
    @Select("select * from user_info where uid = #{uid}")
    UserInfo getUserInfoWithUserID(int uid);

    @Select("select * from user_info where urn = #{urn}")
    UserInfo getUserInfoWithUrn(String urn);

    @Insert("INSERT INTO user_info(urn, regtime, viprate, baned, money) VALUES(#{urn}, #{regtime}, #{viprate}, #{baned}, #{money})")
    void insertNewUserIntoUserInfo(String urn, long regtime, String viprate, Integer baned, String money);
    
    @Select("select * from user_reg where uid = #{uid}")
    UserReg getUserRegWithUserID(int uid);

    @Insert("INSERT INTO user_reg(uid, urn, pwd, salt) VALUES(#{uid}, #{urn}, #{pwd}, #{salt})")
    void insertNewUserRegIntoUserReg(int uid, String urn, String pwd, String salt);

    @Select("SELECT * FROM user_login WHERE uid = #{uid}")
    UserLogin getUserLoginInfoWithUID(int uid);

    @Insert("INSERT INTO user_login(uid, token) VALUES(#{uid}, #{token})")
    void insertNewUserLoginIntoUserLogin(int uid, String token);

    @Update("UPDATE user_login SET token = #{token} WHERE uid = #{uid}")
    void updateUserLoginToken(int uid, String token);
}