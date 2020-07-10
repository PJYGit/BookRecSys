package com.bjtu.bookshop.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bjtu.bookshop.bean.db.BookInfo;
import com.bjtu.bookshop.bean.db.CarInfo;
import com.bjtu.bookshop.bean.db.StoreInfo;
import com.bjtu.bookshop.mapper.*;
import com.bjtu.bookshop.response.ListResponse;
import com.bjtu.bookshop.response.Response;
import com.bjtu.bookshop.response.StateResponse;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * @author 李高丞
 * @version 1.0
 */
@Service
public class CarService {
    private final CarMapper carMapper;
    private final UserMapper userMapper;
    private final StoreMapper storeMapper;
    private final BookMapper bookMapper;
    private final OrderMapper orderMapper;

    @Autowired
    public CarService(CarMapper carMapper, UserMapper userMapper, StoreMapper storeMapper, BookMapper bookMapper, OrderMapper orderMapper) {
        this.carMapper = carMapper;
        this.userMapper = userMapper;
        this.storeMapper = storeMapper;
        this.bookMapper = bookMapper;
        this.orderMapper = orderMapper;
    }

    public Response getCarInfo(int uid, String token) {
        if (isTokenValid(uid, token)) {
            List<CarInfo> carInfos = carMapper.getCarInfoWithUID(uid);
            @Data
            class BookPartInfo {
                private int bid;
                private String bname;
                private int sid;
                private String sname;
                private String pic;
                private int cnt;
            }
            List<BookPartInfo> list = new LinkedList<>();
            for (CarInfo info : carInfos) {
                BookInfo bookInfo = bookMapper.getBookInfoWithBID(info.getBid());
                StoreInfo storeInfo = storeMapper.getStoreInfoWithSID(info.getSid());
                BookPartInfo bookPartInfo = new BookPartInfo();
                bookPartInfo.setBid(info.getBid());
                bookPartInfo.setBname(bookInfo.getName());
                bookPartInfo.setSid(info.getSid());
                bookPartInfo.setSname(storeInfo.getName());
                bookPartInfo.setPic(bookInfo.getPic());
                bookPartInfo.setCnt(info.getCnt());
                list.add(bookPartInfo);
            }
            return new ListResponse<>(list, Response.STATE_SUCCESS);
        } else return new StateResponse(Response.STATE_FAIL);
    }

    public Response addBookIntoCar(int uid, String token, JSONObject object) {
        if (isTokenValid(uid, token)) {
            carMapper.insertIntoCarInfo(uid, object.getIntValue("bid"), object.getIntValue("sid"), object.getIntValue("cnt"));
            return new StateResponse(Response.STATE_SUCCESS);
        } else return new StateResponse(Response.STATE_FAIL);
    }

    public Response updateCarInfo(int uid, String token, JSONObject object) {
        if (isTokenValid(uid, token)) {
            JSONArray array = object.getJSONArray("car");
            for (int i = 0; i < array.size(); i++) {
                JSONObject obj = array.getJSONObject(i);
                carMapper.updateCarInfo(uid, obj.getIntValue("bid"), obj.getIntValue("cnt"));
            }
            return new StateResponse(Response.STATE_SUCCESS);
        } else return new StateResponse(Response.STATE_FAIL);
    }

    public Response submitCarInfo(int uid, String token, JSONObject object) {
        if (isTokenValid(uid, token)) {
            JSONArray array = object.getJSONArray("buy");
            List<CarInfo> list = carMapper.getCarInfoWithUID(uid);
            for (int i = 0; i < array.size(); i++) {
                JSONObject obj = array.getJSONObject(i);
                int bid = obj.getIntValue("bid");
                int oldCnt = 0;
                for (CarInfo carInfo : list) {
                    if (carInfo.getBid() == bid) oldCnt = carInfo.getCnt();
                }
                int newCnt = obj.getIntValue("cnt");
                int costCnt = newCnt > oldCnt ? oldCnt : oldCnt - newCnt;
                if (costCnt == 0) carMapper.deleteCarInfoWithBIDAndCnt(uid, bid);
                else carMapper.updateCarInfo(uid, bid, costCnt);
                orderMapper.addNewOrderIntoOrderInfo(uid, bid, costCnt, 0);
            }
            return new StateResponse(Response.STATE_SUCCESS);
        } else return new StateResponse(Response.STATE_FAIL);
    }

    private boolean isTokenValid(int uid, String token) {
        return false;
    }
}
