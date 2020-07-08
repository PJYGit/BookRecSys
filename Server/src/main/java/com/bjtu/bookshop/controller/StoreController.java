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
    private StoreService storeService;

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
        return storeService.getBookInfo(object.getIntValue("uid"), object.getString("token"), object.getIntValue("sid"), object.getIntValue("bid"));
    }

    @RequestMapping(value = "/manage/list", method = {RequestMethod.POST})
    public void getStoreList(@RequestBody JSONObject object) {

    }

    @RequestMapping(value = "/manage/getinfo", method = {RequestMethod.POST})
    public void getStoreManagerInfo(@RequestBody JSONObject object) {

    }

    @RequestMapping(value = "/manage/setinfo", method = {RequestMethod.POST})
    public void modifyStoreInfo(@RequestBody JSONObject object) {

    }

    @RequestMapping(value = "/manage/addbook", method = {RequestMethod.POST})
    public void addBookIntoStore(@RequestBody JSONObject object) {

    }

    @RequestMapping(value = "/manage/delbook", method = {RequestMethod.POST})
    public Response delBookInStore(@RequestBody JSONObject object) {
        return storeService.delBook(object.getIntValue("uid"), object.getString("token"), object.getIntValue("bid"));
    }

    @RequestMapping(value = "/manage/setbookinfo", method = {RequestMethod.POST})
    public void modifyBookInfoInStore(@RequestBody JSONObject object) {

    }
}