package com.bjtu.bookshop.service;

import java.util.LinkedList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bjtu.bookshop.bean.db.*;
import com.bjtu.bookshop.bean.middle.HardBook;
import com.bjtu.bookshop.bean.middle.SimpleUser;
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

import javax.validation.constraints.NotNull;

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
        SimpleUser storeBoss = new SimpleUser(bossInfo.getUid(),bossInfo.getNickname());
        List<SimpleUser> storeManages = storeMapper.getStoreManagerWithSID(sid);
        return new GetInfoResponse(0,info.getName(),storeBoss,storeManages,info.getContent(),info.getCode(),info.getHead(),info.getMark());
    }

    /** 2.2 body **/
    public BookListResponse getBookList (int sid) {
        List<HardBook> bookInfos = bookMapper.getBookListBySid(sid);
        if(bookInfos == null) return new BookListResponse(){{setState(-11);}};
        for( HardBook elm : bookInfos){
            elm.trans();
        }
        return new BookListResponse(0, bookInfos);
    }

    /** 2.3 body **/
    public BookInfoResponse getBookInfo(int bid) {
        HardBook bookInfo = bookMapper.getBookByBid(bid);
        if(bookInfo == null) return new BookInfoResponse(){{setState(-11);}};
        bookInfo.trans();
        return new BookInfoResponse(0,bookInfo.getBid(),bookInfo.getSid(),bookInfo.getInnerTid(),
                bookInfo.getTid(),bookInfo.getBname(),bookInfo.getSname(),bookInfo.getAuthor(),
                bookInfo.getContent(),bookInfo.getPic(),bookInfo.getMark(),bookInfo.getSales(),
                bookInfo.getRemain(),bookInfo.getPrice());
    }

    /** 2.4 body **/
    public SearchResponse searchStore(String word) {
        List<SearchResponse.elm> list = storeMapper.searchStoreInfo(word);
        return new SearchResponse(0,list);
    }

    /** 2.5 body **/
    public TagListResponse listTag() {
        List<BookTag> list = storeMapper.getTagList();
        return new TagListResponse(0,list);
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

    /** 2.s.2 body **/
    public ManageGetInfoResponse getStoreManagerInfo(int uid, Integer sid, boolean isSuper) {

        StoreInfo info = getShop(uid, sid, isSuper);
        if(info == null) return new ManageGetInfoResponse(){{setState(-12);}};

        UserInfo bossInfo = userMapper.getUserInfoWithUserID(info.getBoss());
        SimpleUser boss = new SimpleUser(bossInfo.getUid(),bossInfo.getNickname());
        List<SimpleUser> manageList = storeMapper.getStoreManagerWithSID(info.getSid());

        return new ManageGetInfoResponse(0,info.getBoss(),info.getSid(),info.getName(),
                boss,manageList,info.getContent(),info.getCode(),
                info.getHead(),info.getMark());
    }

    /** 2.s.3 body **/
    public ManageSetInfoResponse updateStoreInfo(
            int uid, Integer sid, boolean isSuper, Integer code,
            String content, String head, List<Integer> managers) {

        StoreInfo info = getShop(uid, sid, isSuper);
        if(info == null) return new ManageSetInfoResponse(){{setState(-12);}};

        if(code != null && isSuper) info.setCode(code);
        if(content != null) info.setContent(content);
        if(head != null) info.setHead(head);
        if(managers != null){
            storeMapper.cleanManager(info.getSid());
            for(Integer Uid:managers){
                if(Uid == null || Uid <= 0 )continue;
                storeMapper.insertManager(info.getSid(),Uid);
            }
        }
        storeMapper.updateStoreInfo(info);

        return new ManageSetInfoResponse(0);
    }

    /** 2.s.4 body **/
    public ManageAddBookResponse addBook(
            int uid, int sid, boolean isSuper,
            List<Integer> tid, String bname, String author, String content, String pic,
            int remain, double price) {

        StoreInfo info = getShop(uid, sid, isSuper);
        if(info == null) return new ManageAddBookResponse(){{setState(-12);}};

        BookInfo binfo = new BookInfo(-1,bname,author,sid,content,
                pic,0,remain,(int)Math.round(price * 100));
        int bid = storeMapper.InsertBook(binfo);
        for(Integer Tid:tid) {
            if(Tid == null || Tid <= 0 )continue;
            storeMapper.insertTagForBook(bid, Tid);
        }

        return new ManageAddBookResponse(0);
    }

    /** 2.s.5 body **/
    public ManageDelBookResponse delBook(int uid, boolean isSuper, int bid) {

        BookInfo binfo = storeMapper.getBookWithBid(bid);
        if(binfo == null) return new ManageDelBookResponse(){{setState(-12);}};

        StoreInfo info = getShop(uid, binfo.getSid(), isSuper);
        if(info == null) return new ManageDelBookResponse(){{setState(-13);}};

        storeMapper.removeBook(bid);
        storeMapper.cleanBookTag(bid);

        return new ManageDelBookResponse(0);
    }

    /** 2.s.6 body **/
    public ManageSetBookResponse updateBookInfo(int uid, boolean isSuper, int bid,
                                                List<Integer> tid, String bname, String author,
                                                String content, String pic, Integer remain, Double price) {

        BookInfo binfo = storeMapper.getBookWithBid(bid);
        if(binfo == null) return new ManageSetBookResponse(){{setState(-12);}};

        StoreInfo info = getShop(uid, binfo.getSid(), isSuper);
        if(info == null) return new ManageSetBookResponse(){{setState(-13);}};

        if(bname != null) binfo.setName(bname);
        if(author != null) binfo.setAuthor(author);
        if(content != null) binfo.setContent(content);
        if(pic != null) binfo.setPic(pic);
        if(remain != null) binfo.setRemain(remain);
        if(price != null) binfo.setPrice((int)Math.round(100 * price));

        storeMapper.updateBookInfo(binfo);
        if(tid != null){
            storeMapper.cleanBookTag(bid);
            for(Integer Tid:tid) {
                if(Tid == null || Tid <= 0 )continue;
                storeMapper.insertTagForBook(bid, Tid);
            }
        }

        return new ManageSetBookResponse(0);
    }

    /** 2.s.7 body **/
    public ManageGetUserResponse getUserInfo(String phone) {
        UserReg reg = userMapper.getUserRegWithPhone(phone);
        if(reg == null) return new ManageGetUserResponse(){{setState(-12);}};
        UserInfo info = userMapper.getUserInfoWithUserID(reg.getUid());
        return new ManageGetUserResponse(0,reg.getUid(),info.getNickname());
    }

    /** 2.s.8 body **/
    public NewShopResponse newShop(int uid, String name, String content) {
        StoreInfo info = getShop(uid, null, true);
        if(info != null) return new NewShopResponse(){{setState(-11);}};

        StoreInfo newInfo = new StoreInfo(-1,name,uid,content,1,"",5);
        storeMapper.insertShop(newInfo);

        return new NewShopResponse(0);
    }

    public StoreInfo getShop(int uid, Integer sid,boolean isSuper){
        if(sid != null){
            StoreInfo info = storeMapper.getStoreInfoWithSID(sid);
            if(info == null) return null;
            boolean isBoss = info.getBoss() == uid;
            boolean isManager = storeMapper.checkManager(sid, uid) > 0;
            return (isBoss || isManager || isSuper) ? info : null;
        }else{
            return storeMapper.getStoreInfoWithBoss(uid);
        }
    }


}