package com.bjtu.bookshop.controller;

import com.alibaba.fastjson.JSONObject;
import com.bjtu.bookshop.response.Response;
import com.bjtu.bookshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping(value = "/getlist", method = {RequestMethod.POST})
    public Response getAllOrderList(@RequestBody JSONObject object) {
        return orderService.getAllOrderList(object.getIntValue("uid"), object.getString("token"), object.getIntValue("type"));
    }

    @RequestMapping(value = "/getinfo", method = {RequestMethod.POST})
    public Response getOrderInfo(@RequestBody JSONObject object) {
        return orderService.getOrderInfo(object.getIntValue("uid"), object.getString("token"), object.getIntValue("cid"));
    }

    @RequestMapping(value = "/operate", method = {RequestMethod.POST})
    public Response operateOrder(@RequestBody JSONObject object) {
        return orderService.operateOrder(object.getIntValue("uid"), object.getString("token"), object.getIntValue("cid"), object.getIntValue("op"));
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
