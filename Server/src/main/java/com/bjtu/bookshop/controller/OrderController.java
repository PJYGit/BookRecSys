package com.bjtu.bookshop.controller;

import com.alibaba.fastjson.JSONObject;
import com.bjtu.bookshop.bean.request.OrderRequests.*;
import com.bjtu.bookshop.bean.response.CarResponses;
import com.bjtu.bookshop.bean.response.OrderResponses.*;
import com.bjtu.bookshop.response.Response;
import com.bjtu.bookshop.service.OrderService;
import com.bjtu.bookshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;

    @Autowired
    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @RequestMapping(value = "/getlist", method = {RequestMethod.POST})
    public getListResponse getAllOrderList(@Valid getListRequest req, BindingResult br) {
        if (br.hasErrors()) return new getListResponse() {{
            setState(-1);
        }};
        if (!userService.checkUserToken(req)) return new getListResponse() {{
            setState(-10);
        }};

        return orderService.getAllOrderList(req.getUid(), req.getType());
    }

    @RequestMapping(value = "/getinfo", method = {RequestMethod.POST})
    public getInfoResponse getOrderInfo(@Valid getInfoRequest req, BindingResult br) {
        if (br.hasErrors()) return new getInfoResponse() {{
            setState(-1);
        }};
        if (!userService.checkUserToken(req)) return new getInfoResponse() {{
            setState(-10);
        }};

        return orderService.getOrderInfo(req.getCid());
    }

    @RequestMapping(value = "/operate", method = {RequestMethod.POST})
    public operateResponse operateOrder(@Valid operateRequest req, BindingResult br) {
        if (br.hasErrors()) return new operateResponse() {{
            setState(-1);
        }};
        if (!userService.checkUserToken(req)) return new operateResponse() {{
            setState(-10);
        }};

        return orderService.operateOrder(req.getUid(), req.getCid(), req.getOpcode());
    }

    @RequestMapping(value = "/comment", method = {RequestMethod.POST})
    public Response commentOrder(@RequestBody JSONObject object) {
        return orderService.commentOrder(object.getIntValue("uid"), object.getString("token"), object.getIntValue("cid"), object.getIntValue("mark"), object.getString("comment"));
    }

    @RequestMapping(value = "/create", method = {RequestMethod.POST})
    public Response createOrderWithOneBook(@RequestBody JSONObject object) {
        return orderService.createOneBookOrder(object.getIntValue("uid"), object.getString("token"), object.getIntValue("bid"), object.getIntValue("cnt"));
    }
}
