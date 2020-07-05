package com.bjtu.bookshop.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/book")
public class BookController {

    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public String test() {
        return "Hello, world!";
    }
}
