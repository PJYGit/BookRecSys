package com.bjtu.bookshop.service;

import com.bjtu.bookshop.bean.db.OrderContent;
import com.bjtu.bookshop.bean.db.OrderInfo;
import com.bjtu.bookshop.mapper.OrderMapper;
import com.bjtu.bookshop.mapper.UserMapper;
import com.bjtu.bookshop.response.ItemResponse;
import com.bjtu.bookshop.response.ListResponse;
import com.bjtu.bookshop.response.Response;
import com.bjtu.bookshop.response.StateResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    // ========================================================
    /*
     * 直接下单
     *    CREATE
     * 	  CHECK
     * 	  EXEC
     *
     * 购物车下单
     *    CREATE
     * 	  同一商家检测
     * 	  CHECK
     * 	  购物车清空
     * 	  EXEC
     *
     * CHECK
     *    订单生成
     * 	  书的存量
     * 	  价格计算
     *
     * EXEC
     * 	  订单记录
     * 	  存量和销量修改
     * 	  用户钱包修改
     *
     * CREATE
     *     接收 uid address List<BookItem>
     * */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderItem {
        private int uid;
        private OrderInfo info;
        private List<OrderContent> contentList;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BookItem {
        private int bid;
        private int cnt;
    }

    public OrderItem newOrderCreate(int uid, String address, List<BookItem> list) {
        OrderItem orderItem = new OrderItem();
        // TODO
        return orderItem;
    }

    public int newOrderCheck(OrderItem orderItem) {
        // TODO
        // 书的存量
        return -114;
        // 价格计算
        // return -514;
    }

    public int newOrderExecute(OrderItem orderItem) {
        // TODO
        return 0;
    }
    // ========================================================

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
