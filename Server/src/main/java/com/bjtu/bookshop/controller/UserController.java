package com.bjtu.bookshop.controller;

import com.alibaba.fastjson.JSONObject;
import com.bjtu.bookshop.bean.db.UserInfo;
import com.bjtu.bookshop.bean.request.UserRequests;
import com.bjtu.bookshop.bean.request.UserRequests.*;
import com.bjtu.bookshop.bean.response.UserResponses.*;
import com.bjtu.bookshop.response.Response;
import com.bjtu.bookshop.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 1.1
     * user/login 用户登录
     */
    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    public LoginResponse userLogin(@Valid LoginRequest req, BindingResult br) {
        if(br.hasErrors()) return new LoginResponse(){{setState(-1);}};
        return userService.userLogin(req.getUrn(), req.getPw());
    }

    /**
     * 1.2
     * user/logout 用户登出
     */
    @RequestMapping(value = "/logout", method = {RequestMethod.POST})
    public LogoutResponse userLogout(@Valid LogoutRequest req, BindingResult br) {
        if(br.hasErrors()) return new LogoutResponse(){{setState(-1);}};
        if(! userService.checkUserToken(req)) return new LogoutResponse(){{setState(-10);}};
        return userService.userLogout(req.getUid());
    }

    /**
     * 1.3
     * /user/register 用户注册
     */
    @RequestMapping(value = "/register", method = {RequestMethod.POST})
    public RegisterResponse userRegister(@Valid RegisterRequest req, BindingResult br) {
        if(br.hasErrors()) return new RegisterResponse(){{setState(-1);}};
        if(req.getUname() == null || req.getUname().isEmpty()) req.setUname(req.getUrn());
        return userService.registerUser(req.getUrn(), req.getUname(), req.getPsw(),req.getCode());
    }

    /**
     * 1.4
     * /user/phone 发送手机验证码
     */
    @RequestMapping(value = "/phone", method = {RequestMethod.POST})
    public PhoneResponse userPhone(@Valid PhoneRequest req, BindingResult br) {
        if(br.hasErrors()) return new PhoneResponse(){{setState(-1);}};
        return new PhoneResponse(0);
    }

    /**
     * 1.5
     * /user/getinfo 取本用户基本信息
     */
    @RequestMapping(value = "/getinfo", method = {RequestMethod.POST})
    public GetInfoResponse getUserInfo(@Valid GetInfoRequest req, BindingResult br) {
        if(br.hasErrors()) return new GetInfoResponse(){{setState(-1);}};
        if(! userService.checkUserToken(req)) return new GetInfoResponse(){{setState(-10);}};
        return userService.getUserInfo(req.getUid());
    }

    /**
     * 1.6
     * /user/setinfo 设置用户基本信息
     */
    @RequestMapping(value = "/setinfo", method = {RequestMethod.POST})
    public SetInfoResponse updateUserInfo(@Valid SetInfoRequest req, BindingResult br) {
        if(br.hasErrors()) return new SetInfoResponse(){{setState(-1);}};
        if(! req.trans()) return new SetInfoResponse(){{setState(-1);}};

        for(int i=0;i<req.getAddress().size();i++){
            System.out.println(req.getAddress().get(i));
        }

        //UserInfo info = object.getObject("data", UserInfo.class);
        return userService.updateUserInfo(
                req.getUid(),req.getNickname(),req.getHead(),req.getInnerAddress());
    }

    @RequestMapping(value = "/manage/list", method = {RequestMethod.POST})
    public Response getUserList(@RequestBody JSONObject object) {
        return userService.getUserList(object.getIntValue("uid"), object.getString("token"), object.getIntValue("page"));
    }

    @RequestMapping(value = "/manage/getinfo", method = {RequestMethod.POST})
    public Response getUserInfoM(@RequestBody JSONObject object) {
        return userService.getUserInfo(object.getIntValue("uid"), object.getString("token"), object.getIntValue("target"));
    }

    @RequestMapping(value = "/manage/setinfo", method = {RequestMethod.POST})
    public Response modifyUserInfo(@RequestBody JSONObject object) {
        UserInfo info = object.getObject("data", UserInfo.class);
        return userService.modifyUserInfo(object.getIntValue("uid"), object.getString("token"), object.getIntValue("target"), info);
    }

    @RequestMapping(value = "/manage/search", method = {RequestMethod.POST})
    public Response searchUser(@RequestBody JSONObject object) {
        return userService.searchUserWithPhone(object.getIntValue("uid"), object.getString("token"), object.getString("phone"));
    }

    @RequestMapping(value = "/manage/adduser", method = {RequestMethod.POST})
    public Response addUser(@RequestBody JSONObject object) {
        return userService.addUser(object);
    }
}
