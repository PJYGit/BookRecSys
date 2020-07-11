package com.bjtu.bookshop.service;

import com.bjtu.bookshop.bean.db.BookInfo;
import com.bjtu.bookshop.bean.db.CarInfo;
import com.bjtu.bookshop.bean.db.OrderContent;
import com.bjtu.bookshop.bean.db.StoreInfo;
import com.bjtu.bookshop.bean.request.CarRequests.setListRequest;
import com.bjtu.bookshop.bean.request.CarRequests.submitRequest;
import com.bjtu.bookshop.bean.response.CarResponses.addBookResponse;
import com.bjtu.bookshop.bean.response.CarResponses.getListResponse;
import com.bjtu.bookshop.bean.response.CarResponses.setListResponse;
import com.bjtu.bookshop.bean.response.CarResponses.submitResponse;
import com.bjtu.bookshop.mapper.BookMapper;
import com.bjtu.bookshop.mapper.CarMapper;
import com.bjtu.bookshop.mapper.StoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class CarService {
    private final CarMapper carMapper;
    private final StoreMapper storeMapper;
    private final BookMapper bookMapper;
    private final OrderService orderService;

    @Autowired
    public CarService(CarMapper carMapper, StoreMapper storeMapper, BookMapper bookMapper, OrderService orderService) {
        this.carMapper = carMapper;
        this.storeMapper = storeMapper;
        this.bookMapper = bookMapper;
        this.orderService = orderService;
    }

    /**
     * 3.1
     * /car/getlist 查看我的购物车
     */
    public getListResponse getCarInfo(int uid) {
        List<getListResponse.elm> list = new LinkedList<>();

        List<CarInfo> carInfoList = carMapper.getCarInfoWithUID(uid);
        for (CarInfo carInfo : carInfoList) {
            BookInfo bookInfo = bookMapper.getBookInfoWithBID(carInfo.getBid());
            StoreInfo storeInfo = storeMapper.getStoreInfoWithSID(carInfo.getSid());

            getListResponse.elm elm = new getListResponse.elm(
                    carInfo.getBid(),
                    bookInfo.getName(),
                    carInfo.getSid(),
                    storeInfo.getName(),
                    bookInfo.getPic(),
                    carInfo.getCnt()
            );
            list.add(elm);
        }

        return new getListResponse(0, list);
    }

    /**
     * 3.2
     * /car/addbook 添加书
     */
    public addBookResponse addBookIntoCar(int uid, int bid, int cnt, int sid) {
        carMapper.insertIntoCarInfo(uid, bid, cnt, sid);
        return new addBookResponse(0);
    }

    /**
     * 3.3
     * /car/setlist 编辑列表
     */
    public setListResponse setCarInfo(int uid, List<setListRequest.eml> car) {
        carMapper.deleteCarInfoWithUid(uid);
        for (setListRequest.eml eml : car) {
            carMapper.insertIntoCarInfo(
                    uid,
                    eml.getBid(),
                    eml.getCnt(),
                    bookMapper.getBookInfoWithBID(eml.getBid()).getSid()
            );
        }

        return new setListResponse(0);
    }

    /**
     * 3.4
     * /car/submit 结算一部分
     */
    public submitResponse submitCarInfo(int uid, String address, List<submitRequest.eml> buy) {
        List<OrderService.BookItem> list = new LinkedList<>();
        for (submitRequest.eml eml : buy) {
            list.add(new OrderService.BookItem(eml.getBid(), eml.getCnt()));
        }
        OrderService.OrderItem orderItem = orderService.newOrderCreate(uid, address, list);

        int sid = -19260817;
        for (OrderContent content : orderItem.getContentList()) {
            int nowSid = bookMapper.getBookInfoWithBID(content.getBid()).getSid();
            if (sid == -19260817) {
                sid = nowSid;
            } else if (sid != nowSid) {
                return new submitResponse(-2333, -1);
            }
        }

        int check = orderService.newOrderCheck(orderItem);
        if (check != 0) {
            return new submitResponse(check, -1);
        }

        for (OrderContent content : orderItem.getContentList()) {
            carMapper.deleteCarInfoWithUIDAndBID(orderItem.getUid(), content.getBid());
        }

        int cid = orderService.newOrderExecute(orderItem);
        return new submitResponse(0, cid);
    }
}
