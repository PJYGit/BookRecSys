package com.bjtu.bookshop.controller;

import com.alibaba.fastjson.JSONObject;
import com.bjtu.bookshop.mapper.BookMapper;
import com.bjtu.bookshop.response.Response;
import com.bjtu.bookshop.response.StateResponse;
import com.bjtu.bookshop.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/home")
public class HomeController {

    private final BookService bookService;

    @Autowired
    public HomeController(BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping(value = "/top", method = { RequestMethod.POST })
    public String test(@RequestBody JSONObject object) {

        return object.toString();
    }

    @RequestMapping(value = "/person", method = { RequestMethod.POST })
    public Response recomandBookForUser() {
        return new StateResponse(Response.STATE_SUCCESS);
    }

    @RequestMapping(value = "/types", method = { RequestMethod.POST })
    public Response getTypes() {
        return new StateResponse(Response.STATE_SUCCESS);
    }

    @RequestMapping(value = "/books", method = { RequestMethod.POST })
    public Response getBookTypes() {
        return new StateResponse(Response.STATE_SUCCESS);
    }

    @RequestMapping(value = "/search", method = { RequestMethod.POST })
    public Response searchBook() {
        return new StateResponse(Response.STATE_SUCCESS);
    }
}
