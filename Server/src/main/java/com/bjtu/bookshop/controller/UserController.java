package com.bjtu.bookshop.controller;

import com.alibaba.fastjson.JSONObject;
import com.bjtu.bookshop.entity.UserInfo;
import com.bjtu.bookshop.response.Response;
import com.bjtu.bookshop.response.StateResponse;
import com.bjtu.bookshop.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/register", method = {RequestMethod.POST})
    public Response userRegister(@RequestBody JSONObject object) {
        if (object.getString("code").equals("000000")) {
            return userService.registerUser(object.getString("urn"), object.getString("uname"));
        } else {
            return new StateResponse(Response.STATE_FAIL);
        }
    }

    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    public Response userLogin(@RequestBody JSONObject object) {
        return userService.userLogin(object.getString("urn"), object.getString("pw"));
    }

    @RequestMapping(value = "/logout", method = {RequestMethod.POST})
    public Response userLogout(@RequestBody JSONObject object) {
        return userService.userLogout(object.getIntValue("uid"), object.getString("token"));
    }

    @RequestMapping(value = "/getinfo", method = {RequestMethod.GET})
    public Response getUserInfo(@RequestParam Integer uid, @RequestParam String token) {
        return userService.getUserInfoWithID(uid);
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
    public Response getUserInfo(@RequestBody JSONObject object) {
        return userService.getUserInfo(object.getIntValue("uid"), object.getString("token"), object.getIntValue("targetUid"));
    }

    @RequestMapping(value = "/manage/setinfo", method = {RequestMethod.POST})
    public Response modifyUserInfo(@RequestBody JSONObject object) {
        UserInfo info = object.getObject("data", UserInfo.class);
        return userService.modifyUserInfo(object.getIntValue("uid"), object.getString("token"), object.getIntValue("targetUid"), info);
    }

    @RequestMapping(value = "/manage/search", method = {RequestMethod.POST})
    public Response searchUser(@RequestBody JSONObject object) {
        return userService.searchUserWithPhone(object.getIntValue("uid"), object.getString("token"), object.getString("phone"));
    }
}
