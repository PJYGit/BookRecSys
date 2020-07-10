package com.bjtu.bookshop.service;

import com.bjtu.bookshop.bean.db.OrderInfo;
import com.bjtu.bookshop.bean.db.UserLogin;
import com.bjtu.bookshop.mapper.OrderMapper;
import com.bjtu.bookshop.mapper.UserMapper;
import com.bjtu.bookshop.response.ItemResponse;
import com.bjtu.bookshop.response.ListResponse;
import com.bjtu.bookshop.response.Response;
import com.bjtu.bookshop.response.StateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderMapper orderMapper;
    private final UserMapper userMapper;

    @Autowired
    public OrderService(OrderMapper orderMapper, UserMapper userMapper) {
        this.orderMapper = orderMapper;
        this.userMapper = userMapper;
    }

    public Response getAllOrderList(int uid, String token, int type) {
        if (isTokenValid(uid, token)) {
            List<OrderInfo> list = orderMapper.getAllOrderWithUIDAndType(uid, type);
            return new ListResponse<>(list, Response.STATE_SUCCESS);
        } else return new StateResponse(Response.STATE_FAIL);
    }

    public Response getOrderInfo(int uid, String token, int cid) {
        if (isTokenValid(uid, token)) {
            OrderInfo info = orderMapper.getOrderInfoWithUID(uid, cid);
            return new ItemResponse<>(info, Response.STATE_SUCCESS);
        } else return new StateResponse(Response.STATE_FAIL);
    }

    public Response operateOrder(int uid, String token, int cid, int op) {
        if (isTokenValid(uid, token)) {
            orderMapper.updateOrderInfoCode(uid, cid, op);
            return new StateResponse(Response.STATE_SUCCESS);
        } else return new StateResponse(Response.STATE_FAIL);
    }

    public Response commentOrder(int uid, String token, int cid, int mark, String comment) {
        if (isTokenValid(uid, token)) {

            return new StateResponse(Response.STATE_SUCCESS);
        } else return new StateResponse(Response.STATE_FAIL);
    }

    public Response createOneBookOrder(int uid, String token, int bid, int cnt) {
        if (isTokenValid(uid, token)) {
            orderMapper.addNewOrderIntoOrderInfo(uid, bid, cnt, 0);

            return new StateResponse(Response.STATE_SUCCESS);
        } else return new StateResponse(Response.STATE_FAIL);
    }

    private boolean isTokenValid(int uid, String token) {
        return false;
    }
}
