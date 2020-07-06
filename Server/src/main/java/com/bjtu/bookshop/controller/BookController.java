package com.bjtu.bookshop.controller;

import com.bjtu.bookshop.mapper.BookMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookMapper mapper;

    @RequestMapping(value = "/", method = {RequestMethod.GET})
    public String test() {
        return "";
    }
}
