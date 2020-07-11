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
        return level <= info.getRole();
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

    /** 1.s.2 body **/
    public ManageSearchResponse searchUserWithPhone(String phone) {
        List<ManageSearchResponse.elm> list = userMapper.getManageUserSearch(phone);
        return new ManageSearchResponse(0,list);
    }

    /** 1.s.3 body **/
    public ManageGetResponse getManagedUserInfo(int targetUID) {
        UserInfo info = userMapper.getUserInfoWithUserID(targetUID);
        UserLogin loginInfo = userMapper.getUserLoginInfoWithUID(targetUID);
        if(info == null || loginInfo == null) return new ManageGetResponse(){{setState(-11);}};
        return new ManageGetResponse(0,targetUID,info.getUrn(),loginInfo.getToken(),
                info.getNickname(), info.getRegtime(),info.getViprate()*0.01,
                info.getBaned(), info.getRole(),info.getMoney()*0.01);
    }

    /** 1.s.4 body **/
    public ManageSetResponse modifyUserInfo(int mineUid, int uid, String nickname, Double vipRate,
                                            Integer baned, Double money, Integer role) {
        UserInfo userInfo = userMapper.getUserInfoWithUserID(uid);
        if(userInfo == null) return new ManageSetResponse(){{setState(-11);}};

        if(nickname != null) userInfo.setNickname(nickname);
        if(vipRate != null) userInfo.setViprate((int) Math.round(vipRate * 100));
        if(baned != null && (baned == 0 || baned == 1)) userInfo.setBaned(baned);
        if(money != null && money >= 0 ) userInfo.setMoney((int) Math.round(money * 100));
        if(role != null &&(role >= 0 && role <=1)){
            if(userInfo.getRole() == 2 || mineUid == uid){
                return new ManageSetResponse(){{setState(-12);}};
            }
            userInfo.setRole(role);
        }

        userMapper.updateUserInfo(userInfo);
        return new ManageSetResponse(0);
    }

    /** 1.s.5 body **/
    public ManageAddResponse addUser(String urn, String password, String nickname,
                                     Double vipRate, Integer role, Integer baned, Double money) {
        UserReg preReg = userMapper.getUserRegWithPhone(urn);
        if (preReg != null) return new ManageAddResponse(){{setState(-10);}};

        String salt = StringUtil.getRandString();
        int uid = userMapper.createNewUser(urn,password,salt);
        long regtime = NumberUtil.getUnixTimestamp();
        userMapper.createNewUserInfo(uid,urn,nickname,regtime,"",
                (int)Math.round(vipRate * 100),baned,
                (int)Math.round(money * 100),role);
        return new ManageAddResponse(0,uid);
    }

}
