package com.bjtu.bookshop.controller;

import com.bjtu.bookshop.bean.request.CarRequests.addBookRequest;
import com.bjtu.bookshop.bean.request.CarRequests.getListRequest;
import com.bjtu.bookshop.bean.request.CarRequests.setListRequest;
import com.bjtu.bookshop.bean.request.CarRequests.submitRequest;
import com.bjtu.bookshop.bean.response.CarResponses.addBookResponse;
import com.bjtu.bookshop.bean.response.CarResponses.getListResponse;
import com.bjtu.bookshop.bean.response.CarResponses.setListResponse;
import com.bjtu.bookshop.bean.response.CarResponses.submitResponse;
import com.bjtu.bookshop.service.CarService;
import com.bjtu.bookshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/car")
public class CarController {

    private final CarService carService;
    private final UserService userService;

    @Autowired
    public CarController(CarService carService, UserService userService) {
        this.carService = carService;
        this.userService = userService;
    }

    /**
     * 3.1
     * /car/getlist 查看我的购物车
     */
    @RequestMapping(value = "/getlist", method = {RequestMethod.POST})
    public getListResponse getCarList(@Valid getListRequest req, BindingResult br) {
        if (br.hasErrors()) return new getListResponse() {{
            setState(-1);
        }};
        if (!userService.checkUserToken(req)) return new getListResponse() {{
            setState(-10);
        }};

        return carService.getCarInfo(req.getUid());
    }

    /**
     * 3.2
     * /car/addbook 添加书
     */
    @RequestMapping(value = "/addbook", method = {RequestMethod.POST})
    public addBookResponse addIntoCar(@Valid addBookRequest req, BindingResult br) {
        if (br.hasErrors()) return new addBookResponse() {{
            setState(-1);
        }};
        if (!userService.checkUserToken(req)) return new addBookResponse() {{
            setState(-10);
        }};

        return carService.addBookIntoCar(req.getUid(), req.getBid(), req.getCnt(), req.getSid());
    }

    /**
     * 3.3
     * /car/setlist 编辑列表
     */
    @RequestMapping(value = "/setlist", method = {RequestMethod.POST})
    public setListResponse setCarInfo(@Valid setListRequest req, BindingResult br) {
        if (br.hasErrors()) return new setListResponse() {{
            setState(-1);
        }};
        if (!userService.checkUserToken(req)) return new setListResponse() {{
            setState(-10);
        }};

        return carService.setCarInfo(req.getUid(), req.getCar());
    }

    /**
     * 3.4
     * /car/submit 结算一部分
     */
    @RequestMapping(value = "/submit", method = {RequestMethod.POST})
    public submitResponse submitCar(@Valid submitRequest req, BindingResult br) {
        if (br.hasErrors()) return new submitResponse() {{
            setState(-1);
        }};
        if (!userService.checkUserToken(req)) return new submitResponse() {{
            setState(-10);
        }};

        return carService.submitCarInfo(req.getUid(), req.getAddress(), req.getBuy());
    }
}
