package com.bjtu.bookshop.mapper;

import java.util.List;

import com.bjtu.bookshop.bean.db.UserInfo;
import com.bjtu.bookshop.bean.db.UserLogin;
import com.bjtu.bookshop.bean.db.UserReg;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserMapper {
    /* user_info start */

    @Select("select * from user_info where uid = #{uid}")
    UserInfo getUserInfoWithUserID(int uid);

    @Select("select * from user_info where urn = #{urn}")
    UserInfo getUserInfoWithUrn(String urn);

    @Select("select * from user_info limit #{start}, #{end}")
    List<UserInfo> getUserList(int start, int end);

    @Select("SELECT * FROM user_info WHERE urn LIKE CONCAT('%',#{urn},'%')")
    List<UserInfo> getUserInfoWithPhonePattern(String urn);

    @Insert("INSERT INTO user_info(urn, nickname, regtime, viprate, head, baned, money, role) " +
            "VALUES(#{urn}, #{nickname}, #{regtime}, #{viprate}, #{head}, #{baned}, #{money}, #{role})")
    void insertUserIntoUserInfo(UserInfo info);
    
    @Update("UPDATE user_info SET urn=#{urn}, nickname=#{nickname}, head=#{head}, viprate=#{viprate}, baned=#{baned}, money=#{money} WHERE uid = #{uid}")
    void updateUserInfo(UserInfo info);

    /* user_info end */

    /* user_reg start */

    @Insert("INSERT INTO user_info(urn, nickname, regtime, head, viprate, baned, money) " +
            "VALUES(#{urn}, #{nickname}, #{regtime}, #{head}, #{viprate}, #{baned}, #{money})")
    void insertNewUserIntoUserInfo(String urn, String nickname, long regtime, String head, double viprate, int baned, String money);


    @Select("select * from user_reg where uid = #{uid}")
    UserReg getUserRegWithUserID(int uid);

    @Insert("INSERT INTO user_reg(uid, urn, pwd, salt) VALUES(#{uid}, #{urn}, #{pwd}, #{salt})")
    void insertNewUserRegIntoUserReg(int uid, String urn, String pwd, String salt);

    /* user_reg end */

    /* user_login start */

    @Select("SELECT * FROM user_login WHERE uid = #{uid}")
    UserLogin getUserLoginInfoWithUID(int uid);

    @Insert("INSERT INTO user_login(uid, token) VALUES(#{uid}, #{token})")
    void insertNewUserLoginIntoUserLogin(int uid, String token);

    /* user_login end */

    ///////////////////////////////////////////////////////////////////////////////////
    //WJ_BEGIN
    ///////////////////////////////////////////////////////////////////////////////////

    //手机号取注册信息
    @Select("select * from user_reg where urn = #{phone}")
    UserReg getUserRegWithPhone(String phone);

    //uid token 更新token
    @Insert("insert into user_login " +
            "values (#{uid},#{token}) " +
            "ON DUPLICATE KEY UPDATE token = #{token}")
    void updateUserLoginToken(int uid, String token);

    default int createNewUser(String urn, String pwd, String salt){
        UserReg rtv = new UserReg(-1,urn,pwd,salt);
        createNewUser(rtv);
        return rtv.getUid();
    }

    @Insert("INSERT INTO user_reg(urn, pwd, salt) VALUES(#{body.urn}, #{body.pwd}, #{body.salt})")
    @Options(useGeneratedKeys = true, keyProperty = "body.uid")
    void createNewUser(@Param("body") UserReg i);

    @Insert("INSERT INTO user_info " +
            "VALUES(#{uid}, #{urn}, #{nickname}, #{regtime}, #{head}, #{viprate}, #{baned}, #{money}, #{role})")
    void createNewUserInfo(int uid, String urn, String nickname, long regtime, String head, double viprate, int baned, int money, int role);


}
