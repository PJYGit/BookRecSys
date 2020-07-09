package com.bjtu.bookshop.controller;

import com.alibaba.fastjson.JSONObject;
import com.bjtu.bookshop.response.Response;
import com.bjtu.bookshop.service.StoreService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shop")
public class StoreController {
    private final StoreService storeService;

    @Autowired
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @RequestMapping(value = "/getinfo", method = {RequestMethod.POST})
    public Response getShopInfo(@RequestBody JSONObject object) {
        return storeService.getStoreInfo(object.getIntValue("uid"), object.getString("token"), object.getIntValue("sid"));
    }

    @RequestMapping(value = "/booklist", method = {RequestMethod.POST})
    public Response getShopBookList(@RequestBody JSONObject object) {
        return storeService.getBookList(object.getIntValue("uid"), object.getString("token"), object.getIntValue("sid"));
    }

    @RequestMapping(value = "/getbookinfo", method = {RequestMethod.POST})
    public Response getShopBookInfo(@RequestBody JSONObject object) {
        return storeService.getBookInfo(object.getIntValue("uid"), object.getString("token"), object.getIntValue("bid"));
    }

    @RequestMapping(value = "/search", method = {RequestMethod.POST})
    public Response searchShop(@RequestBody JSONObject object) {
        return storeService.searchStore(object.getIntValue("uid"), object.getString("token"), object.getString("word"));
    }

    @RequestMapping(value = "/manage/list", method = {RequestMethod.POST})
    public Response getStoreList(@RequestBody JSONObject object) {
        return storeService.getStoreList(object.getIntValue("uid"), object.getString("token"), object.getIntValue("page"));
    }

    @RequestMapping(value = "/manage/getinfo", method = {RequestMethod.POST})
    public Response getStoreManagerInfo(@RequestBody JSONObject object) {
        return storeService.getStoreManagerInfo(object.getIntValue("uid"), object.getString("token"), object.getInteger("sid"));
    }

    @RequestMapping(value = "/manage/setinfo", method = {RequestMethod.POST})
    public Response modifyStoreInfo(@RequestBody JSONObject object) {
        // TODO 未完成，给董老师吧
        return storeService.updateStoreInfo(object.getIntValue("uid"), object.getString("token"));
    }

    @RequestMapping(value = "/manage/addbook", method = {RequestMethod.POST})
    public Response addBookIntoStore(@RequestBody JSONObject object) {
        return storeService.addBook(object.getIntValue("uid"), object.getString("token"), object);
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