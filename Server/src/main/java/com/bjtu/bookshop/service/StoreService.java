package com.bjtu.bookshop.service;

import java.util.LinkedList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.bjtu.bookshop.entity.BookInfo;
import com.bjtu.bookshop.entity.BookTag;
import com.bjtu.bookshop.entity.StoreInfo;
import com.bjtu.bookshop.entity.StoreManage;
import com.bjtu.bookshop.entity.UserInfo;
import com.bjtu.bookshop.entity.UserLogin;
import com.bjtu.bookshop.mapper.BookMapper;
import com.bjtu.bookshop.mapper.StoreMapper;
import com.bjtu.bookshop.mapper.UserMapper;
import com.bjtu.bookshop.response.ItemResponse;
import com.bjtu.bookshop.response.Response;
import com.bjtu.bookshop.response.StateResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreService {
    private StoreMapper storeMapper;
    private UserMapper userMapper;
    private BookMapper bookMapper;

    @Autowired
    public StoreService(StoreMapper storeMapper, UserMapper userMapper, BookMapper bookMapper) {
        this.storeMapper = storeMapper;
        this.userMapper = userMapper;
        this.bookMapper = bookMapper;
    }

    public Response getStoreInfo(int uid, String token, int sid) {
        if (isTokenValid(uid, token)) {
            StoreInfo info = storeMapper.getStoreInfoWithSID(sid);
            UserInfo bossInfo = userMapper.getUserInfoWithUserID(info.getBoss());
            List<StoreManage> storeManages = storeMapper.getStoreTypeWithSID(sid);
            List<UserInfo> managers = new LinkedList<>();

            for (StoreManage manager : storeManages) {
                UserInfo item = userMapper.getUserInfoWithUserID(manager.getUid());
                managers.add(item);
            }

            JSONObject data = new JSONObject();
            data.put("storeInfo", info);
            data.put("bossInfo", bossInfo);
            data.put("managers", managers);
            return new ItemResponse<>(data, Response.STATE_SUCCESS);
        } else return new StateResponse(Response.STATE_FAIL);
    }

    public Response getBookList(int uid, String token, int sid) {
        if (isTokenValid(uid, token)) {
            StoreInfo storeInfo = storeMapper.getStoreInfoWithSID(sid);
            if (storeInfo == null) return new StateResponse(Response.STATE_FAIL);

            List<BookInfo> bookInfos = bookMapper.getBookInfoWithSID(sid);
            JSONObject data = new JSONObject();
            data.put("storeInfo", storeInfo);
            data.put("bookInfos", bookInfos);
            return new ItemResponse<>(data, Response.STATE_SUCCESS);

        } else return new StateResponse(Response.STATE_FAIL);
    }

    public Response getBookInfo(int uid, String token, int sid, int bid) {
        if (isTokenValid(uid, token)) {
            BookInfo info = bookMapper.getBookInfoWithBID(bid);
            List<BookTag> tags = bookMapper.getBookTagWithBID(bid);

            JSONObject data = new JSONObject();
            data.put("bookInfo", info);
            data.put("tags", tags);
            return new ItemResponse<>(data, Response.STATE_SUCCESS);
        } else return new StateResponse(Response.STATE_FAIL);
    }

    private boolean isTokenValid(int uid, String token) {
        UserLogin login = userMapper.getUserLoginInfoWithUID(uid);
        return token.equals(login.getToken());
    }
}