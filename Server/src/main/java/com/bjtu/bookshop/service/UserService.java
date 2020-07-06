package com.bjtu.bookshop.service;

import com.bjtu.bookshop.entity.UserInfo;
import com.bjtu.bookshop.entity.UserLogin;
import com.bjtu.bookshop.entity.UserReg;
import com.bjtu.bookshop.mapper.UserMapper;
import com.bjtu.bookshop.response.ItemResponse;
import com.bjtu.bookshop.response.Response;
import com.bjtu.bookshop.response.StateResponse;
import com.bjtu.bookshop.util.NumberUtil;
import com.bjtu.bookshop.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public Response getUserInfoWithID(Integer uid) {
        UserInfo info;
        if (uid == null)
            info = null;
        else
            info = userMapper.getUserInfoWithUserID(uid);
        return new ItemResponse<UserInfo>(info, Response.STATE_SUCCESS);
    }

    public Response registerUser(String urn, String uname) {
        // 添加新用户到用户消息表
        userMapper.insertNewUserIntoUserInfo(urn, NumberUtil.getUnixTimestamp(), "1.0", 0, "0");
        // 获取新用户的 uid
        UserInfo info = userMapper.getUserInfoWithUrn(urn);
        String salt = StringUtil.MD5("000000");
        userMapper.insertNewUserRegIntoUserReg(info.getUid(), urn, "000000", salt);

        // 生成 token
        String token = StringUtil.getRandString("000000" + salt);
        userMapper.insertNewUserLoginIntoUserLogin(info.getUid(), token);

        UserLogin login = userMapper.getUserLoginInfoWithUID(info.getUid());
        return new ItemResponse<UserLogin>(login, Response.STATE_SUCCESS);
    }

    public Response userLogin(String urn, String pw) {
        UserInfo info = userMapper.getUserInfoWithUrn(urn);
        UserReg reg = userMapper.getUserRegWithUserID(info.getUid());

        String tmp = StringUtil.MD5(pw);

        if (tmp.equals(reg.getSalt())) {
            userMapper.updateUserLoginToken(info.getUid(), StringUtil.getRandString(pw + reg.getSalt()));
            UserLogin login = userMapper.getUserLoginInfoWithUID(info.getUid());
            return new ItemResponse<UserLogin>(login, Response.STATE_SUCCESS);
        } else {
            return new StateResponse(Response.STATE_FAIL);
        }
    }
}
