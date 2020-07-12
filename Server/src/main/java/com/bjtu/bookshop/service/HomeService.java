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

import java.util.ArrayList;
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
        List<HardBook> topList = homeMapper.getTopList();
        for(HardBook e: topList){
            e.trans();
        }
        return new TopResponse(0, topList);
    }

    /** 6.1.b body **/
    public NewResponse getNew() {
        List<HardBook> newList = homeMapper.getNewList();
        for(HardBook e: newList){
            e.trans();
        }
        return new NewResponse(0, newList);
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

    /** 6.4 body **/
    public TypeBookResponse getTypeBooks(int tid, int page) {
        int typeCnt = homeMapper.getTypeSize();
        int pageCnt = (typeCnt + 9 ) / 10;
        page = Math.min(Math.max(1,page),pageCnt);
        List<HardBook> pList = homeMapper.getTypeBooks(tid, page);
        for(HardBook b:pList) b.trans();
        return new TypeBookResponse(0,pList, pageCnt);
    }

    /** 6.5 body **/
    public SearchResponse getSearchBooks(Integer tid, String word, Double rangemin, Double rangemax, Integer order) {

        String whereAddition = "";
        String OrderByPart = "-sales";
        int lowerP = 0;
        int upperP = 999999999;

        if(tid != null) whereAddition = " and tid = " + tid + " ";
        if(rangemin != null) lowerP = (int) Math.round(rangemin * 100);
        if(rangemax != null) upperP = (int) Math.round(rangemax * 100);
        if(order != null){
            if(order == 1) OrderByPart = "-price";
            if(order == 2) OrderByPart = "price";
        }

        List<HardBook> pList = homeMapper.getSearchBooks(word,whereAddition,OrderByPart,lowerP,upperP);

        return new SearchResponse(0, pList);
    }

    public double getCvt() {
        return homeMapper.getCvt();
    }
}
