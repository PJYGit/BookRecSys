package com.bjtu.bookshop.service;

import com.bjtu.bookshop.bean.db.BookInfo;
import com.bjtu.bookshop.bean.db.OrderContent;
import com.bjtu.bookshop.bean.db.OrderInfo;
import com.bjtu.bookshop.bean.db.UserInfo;
import com.bjtu.bookshop.bean.request.OrderRequests.commentRequest;
import com.bjtu.bookshop.bean.response.OrderResponses.*;
import com.bjtu.bookshop.mapper.BookMapper;
import com.bjtu.bookshop.mapper.OrderMapper;
import com.bjtu.bookshop.mapper.StoreMapper;
import com.bjtu.bookshop.mapper.UserMapper;
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
     * 	  用户余额 -114514
     *
     * EXEC
     * 	  订单记录
     * 	  存量和销量修改
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
                    (int) (bookMapper.getBookByBid(bookItem.getBid()).getPrice() * bookItem.getCnt() * viprate / 100)
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

    public OrderItem newOrderCreate(int uid, String address, int bid, int cnt) {
        List<BookItem> list = new LinkedList<>();
        list.add(new BookItem(bid, cnt));
        return newOrderCreate(uid, address, list);
    }

    public int newOrderCheck(OrderItem orderItem) {
        // 书的存量
        for (OrderContent orderContent : orderItem.getContentList()) {
            if (orderContent.getCnt() > bookMapper.getBookByBid(orderContent.getBid()).getRemain()) {
                return -114;
            }
        }

        // 用户余额
        if (orderItem.getInfo().getMoney() > userMapper.getUserInfoWithUID(orderItem.getInfo().getUid()).getMoney()) {
            return -114514;
        }

        return 0;
    }

    public int newOrderExecute(OrderItem orderItem) {
        orderMapper.addNewOrderIntoOrderInfo(orderItem.getInfo());

        for (OrderContent orderContent : orderItem.getContentList()) {
            orderContent.setCid(orderItem.getInfo().getCid());
            orderMapper.addNewOrderIntoOrderContent(orderContent);
        }

        for (OrderContent orderContent : orderItem.getContentList()) {
            bookMapper.updateBookSalesAndRemain(orderContent.getBid(), orderContent.getCnt());
        }

        return orderItem.getInfo().getCid();
    }
    // ========================================================

    public getListResponse getAllOrderList(int uid, int type) {
        List<getListResponse.elm> elmList = new LinkedList<>();

        List<OrderInfo> orderInfoList;
        if (type == 999) {
            orderInfoList = orderMapper.getAllOrderWithUID(uid);
        } else {
            orderInfoList = orderMapper.getAllOrderWithUIDAndType(uid, type);
        }

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

    public operateResponse operateOrder(int uid, int cid, int opcode) {
        OrderInfo orderInfo = orderMapper.getOrderInfoWithCID(cid);
        List<OrderContent> orderContentList = orderMapper.getAllOrderContentWithCID(cid);
        UserInfo userInfo = userMapper.getUserInfoWithUID(uid);

        if (orderInfo.getType() == 0 && opcode == 1) {
            if (userInfo.getMoney() < orderInfo.getMoney()) {
                return new operateResponse(-114514);
            }
            userMapper.updateUserMoney(uid, orderInfo.getMoney());
            orderMapper.updateOrderInfoType(cid, 1);
            return new operateResponse(0);
        }

        if ((orderInfo.getType() == 0 || orderInfo.getType() == 1) && opcode == 2) {
            for (OrderContent orderContent : orderContentList) {
                bookMapper.updateBookSalesAndRemain(orderContent.getBid(), -orderContent.getCnt());
            }

            if (orderInfo.getType() == 1) {
                userMapper.updateUserMoney(uid, -orderInfo.getMoney());
            }

            orderMapper.updateOrderInfoType(cid, -1);
            return new operateResponse(0);
        }

        if (orderInfo.getType() == 2 && opcode == 3) {
            orderMapper.updateOrderInfoType(cid, 3);
            return new operateResponse(0);
        }

        return new operateResponse(-777);
    }

    public commentResponse commentOrder(int uid, int cid, List<commentRequest.cmt> items) {
        OrderInfo orderInfo = orderMapper.getOrderInfoWithCID(cid);

        if (orderInfo.getType() != 3) {
            return new commentResponse(-777);
        }

        for (commentRequest.cmt cmt : items) {
            bookMapper.insertBookComment(
                    cid,
                    cmt.getBid(),
                    uid,
                    cmt.getMark(),
                    cmt.getComment()
            );
        }
        orderMapper.updateOrderInfoType(cid, 4);
        return new commentResponse(0);
    }

    public createResponse createOneBookOrder(int uid, String address, int bid, int cnt) {
        OrderItem orderItem = newOrderCreate(uid, address, bid, cnt);

        int check = newOrderCheck(orderItem);
        if (check != 0) return new createResponse(check, -1);

        int cid = newOrderExecute(orderItem);
        return new createResponse(0, cid);
    }

    // 4.sub
    public manageGetListResponse manageGetAllOrderList(int sid, int type) {
        List<manageGetListResponse.elm> elmList = new LinkedList<>();

        List<OrderInfo> orderInfoList;
        if (type == 999) {
            orderInfoList = orderMapper.getAllOrderWithSID(sid);
        } else {
            orderInfoList = orderMapper.getAllOrderWithSIDAndType(sid, type);
        }

        for (OrderInfo orderInfo : orderInfoList) {
            List<manageGetListResponse.elm.mmp> mmpList = new LinkedList<>();

            List<OrderContent> orderContentList = orderMapper.getAllOrderContentWithCID(orderInfo.getCid());
            for (OrderContent orderContent : orderContentList) {
                BookInfo bookInfo = bookMapper.getBookInfoWithBID(orderContent.getBid());
                mmpList.add(new manageGetListResponse.elm.mmp(
                        bookInfo.getBid(),
                        bookInfo.getName(),
                        orderContent.getCnt(),
                        bookInfo.getPic()
                ));
            }

            elmList.add(new manageGetListResponse.elm(
                    orderInfo.getCid(),
                    orderInfo.getType(),
                    mmpList,
                    (double) orderInfo.getMoney() / 100.0
            ));
        }

        return new manageGetListResponse(0, elmList);
    }

    public manageOperateResponse manageOperateOrder(int cid, int opcode) {
        OrderInfo orderInfo = orderMapper.getOrderInfoWithCID(cid);
        List<OrderContent> orderContentList = orderMapper.getAllOrderContentWithCID(cid);
        UserInfo userInfo = userMapper.getUserInfoWithUID(orderInfo.getUid());

        if ((orderInfo.getType() == 0 || orderInfo.getType() == 1) && opcode == 1) {
            for (OrderContent orderContent : orderContentList) {
                bookMapper.updateBookSalesAndRemain(orderContent.getBid(), -orderContent.getCnt());
            }

            if (orderInfo.getType() == 1) {
                userMapper.updateUserMoney(userInfo.getUid(), -orderInfo.getMoney());
            }

            orderMapper.updateOrderInfoType(cid, -1);
            return new manageOperateResponse(0);
        }

        if (orderInfo.getType() == 1 && opcode == 2) {
            orderMapper.updateOrderInfoType(cid, 1);
            return new manageOperateResponse(0);
        }

        return new manageOperateResponse(-777);
    }

    public manageSetInfoResponse manageSetOrderInfo(int cid, double money) {
        OrderInfo orderInfo = orderMapper.getOrderInfoWithCID(cid);
        if (orderInfo.getType() == 0) {
            orderMapper.updateOrderInfoMoney(cid, (int) (money * 100));
        }

        return new manageSetInfoResponse(-777);
    }
}
