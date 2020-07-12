package com.bjtu.bookshop.controller;

import com.bjtu.bookshop.bean.request.HomeRequests.*;
import com.bjtu.bookshop.bean.response.HomeResponses.*;
import com.bjtu.bookshop.service.HomeService;
import com.bjtu.bookshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("/home")
public class HomeController {

    private final HomeService homeService;
    private final UserService userService;

    @Autowired
    public HomeController(HomeService homeService, UserService userService) {
        this.homeService = homeService;
        this.userService = userService;
    }

    /**
     * 6.1
     * home/top 销量前n书
     */
    @RequestMapping(value = "/top", method = {RequestMethod.POST})
    public TopResponse userLogin(@Valid TopRequest req, BindingResult br) {
        if (br.hasErrors()) return new TopResponse() {{setState(-1);}};
        return homeService.getTop();
    }

}
