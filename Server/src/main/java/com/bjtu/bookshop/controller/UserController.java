package com.bjtu.bookshop.controller;

import com.alibaba.fastjson.JSONObject;
import com.bjtu.bookshop.bean.db.UserInfo;
import com.bjtu.bookshop.bean.request.UserRequests.*;
import com.bjtu.bookshop.bean.response.UserResponses.*;
import com.bjtu.bookshop.response.Response;
import com.bjtu.bookshop.response.StateResponse;
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
     * user/login
     */
    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    public LoginResponse userLogin(@Valid LoginRequest req, BindingResult br) {
        if(br.hasErrors()) return LoginResponse.FailWith(-1);
        return userService.userLogin(req.getUrn(), req.getPw());
    }

    /**
     * 1.2
     * user/logout
     */
    @RequestMapping(value = "/logout", method = {RequestMethod.POST})
    public LogoutResponse userLogout(@Valid LogoutRequest req, BindingResult br) {
        if(br.hasErrors()) return LogoutResponse.FailWith(-1);
        if(! userService.checkUserToken(req)) return LogoutResponse.FailWith(-10);
        return userService.userLogout(req.getUid());
    }

    @RequestMapping(value = "/register", method = {RequestMethod.POST})
    public Response userRegister(@RequestBody JSONObject object) {
        if (object.getString("code").equals("000000")) {
            return userService.registerUser(object.getString("urn"), object.getString("uname"), object.getString("psw"));
        } else {
            return new StateResponse(Response.STATE_FAIL);
        }
    }



    @RequestMapping(value = "/getinfo", method = {RequestMethod.POST})
    public Response getUserInfo(@RequestBody JSONObject object) {
        return userService.getUserInfoWithID(object.getIntValue("uid"), object.getString("token"));
    }

    @RequestMapping(value = "/setinfo", method = {RequestMethod.POST})
    public Response updateUserInfo(@RequestBody JSONObject object) {
        UserInfo info = object.getObject("data", UserInfo.class);
        return userService.updateUserInfo(object.getIntValue("uid"), object.getString("token"), info);
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
