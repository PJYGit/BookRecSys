package com.bjtu.bookshop.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.bjtu.bookshop.bean.db.UserAddress;
import com.bjtu.bookshop.bean.db.UserInfo;
import com.bjtu.bookshop.bean.db.UserLogin;
import com.bjtu.bookshop.bean.db.UserReg;
import com.bjtu.bookshop.bean.request.CommonRequests.*;
import com.bjtu.bookshop.bean.request.UserRequests.*;
import com.bjtu.bookshop.bean.response.UserResponses.*;
import com.bjtu.bookshop.mapper.UserMapper;
import com.bjtu.bookshop.response.ItemResponse;
import com.bjtu.bookshop.response.ListResponse;
import com.bjtu.bookshop.response.Response;
import com.bjtu.bookshop.response.StateResponse;
import com.bjtu.bookshop.util.CacheUtil.*;
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

    public boolean checkUserRole(int uid, int level){
        UserInfo info = userMapper.getUserInfoWithUID(uid);
        assert (info != null);
        return level >= info.getRole();
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

    /** 1.3 body **/
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

    /** 1.5 body **/
    public GetInfoResponse getUserInfo(int uid){
        UserInfo info = userMapper.getUserInfoWithUserID(uid);
        List<GetInfoResponse.loc> listAddress = userMapper.getAddressById(uid);
        List<GetInfoResponse.elm> listBoss = userMapper.getShopBossedById(uid);
        List<GetInfoResponse.elm> listManage = userMapper.getShopManagedById(uid);
        listManage.addAll(listBoss);

        return new GetInfoResponse(0,uid,info.getUrn(),info.getNickname(),
                info.getRole(),info.getHead(),info.getRegtime(),
                info.getViprate() * 0.01, info.getMoney() * 0.01,
                listAddress, listManage);
    }

    /** 1.6 body **/
    public SetInfoResponse updateUserInfo(
            int uid, String nickname, String head, List<SetInfoRequest.loc> address) {

        if(nickname != null) userMapper.updateUserNickname(uid, nickname);
        if(head != null) userMapper.updateUserHead(uid, head);
        if(address != null) {
            userMapper.cleanAddress(uid);
            for(int i=0;i<address.size();i++){
                SetInfoRequest.loc elm = address.get(i);
                UserAddress rcd = new UserAddress(uid,i,elm.getTitle(),elm.getContent(),
                        elm.getName(),elm.getPhone(),elm.getSelected());
                userMapper.insertAddress(rcd);
            }
        }
        System.out.println(address);

        return new SetInfoResponse(0);
    }


    static TimeCache<Integer> ManageListResponseListCache = new TimeCache<Integer>();
    /** 1.s.1 body **/
    public ManageListResponse getUserList(int page) {
        if(!ManageListResponseListCache.available()){
            ManageListResponseListCache.update((userMapper.getUserListCount() + 19) / 20);
        }
        int pageCnt = ManageListResponseListCache.get();
        page = Math.max(1, page);
        List<ManageListResponse.elm> list = userMapper.getManageUserList(page);
        return new ManageListResponse(0,pageCnt,list);
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
            //if (info.getMoney() == null) info.setMoney(old.getMoney());

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
            //user.setMoney(object.getString("money"));
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
