package com.bjtu.bookshop.controller;

import com.bjtu.bookshop.bean.request.OrderRequests.*;
import com.bjtu.bookshop.bean.response.OrderResponses.*;
import com.bjtu.bookshop.service.OrderService;
import com.bjtu.bookshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
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
    public commentResponse commentOrder(@Valid commentRequest req, BindingResult br) {
        if (br.hasErrors()) return new commentResponse() {{
            setState(-1);
        }};
        if (!userService.checkUserToken(req)) return new commentResponse() {{
            setState(-10);
        }};

        return orderService.commentOrder(req.getUid(), req.getCid(), req.getItems());
    }

    @RequestMapping(value = "/create", method = {RequestMethod.POST})
    public createResponse createOrderWithOneBook(@Valid createRequest req, BindingResult br) {
        if (br.hasErrors()) return new createResponse() {{
            setState(-1);
        }};
        if (!userService.checkUserToken(req)) return new createResponse() {{
            setState(-10);
        }};

        return orderService.createOneBookOrder(req.getUid(), req.getAddress(), req.getBid(), req.getCnt());
    }

    // 4.sub
    @RequestMapping(value = "/manage/getlist", method = {RequestMethod.POST})
    public manageGetListResponse manageGetAllOrderList(@Valid manageGetListRequest req, BindingResult br) {
        if (br.hasErrors()) return new manageGetListResponse() {{
            setState(-1);
        }};
        if (!userService.checkUserToken(req)) return new manageGetListResponse() {{
            setState(-10);
        }};

        return orderService.manageGetAllOrderList(req.getSid(), req.getType());
    }

    @RequestMapping(value = "/manage/operate", method = {RequestMethod.POST})
    public manageOperateResponse manageOperateOrder(@Valid manageOperateRequest req, BindingResult br) {
        if (br.hasErrors()) return new manageOperateResponse() {{
            setState(-1);
        }};
        if (!userService.checkUserToken(req)) return new manageOperateResponse() {{
            setState(-10);
        }};

        return orderService.manageOperateOrder(req.getCid(), req.getOpcode());
    }

    @RequestMapping(value = "/manage/setinfo", method = {RequestMethod.POST})
    public manageSetInfoResponse manageSetOrderInfo(@Valid manageSetInfoRequest req, BindingResult br) {
        if (br.hasErrors()) return new manageSetInfoResponse() {{
            setState(-1);
        }};
        if (!userService.checkUserToken(req)) return new manageSetInfoResponse() {{
            setState(-10);
        }};

        return orderService.manageSetOrderInfo(req.getCid(), req.getMoney());
    }
}
