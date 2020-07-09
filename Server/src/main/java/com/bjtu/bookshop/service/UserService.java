package com.bjtu.bookshop.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.bjtu.bookshop.bean.db.UserInfo;
import com.bjtu.bookshop.bean.db.UserLogin;
import com.bjtu.bookshop.bean.db.UserReg;
import com.bjtu.bookshop.bean.request.CommonRequests.*;
import com.bjtu.bookshop.bean.response.UserResponses.*;
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

    public boolean checkUserToken(UserAuthorization uinfo){
        UserLogin loginInfo = userMapper.getUserLoginInfoWithUID(uinfo.getUid());
        return (loginInfo != null) && (uinfo.getToken().equals(loginInfo.getToken()));
    }

    /** 1.1 body **/
    public LoginResponse userLogin(String urn, String pw) {
        UserReg reg = userMapper.getUserRegWithPhone(urn);
        if (reg == null) return new LoginResponse(){{setState(-10);}};
        if (! reg.getPwd().equals(pw)) return new LoginResponse(){{setState(-11);}};

        String token = StringUtil.getRandString();
        userMapper.updateUserLoginToken(reg.getUid(), token);
        return new LoginResponse(0, reg.getUid(), token);
    }

    /** 1.2 body **/
    public LogoutResponse userLogout(int uid) {
        String token = StringUtil.getRandString();
        userMapper.updateUserLoginToken(uid, token);
        return new LogoutResponse(0);
    }

    public RegisterResponse registerUser(String urn, String uname, String psw, String code) {
        UserReg preReg = userMapper.getUserRegWithPhone(urn);
        if (preReg != null) return new RegisterResponse(){{setState(-10);}};
        if (! "000000".equals(code)) return new RegisterResponse(){{setState(-11);}};

        String salt = StringUtil.getRandString();
        int uid = userMapper.createNewUser(urn, psw, salt);

        userMapper.createNewUserInfo(uid, urn, uname, NumberUtil.getUnixTimestamp(),
                "", 100, 0, 30000, 0);

        String token = StringUtil.getRandString();
        userMapper.updateUserLoginToken(uid, token);

        return new RegisterResponse(0,uid,token);
    }




    public Response getUserInfoWithID(int uid, String token) {
        if (isTokenValid(uid, token)) {
            UserInfo info;
            info = userMapper.getUserInfoWithUserID(uid);
            return new ItemResponse<>(info, Response.STATE_SUCCESS);
        } else return new StateResponse(Response.STATE_FAIL);
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
            return new StateResponse(Response.STATE_SUCCESS);
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
            //user.setViprate(object.getDoubleValue("vipRate"));
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

            return new ItemResponse<>(info.getUid(), Response.STATE_SUCCESS);
        } else return new StateResponse(Response.STATE_FAIL);
    }

    private boolean isTokenValid(int uid, String token) {
        UserLogin login = userMapper.getUserLoginInfoWithUID(uid);
        return token.equals(login.getToken());
    }
}
