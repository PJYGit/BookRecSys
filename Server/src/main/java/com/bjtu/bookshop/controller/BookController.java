package com.bjtu.bookshop.controller;

import com.alibaba.fastjson.JSONObject;
import com.bjtu.bookshop.mapper.BookMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookMapper bookMapper;

    @RequestMapping(value = "/", method = {RequestMethod.POST})
    public String test(@RequestBody JSONObject object) {
        
        return object.toString();
    }
}
