package com.bjtu.bookshop.service;

import com.bjtu.bookshop.bean.db.UserAddress;
import com.bjtu.bookshop.bean.db.UserInfo;
import com.bjtu.bookshop.bean.db.UserLogin;
import com.bjtu.bookshop.bean.db.UserReg;
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

    public TopResponse getTop() {
        List<TopResponse.elm> topList = homeMapper.getList();
        for(TopResponse.elm e: topList){
            e.trans();
        }
        return new TopResponse(0, topList);
    }
}
