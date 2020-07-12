package com.bjtu.bookshop.service;

import com.bjtu.bookshop.bean.db.UserAddress;
import com.bjtu.bookshop.bean.db.UserInfo;
import com.bjtu.bookshop.bean.db.UserLogin;
import com.bjtu.bookshop.bean.db.UserReg;
import com.bjtu.bookshop.bean.middle.HardBook;
import com.bjtu.bookshop.bean.request.CommonRequests.UserAuthorization;
import com.bjtu.bookshop.bean.request.UserRequests.SetInfoRequest;
import com.bjtu.bookshop.bean.response.HomeResponses.*;
import com.bjtu.bookshop.bean.response.UserResponses.*;
import com.bjtu.bookshop.mapper.HomeMapper;
import com.bjtu.bookshop.mapper.UserMapper;
import com.bjtu.bookshop.util.CacheUtil.TimeCache;
import com.bjtu.bookshop.util.NumberUtil;
import com.bjtu.bookshop.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeService {

    private final HomeMapper homeMapper;

    @Autowired
    public HomeService(HomeMapper homeMapper) {
        this.homeMapper = homeMapper;
    }

    /** 6.1 body **/
    public TopResponse getTop() {
        List<HardBook> topList = homeMapper.getList();
        for(HardBook e: topList){
            e.trans();
        }
        return new TopResponse(0, topList);
    }

    /** 6.2 body **/
    public PersonResponse getPerson(int uid) {
        List<HardBook> pList = homeMapper.getPerson(uid);
        for(HardBook e: pList){
            e.trans();
            if(e.getBname() == null){
                e.setBname("查无此书");
            }
        }
        return new PersonResponse(0, pList);
    }
}
