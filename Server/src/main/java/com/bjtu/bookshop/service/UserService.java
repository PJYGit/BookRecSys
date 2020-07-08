package com.bjtu.bookshop.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.bjtu.bookshop.entity.UserInfo;
import com.bjtu.bookshop.entity.UserLogin;
import com.bjtu.bookshop.entity.UserReg;
import com.bjtu.bookshop.mapper.UserMapper;
import com.bjtu.bookshop.response.ItemResponse;
import com.bjtu.bookshop.response.ListResponse;
import com.bjtu.bookshop.response.Response;
import com.bjtu.bookshop.response.StateResponse;
import com.bjtu.bookshop.util.NumberUtil;
import com.bjtu.bookshop.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserMapper userMapper;

    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public Response registerUser(String urn, String uname, String psw) {
        // 添加新用户到用户消息表
        userMapper.insertNewUserIntoUserInfo(urn, NumberUtil.getUnixTimestamp(), 1.0, 0, "0");
        // 获取新用户的 uid
        UserInfo info = userMapper.getUserInfoWithUrn(urn);
        String salt = StringUtil.MD5("000000");
        userMapper.insertNewUserRegIntoUserReg(info.getUid(), urn, "000000", salt);

        // 生成 token
        String token = StringUtil.getRandString("000000" + salt);
        userMapper.insertNewUserLoginIntoUserLogin(info.getUid(), token);

        UserLogin login = userMapper.getUserLoginInfoWithUID(info.getUid());
        return new ItemResponse<>(login, Response.STATE_SUCCESS);
    }

    public Response userLogin(String urn, String pw) {
        UserInfo info = userMapper.getUserInfoWithUrn(urn);
        if (info == null) return new StateResponse(500);
        UserReg reg = userMapper.getUserRegWithUserID(info.getUid());

        String tmp = StringUtil.MD5(pw);

        if (tmp.equals(reg.getSalt())) {
            userMapper.updateUserLoginToken(info.getUid(), StringUtil.getRandString(pw + tmp));
            UserLogin login = userMapper.getUserLoginInfoWithUID(info.getUid());
            return new ItemResponse<>(login, Response.STATE_SUCCESS);
        } else {
            return new StateResponse(Response.STATE_FAIL);
        }
    }

    public Response userLogout(int uid, String token) {
        if (isTokenValid(uid, token)) {
            userMapper.updateUserLoginToken(uid, StringUtil.getRandString(token));
            return new StateResponse(Response.STATE_SUCCESS);
        } else return new StateResponse(Response.STATE_FAIL);
    }

    public Response getUserInfoWithID(Integer uid) {
        UserInfo info;
        if (uid == null)
            info = null;
        else
            info = userMapper.getUserInfoWithUserID(uid);
        return new ItemResponse<UserInfo>(info, Response.STATE_SUCCESS);
    }

    public Response updateUserInfo(int uid, String token, UserInfo info) {
        if (isTokenValid(uid, token)) {
            UserInfo old = userMapper.getUserInfoWithUserID(uid);

            if (info.getUid() == 0) info.setUid(old.getUid());
            if (info.getUrn() == null) info.setUrn(old.getUrn());
            if (info.getNickname() == null) info.setNickname(old.getNickname());
            if (info.getRegtime() == 0) info.setRegtime(old.getRegtime());
            if (info.getHead() == null) info.setHead(old.getHead());
            if (info.getViprate() == 0) info.setViprate(old.getViprate());
            if (info.getBaned() == 0) info.setBaned(old.getBaned());
            if (info.getMoney() == null) info.setMoney(old.getMoney());

            userMapper.updateUserInfo(info);
            return new StateResponse(Response.STATE_SUCCESS);
        } else return new StateResponse(Response.STATE_FAIL);
    }

    public Response getUserList(int uid, String token, int page) {
        if (isTokenValid(uid, token)) {
            List<UserInfo> userInfos;
            if (page <= 0) page = 1;
            userInfos = userMapper.getUserList((page - 1) * 20, page * 20);
            return new ListResponse<>(userInfos, Response.STATE_SUCCESS);
        } else return new StateResponse(Response.STATE_FAIL);
    }

    public Response getUserInfo(int uid, String token, int targetUID) {
        if (isTokenValid(uid, token)) {
            int role = userMapper.getUserInfoWithUserID(uid).getRole();
            if (role == 0) return new StateResponse(Response.STATE_FAIL);

            UserInfo info = userMapper.getUserInfoWithUserID(targetUID);
            return new ItemResponse<>(info, Response.STATE_SUCCESS);
        } else return new StateResponse(Response.STATE_FAIL);
    }

    public Response modifyUserInfo(int uid, String token, int targetUID, UserInfo info) {
        if (isTokenValid(uid, token)) {

            int role = userMapper.getUserInfoWithUserID(uid).getRole();
            if (role == 0) return new StateResponse(Response.STATE_FAIL);

            UserInfo old = userMapper.getUserInfoWithUserID(targetUID);

            if (info.getUid() == 0) info.setUid(old.getUid());
            if (info.getUrn() == null) info.setUrn(old.getUrn());
            if (info.getNickname() == null) info.setNickname(old.getNickname());
            if (info.getRegtime() == 0) info.setRegtime(old.getRegtime());
            if (info.getHead() == null) info.setHead(old.getHead());
            if (info.getViprate() == 0) info.setViprate(old.getViprate());
            if (info.getBaned() == 0) info.setBaned(old.getBaned());
            if (info.getMoney() == null) info.setMoney(old.getMoney());

            userMapper.updateUserInfo(info);
            return new ItemResponse<>(info, Response.STATE_SUCCESS);
        } else return new StateResponse(Response.STATE_FAIL);
    }

    public Response searchUserWithPhone(int uid, String token, String phone) {
        if (isTokenValid(uid, token)) {
            int role = userMapper.getUserInfoWithUserID(uid).getRole();
            if (role == 0) return new StateResponse(Response.STATE_FAIL);

            List<UserInfo> userInfos = userMapper.getUserInfoWithPhonePattern(phone);

            return new ListResponse<>(userInfos, Response.STATE_SUCCESS);
        } else return new StateResponse(Response.STATE_FAIL);
    }

    public Response addUser(JSONObject object) {
        int uid = object.getIntValue("uid");
        String token = object.getString("token");
        if (isTokenValid(uid, token)) {
            UserInfo role = userMapper.getUserInfoWithUserID(uid);
            if (role.getRole() == 0) return new StateResponse(Response.STATE_FAIL);

            UserInfo user = new UserInfo();
            String psw = object.getString("password");
            user.setNickname(object.getString("nickname"));
            user.setRegtime(NumberUtil.getUnixTimestamp());
            user.setViprate(object.getDoubleValue("vipRate"));
            user.setRole(object.getIntValue("role"));
            user.setMoney(object.getString("money"));
            user.setBaned(object.getIntValue("baned"));
            String urn = object.getString("urn");
            user.setUrn(urn);

            userMapper.insertUserIntoUserInfo(user);
            UserInfo info = userMapper.getUserInfoWithUrn(urn);
            String salt = StringUtil.MD5(psw);
            userMapper.insertNewUserRegIntoUserReg(info.getUid(), urn, psw, salt);

            String uToken = StringUtil.getRandString(psw + salt);
            userMapper.insertNewUserLoginIntoUserLogin(info.getUid(), uToken);

            return new ItemResponse<>(0 , Response.STATE_SUCCESS);
        } else return new StateResponse(Response.STATE_FAIL);
    }

    private boolean isTokenValid(int uid, String token) {
        UserLogin login = userMapper.getUserLoginInfoWithUID(uid);
        return token.equals(login.getToken());
    }
}
