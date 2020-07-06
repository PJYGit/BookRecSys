package com.bjtu.bookshop.controller;

import com.alibaba.fastjson.JSONObject;
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
    
    @Autowired
    UserService userService;

    @RequestMapping(value = "/info", method = {RequestMethod.GET})
    public Response userLogin(@RequestParam Integer uid, @RequestParam String token) {
        return userService.getUserInfoWithID(uid);
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
}