package com.bjtu.bookshop.controller;

import com.alibaba.fastjson.JSONObject;
import com.bjtu.bookshop.bean.request.ShopRequests.*;
import com.bjtu.bookshop.bean.response.ShopResponses.*;
import com.bjtu.bookshop.response.Response;
import com.bjtu.bookshop.service.StoreService;
import com.bjtu.bookshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/shop")
public class StoreController {

    private final StoreService storeService;
    private final UserService userService;

    @Autowired
    public StoreController(StoreService storeService, UserService userService) {
        this.storeService = storeService;
        this.userService = userService;
    }

    /**
     * 2.1
     * /user/manage/list 取用户列表
     */
    @RequestMapping(value = "/getinfo", method = {RequestMethod.POST})
    public GetInfoResponse getShopInfo(@Valid GetInfoRequest req, BindingResult br) {
        if(br.hasErrors()) return new GetInfoResponse(){{setState(-1);}};
        if(! userService.checkUserToken(req)) return new GetInfoResponse(){{setState(-10);}};
        return storeService.getStoreInfo(req.getSid());
    }

    /**
     * 2.2
     * /shop/booklist 取某商店书本列表
     */
    @RequestMapping(value = "/booklist", method = {RequestMethod.POST})
    public BookListResponse getShopBookList(@Valid BookListRequest req, BindingResult br) {
        if(br.hasErrors()) return new BookListResponse(){{setState(-1);}};
        if(! userService.checkUserToken(req)) return new BookListResponse(){{setState(-10);}};
        return storeService.getBookList(req.getSid());
    }

    /**
     * 2.3
     * /shop/getbookinfo 取书信息
     */
    @RequestMapping(value = "/getbookinfo", method = {RequestMethod.POST})
    public BookInfoResponse getShopBookInfo(@Valid BookInfoRequest req, BindingResult br) {
        if(br.hasErrors()) return new BookInfoResponse(){{setState(-1);}};
        if(! userService.checkUserToken(req)) return new BookInfoResponse(){{setState(-10);}};
        return storeService.getBookInfo(req.getBid());
    }

    /**
     * 2.4
     * /shop/search 店铺搜索
     */
    @RequestMapping(value = "/search", method = {RequestMethod.POST})
    public SearchResponse searchShop(@Valid SearchRequest req, BindingResult br) {
        if(br.hasErrors()) return new SearchResponse(){{setState(-1);}};
        if(! userService.checkUserToken(req)) return new SearchResponse(){{setState(-10);}};
        return storeService.searchStore(req.getWord());
    }

    /**
     * 2.s.1
     * /shop/manage/list 取店铺列表
     */
    @RequestMapping(value = "/manage/list", method = {RequestMethod.POST})
    public ManageListResponse getStoreList(@Valid ManageListRequest req, BindingResult br) {
        if(br.hasErrors()) return new ManageListResponse(){{setState(-1);}};
        if(! userService.checkUserToken(req)) return new ManageListResponse(){{setState(-10);}};
        if(! userService.checkUserRole(req.getUid(),1)) return new ManageListResponse(){{setState(-11);}};
        return storeService.getStoreList( req.getPage() == null ? 1 : req.getPage());
    }

    /**
     * 2.s.2
     * /shop/manage/getinfo 取某用户/某店信息
     */
    @RequestMapping(value = "/manage/getinfo", method = {RequestMethod.POST})
    public ManageGetInfoResponse getStoreManagerInfo(@Valid ManageGetInfoRequest req, BindingResult br) {
        if(br.hasErrors()) return new ManageGetInfoResponse(){{setState(-1);}};
        if(! userService.checkUserToken(req)) return new ManageGetInfoResponse(){{setState(-10);}};
        boolean isSuper = userService.checkUserRole(req.getUid(),1);
        return storeService.getStoreManagerInfo(req.getUid(),req.getSid(),isSuper);
    }

    /**
     * 2.s.2
     * /shop/manage/setinfo 设置用户/某店信息
     */
    @RequestMapping(value = "/manage/setinfo", method = {RequestMethod.POST})
    public ManageSetInfoResponse modifyStoreInfo(@Valid ManageSetInfoRequest req, BindingResult br) {
        if(br.hasErrors()) return new ManageSetInfoResponse(){{setState(-1);}};
        if(! userService.checkUserToken(req)) return new ManageSetInfoResponse(){{setState(-10);}};
        boolean isSuper = userService.checkUserRole(req.getUid(),1);
        return storeService.updateStoreInfo(req.getUid(),req.getSid(),isSuper,
                req.getCode(),req.getContent(),req.getHead(),req.getManagers());
    }

    /**
     * 2.s.3
     * /shop/manage/addbook 添加书
     */
    @RequestMapping(value = "/manage/addbook", method = {RequestMethod.POST})
    public ManageAddBookResponse addBookIntoStore(@Valid ManageAddBookRequest req, BindingResult br) {
        if(br.hasErrors()) return new ManageAddBookResponse(){{setState(-1);}};
        if(! userService.checkUserToken(req)) return new ManageAddBookResponse(){{setState(-10);}};
        boolean isSuper = userService.checkUserRole(req.getUid(),1);
        return storeService.addBook(req.getUid(),req.getSid(),isSuper,
                req.getTid(),req.getBname(),req.getAuthor(),req.getContent(),
                req.getPic(),req.getRemain(),req.getPrice());
    }

    @RequestMapping(value = "/manage/delbook", method = {RequestMethod.POST})
    public Response delBookInStore(@RequestBody JSONObject object) {
        return storeService.delBook(object.getIntValue("uid"), object.getString("token"), object.getIntValue("bid"));
    }

    @RequestMapping(value = "/manage/setbookinfo", method = {RequestMethod.POST})
    public Response modifyBookInfoInStore(@RequestBody JSONObject object) {
        return storeService.updateBookInfo(object.getIntValue("uid"), object.getString("token"), object);
    }
}