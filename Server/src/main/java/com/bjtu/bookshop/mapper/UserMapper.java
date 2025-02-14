package com.bjtu.bookshop.mapper;

import java.util.List;

import com.bjtu.bookshop.bean.db.UserAddress;
import com.bjtu.bookshop.bean.db.UserInfo;
import com.bjtu.bookshop.bean.db.UserLogin;
import com.bjtu.bookshop.bean.db.UserReg;

import com.bjtu.bookshop.bean.response.UserResponses.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserMapper {

    @Select("select * from user_reg where urn = #{phone}")
    UserReg getUserRegWithPhone(String phone);

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
    void createNewUserInfo(int uid, String urn, String nickname, long regtime, String head, int viprate, int baned, int money, int role);

    @Select("select * from user_info where uid = #{uid}")
    UserInfo getUserInfoWithUserID(int uid);

    @Select("select title,content,name,phone,selected from user_address where uid = #{uid} order by plid")
    List<GetInfoResponse.loc> getAddressById(int uid);

    @Select("select sid,name as sname,1 as boss from store_info where boss = #{uid}")
    List<GetInfoResponse.elm> getShopBossedById(int uid);

    @Select("select m.sid,name as sname,0 as boss " +
            "from store_manage as m RIGHT JOIN store_info as i on m.sid = i.sid " +
            "where uid = #{uid}")
    List<GetInfoResponse.elm> getShopManagedById(int uid);

    @Update("update user_info set nickname = #{nickname} where uid = #{uid}")
    void updateUserNickname(int uid, String nickname);

    @Update("update user_info set head = #{head} where uid = #{uid}")
    void updateUserHead(int uid, String head);

    @Delete("delete from user_address where uid = #{uid}")
    void cleanAddress(int uid);

    @Insert("insert into user_address values " +
            "(#{uid},#{plid},#{title},#{content},#{name},#{phone},#{selected})")
    void insertAddress(UserAddress rcd);

    @Select("select * from user_info where uid = #{uid}")
    UserInfo getUserInfoWithUID(int uid);

    @Select("select i.uid,token,urn,nickname,regtime,viprate*0.01 as vipRate,baned,role,money*0.01 as money " +
            "from user_info as i left join user_login as l on i.uid = l.uid limit #{page}, 20")
    List<ManageListResponse.elm> getManageUserList(int page, boolean real);
    default List<ManageListResponse.elm> getManageUserList(int page){
        return getManageUserList(( page - 1 ) * 20,true);
    }

    @Select("select count(*) from user_info")
    int getUserListCount();

    @Select("select i.uid,token,urn,nickname,regtime,viprate*0.01 as vipRate,baned,role,money*0.01 as money " +
            "from user_info as i left join user_login as l on i.uid = l.uid " +
            "where urn like concat('%',#{phone},'%')")
    List<ManageSearchResponse.elm> getManageUserSearch(String phone);

    @Update("UPDATE user_info " +
            "SET urn=#{urn}, nickname=#{nickname}, head=#{head}, viprate=#{viprate}, " +
            "baned=#{baned}, money=#{money}, role=#{role} " +
            "WHERE uid = #{uid}")
    void updateUserInfo(UserInfo info);

    @Select("SELECT * FROM user_login WHERE uid = #{uid}")
    UserLogin getUserLoginInfoWithUID(int uid);

    @Update("update user_info set money = money - #{money} where uid = #{uid}")
    void updateUserMoney(int uid, int money);
}
