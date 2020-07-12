package com.bjtu.bookshop.service;

import com.bjtu.bookshop.bean.db.BookInfo;
import com.bjtu.bookshop.bean.db.OrderContent;
import com.bjtu.bookshop.bean.db.OrderInfo;
import com.bjtu.bookshop.bean.response.OrderResponses.getInfoResponse;
import com.bjtu.bookshop.bean.response.OrderResponses.getListResponse;
import com.bjtu.bookshop.mapper.BookMapper;
import com.bjtu.bookshop.mapper.OrderMapper;
import com.bjtu.bookshop.mapper.StoreMapper;
import com.bjtu.bookshop.mapper.UserMapper;
import com.bjtu.bookshop.response.Response;
import com.bjtu.bookshop.response.StateResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class OrderService {
    private final OrderMapper orderMapper;
    private final BookMapper bookMapper;
    private final UserMapper userMapper;
    private final StoreMapper storeMapper;

    @Autowired
    public OrderService(OrderMapper orderMapper, BookMapper bookMapper, UserMapper userMapper, StoreMapper storeMapper) {
        this.orderMapper = orderMapper;
        this.bookMapper = bookMapper;
        this.userMapper = userMapper;
        this.storeMapper = storeMapper;
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
     * 	  书的存量 -114
     * 	  用户余额 -514
     *
     * EXEC
     * 	  订单记录
     * 	  存量和销量修改
     * 	  用户钱包修改
     *
     * CREATE
     *     接收 uid address List<BookItem>
     *     cid为0
     *     计算并构造
     * */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderItem {
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
        int viprate = userMapper.getUserInfoWithUserID(uid).getViprate();
        int sid = bookMapper.getBookInfoWithBID(list.get(0).getBid()).getSid();
        int money = 0;

        List<OrderContent> contentList = new LinkedList<>();
        for (BookItem bookItem : list) {
            OrderContent orderContent = new OrderContent(
                    -1,
                    bookItem.getBid(),
                    bookItem.getCnt(),
                    bookItem.getBid() * bookItem.getCnt() * viprate / 100
            );
            contentList.add(orderContent);
            money += orderContent.getMoney();
        }

        OrderInfo orderInfo = new OrderInfo(
                -1,
                uid,
                sid,
                0,
                money,
                address
        );

        return new OrderItem(orderInfo, contentList);
    }

    public int newOrderCheck(OrderItem orderItem) {
        // 书的存量
        for (OrderContent orderContent : orderItem.getContentList()) {
            if (orderContent.getCid() < bookMapper.getBookByBid(orderContent.getBid()).getRemain()) {
                return -114;
            }
        }

        // 用户余额
        if (orderItem.getInfo().getMoney() > userMapper.getUserInfoWithUID(orderItem.getInfo().getUid()).getMoney()) {
            return -514;
        }

        return 0;
    }

    public int newOrderExecute(OrderItem orderItem) {
        orderMapper.addNewOrderIntoOrderInfo(orderItem.getInfo());
        for (OrderContent orderContent : orderItem.getContentList()) {
            orderMapper.addNewOrderIntoOrderContent(orderContent);
        }

        for (OrderContent orderContent : orderItem.getContentList()) {
            bookMapper.updateBookSalesAndRemain(orderContent.getBid(), orderContent.getCnt());
        }

        userMapper.updateUserMoney(orderItem.getInfo().getUid(), orderItem.getInfo().getMoney());

        // TODO test
        return orderItem.getInfo().getCid();
    }
    // ========================================================

    public getListResponse getAllOrderList(int uid, int type) {
        List<getListResponse.elm> elmList = new LinkedList<>();

        List<OrderInfo> orderInfoList = orderMapper.getAllOrderWithUIDAndType(uid, type);
        for (OrderInfo orderInfo : orderInfoList) {
            List<getListResponse.elm.smp> smpList = new LinkedList<>();

            List<OrderContent> orderContentList = orderMapper.getAllOrderContentWithCID(orderInfo.getCid());
            for (OrderContent orderContent : orderContentList) {
                smpList.add(new getListResponse.elm.smp(
                        bookMapper.getBookInfoWithBID(orderContent.getBid()).getName(),
                        orderContent.getCnt()
                ));
            }

            elmList.add(new getListResponse.elm(
                    orderInfo.getCid(),
                    orderInfo.getType(),
                    orderInfo.getSid(),
                    storeMapper.getStoreInfoWithSID(orderInfo.getSid()).getName(),
                    bookMapper.getBookByBid(orderContentList.get(0).getBid()).getPic(),
                    smpList
            ));
        }

        return new getListResponse(0, elmList);
    }

    public getInfoResponse getOrderInfo(int cid) {
        List<getInfoResponse.cfd> cfdList = new LinkedList<>();

        List<OrderContent> orderContentList = orderMapper.getAllOrderContentWithCID(cid);
        for (OrderContent orderContent : orderContentList) {
            BookInfo bookInfo = bookMapper.getBookInfoWithBID(orderContent.getBid());
            cfdList.add(new getInfoResponse.cfd(
                    orderContent.getBid(),
                    bookInfo.getName(),
                    orderContent.getCnt(),
                    bookInfo.getPic(),
                    (double) orderContent.getMoney() / 100.0
            ));
        }

        OrderInfo orderInfo = orderMapper.getOrderInfoWithCID(cid);
        return new getInfoResponse(
                0,
                orderInfo.getCid(),
                orderInfo.getType(),
                orderInfo.getSid(),
                storeMapper.getStoreInfoWithSID(orderInfo.getSid()).getName(),
                orderInfo.getAddress(),
                cfdList
        );
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
//            orderMapper.addNewOrderIntoOrderInfo(uid, bid, cnt, 0);

            return new StateResponse(Response.STATE_SUCCESS);
        } else return new StateResponse(Response.STATE_FAIL);
    }

    private boolean isTokenValid(int uid, String token) {
        return false;
    }
}
