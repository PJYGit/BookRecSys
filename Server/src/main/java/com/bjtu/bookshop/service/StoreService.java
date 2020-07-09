package com.bjtu.bookshop.service;

import java.util.LinkedList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bjtu.bookshop.bean.db.BookInfo;
import com.bjtu.bookshop.bean.db.BookTag;
import com.bjtu.bookshop.bean.db.StoreInfo;
import com.bjtu.bookshop.bean.db.StoreManage;
import com.bjtu.bookshop.bean.db.UserInfo;
import com.bjtu.bookshop.bean.db.UserLogin;
import com.bjtu.bookshop.mapper.BookMapper;
import com.bjtu.bookshop.mapper.StoreMapper;
import com.bjtu.bookshop.mapper.UserMapper;
import com.bjtu.bookshop.response.ItemResponse;
import com.bjtu.bookshop.response.Response;
import com.bjtu.bookshop.response.StateResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Data;

@Service
public class StoreService {
    private final StoreMapper storeMapper;
    private final UserMapper userMapper;
    private final BookMapper bookMapper;

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
            List<StoreManage> storeManages = storeMapper.getStoreManagerWithSID(sid);
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

    public Response getBookInfo(int uid, String token, int bid) {
        if (isTokenValid(uid, token)) {
            BookInfo info = bookMapper.getBookInfoWithBID(bid);
            List<BookTag> tags = bookMapper.getBookTagWithBID(bid);

            JSONObject data = new JSONObject();
            data.put("bookInfo", info);
            data.put("tags", tags);
            return new ItemResponse<>(data, Response.STATE_SUCCESS);
        } else return new StateResponse(Response.STATE_FAIL);
    }

    public Response searchStore(int uid, String token, String word) {
        if (isTokenValid(uid, token)) {
            List<StoreInfo> list = storeMapper.searchStoreInfo(word);
            return new ItemResponse<>(list, Response.STATE_SUCCESS);
        } else return new StateResponse(Response.STATE_FAIL);
    }

    public Response getStoreList(int uid, String token, int page) {
        if (isTokenValid(uid, token)) {
            if (page <= 0) page = 1;
            List<StoreInfo> storeInfos = storeMapper.getStoreListWithPage((page - 1) * 20, page * 20);

            @Data
            class ReturnClass {
                StoreInfo storeInfo;
                UserInfo bossInfo;
                List<UserInfo> managerInfos;
            }
            List<ReturnClass> list = new LinkedList<>();
            for (StoreInfo info : storeInfos) {
                ReturnClass class1 = new ReturnClass();
                class1.storeInfo = info;
                class1.bossInfo = userMapper.getUserInfoWithUserID(info.getBoss());

                List<StoreManage> manages = storeMapper.getStoreManagerWithSID(info.getSid());
                List<UserInfo> managerInfos = new LinkedList<>();
                for (StoreManage manage : manages) {
                    UserInfo managerInfo = userMapper.getUserInfoWithUserID(manage.getUid());
                    managerInfos.add(managerInfo);
                }
                class1.managerInfos = managerInfos;
            }

            return new ItemResponse<>(list, Response.STATE_SUCCESS);
        } else return new StateResponse(Response.STATE_FAIL);
    }

    public Response getStoreManagerInfo(int uid, String token, Integer sid) {
        if (isTokenValid(uid, token)) {
            JSONObject data = new JSONObject();
            StoreInfo storeInfo = storeMapper.getStoreInfoWithSID(sid);
            UserInfo bossInfo = userMapper.getUserInfoWithUserID(storeInfo.getBoss());
            data.put("boss", bossInfo);

            List<StoreManage> list = storeMapper.getStoreManagerWithSID(sid);
            List<UserInfo> managerInfo = new LinkedList<>();
            for (StoreManage manage : list) {
                UserInfo manager = userMapper.getUserInfoWithUserID(manage.getUid());
                managerInfo.add(manager);
            }
            data.put("manager", managerInfo);
            return new ItemResponse<>(data, Response.STATE_FAIL);
        } else return new StateResponse(Response.STATE_FAIL);
    }

    public Response updateStoreInfo(int uid, String token) {
        if (isTokenValid(uid, token)) {

            return new ItemResponse<>(uid, Response.STATE_FAIL);
        } else return new StateResponse(Response.STATE_FAIL);
    }

    public Response addBook(int uid, String token, JSONObject object) {
        if (isTokenValid(uid, token)) {
            BookInfo info = new BookInfo();
            info.setName(object.getString("bname"));
            info.setAuthor(object.getString("author"));
            info.setContent(object.getString("content"));
            info.setPic(object.getString("pic"));
            info.setRemain(object.getString("remain"));
            info.setPrice(object.getIntValue("price"));
            info.setSid(object.getIntValue("sid"));
            bookMapper.insertNewBookIntoBookInfo(info);

            JSONArray tids = object.getJSONArray("tid");
            BookInfo bookInfo = bookMapper.getBookInfoWithNameAndSID(object.getIntValue("sid"), object.getString("bname"));

            for (int i = 0; i < tids.size(); i++) {
                bookMapper.insertBookTag(bookInfo.getBid(), tids.getIntValue(i));
            }

            return new StateResponse(Response.STATE_SUCCESS);
        } else return new StateResponse(Response.STATE_FAIL);
    }

    public Response delBook(int uid, String token, int bid) {
        if (isTokenValid(uid, token)) {
            bookMapper.deleteBookWithBID(bid);
            return new StateResponse(Response.STATE_SUCCESS);
        } else return new StateResponse(Response.STATE_FAIL);
    }

    public Response updateBookInfo(int uid, String token, JSONObject object) {
        if (isTokenValid(uid, token)) {

            BookInfo info = bookMapper.getBookInfoWithBID(object.getIntValue("bid"));
            if (object.getString("bname") != null) info.setName(object.getString("bname"));
            if (object.getString("author") != null) info.setAuthor(object.getString("author"));
            if (object.getString("content") != null) info.setContent(object.getString("content"));
            if (object.getString("pic") != null) info.setPic(object.getString("pic"));
            if (object.getString("remain") != null) info.setRemain(object.getString("remain"));
            //if (object.getDoubleValue("price") != 0) info.setPrice(object.getDoubleValue("price"));
            bookMapper.updateBookInfo(info);

            JSONArray array = object.getJSONArray("tid");
            bookMapper.deleteBookAllTagWithBID(info.getBid());
            for (int i = 0; i < array.size(); i++)
                bookMapper.insertBookTag(info.getBid(), array.getIntValue(i));

            return new StateResponse(Response.STATE_SUCCESS);
        } else return new StateResponse(Response.STATE_FAIL);
    }

    private boolean isTokenValid(int uid, String token) {
        UserLogin login = userMapper.getUserLoginInfoWithUID(uid);
        return token.equals(login.getToken());
    }
}