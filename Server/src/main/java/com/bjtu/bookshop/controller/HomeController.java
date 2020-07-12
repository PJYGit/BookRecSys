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
    public TopResponse getTop(@Valid TopRequest req, BindingResult br) {
        if (br.hasErrors()) return new TopResponse() {{setState(-1);}};
        return homeService.getTop();
    }

    /**
     * 6.2
     * /home/person 给本用户分析推荐的书
     */
    @RequestMapping(value = "/person", method = {RequestMethod.POST})
    public PersonResponse getPerson(@Valid PersonRequest req, BindingResult br) {
        if (br.hasErrors()) return new PersonResponse() {{setState(-1);}};
        if (!userService.checkUserToken(req)) return new PersonResponse() {{setState(-10);}};
        return homeService.getPerson(req.getUid());
    }

    /**
     * 6.3
     * types 图书类别
     * 已经迁移
     */
    @RequestMapping(value = "/types")
    public String getTypes() {
        return "{ state: 0, msg: \"已迁移，详情请查看文档\" }";
    }

    /**
     * 6.4
     * /home/books 指定类别的图书列表
     */
    @RequestMapping(value = "/books", method = {RequestMethod.POST})
    public TypeBookResponse getBooks(@Valid TypeBookRequest req, BindingResult br) {
        if (br.hasErrors()) return new TypeBookResponse() {{setState(-1);}};
        return homeService.getTypeBooks(req.getTid(),req.getPage());
    }

}
