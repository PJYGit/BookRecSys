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
import com.bjtu.bookshop.bean.response.ShopResponses.*;
import com.bjtu.bookshop.bean.response.UserResponses;
import com.bjtu.bookshop.mapper.BookMapper;
import com.bjtu.bookshop.mapper.StoreMapper;
import com.bjtu.bookshop.mapper.UserMapper;
import com.bjtu.bookshop.response.ItemResponse;
import com.bjtu.bookshop.response.Response;
import com.bjtu.bookshop.response.StateResponse;

import com.bjtu.bookshop.util.CacheUtil;
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

    /** 2.1 body **/
    public GetInfoResponse getStoreInfo(int sid) {
        StoreInfo info = storeMapper.getStoreInfoWithSID(sid);
        if(info == null) return new GetInfoResponse(){{setState(-11);}};
        UserInfo bossInfo = userMapper.getUserInfoWithUserID(info.getBoss());
        GetInfoResponse.uel storeBoss = new GetInfoResponse.uel(bossInfo.getUid(),bossInfo.getNickname());
        List<GetInfoResponse.uel> storeManages = storeMapper.getStoreManagerWithSID(sid);
        return new GetInfoResponse(0,info.getName(),storeBoss,storeManages,info.getContent(),info.getCode(),info.getHead(),info.getMark());
    }

    /** 2.2 body **/
    public BookListResponse getBookList (int sid) {
        List<BookListResponse.book> bookInfos = bookMapper.getBookListBySid(sid);
        if(bookInfos == null) return new BookListResponse(){{setState(-11);}};
        for( BookListResponse.book elm : bookInfos){
            elm.trans();
        }
        return new BookListResponse(0, bookInfos);
    }

    /** 2.3 body **/
    public BookInfoResponse getBookInfo(int bid) {
        BookListResponse.book bookInfo = bookMapper.getBookByBid(bid);
        if(bookInfo == null) return new BookInfoResponse(){{setState(-11);}};
        bookInfo.trans();
        return new BookInfoResponse(0,bookInfo.getBid(),bookInfo.getSid(),bookInfo.getInnerTid(),
                bookInfo.getTid(),bookInfo.getBname(),bookInfo.getSname(),bookInfo.getAuther(),
                bookInfo.getContent(),bookInfo.getPic(),bookInfo.getMark(),bookInfo.getSales(),
                bookInfo.getRemain(),bookInfo.getPrice());
    }

    /** 2.4 body **/
    public SearchResponse searchStore(String word) {
        List<SearchResponse.elm> list = storeMapper.searchStoreInfo(word);
        return new SearchResponse(0,list);
    }


    static CacheUtil.TimeCache<Integer> ManageListResponseListCache = new CacheUtil.TimeCache<Integer>();
    /** 2.s.1 body **/
    public ManageListResponse getStoreList(int page) {
        if(!ManageListResponseListCache.available()){
            ManageListResponseListCache.update((storeMapper.getShopCnt() + 19) / 20);
        }
        int pageCnt = ManageListResponseListCache.get();
        page = Math.max(1, page);
        List<ManageListResponse.elm> list = storeMapper.getShopList(page);
        return new ManageListResponse(0,pageCnt,list);
    }

    public Response getStoreManagerInfo(int uid, String token, Integer sid) {
        /*
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

         */
        return null;
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
        return false;
    }
}