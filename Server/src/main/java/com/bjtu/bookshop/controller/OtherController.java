package com.bjtu.bookshop.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 李高丞
 * @version 1.0
 */

@RestController
public class OtherController {
    @RequestMapping(value = "/book/info", method = {RequestMethod.POST})
    public void getBookInfo() {

    }
}
