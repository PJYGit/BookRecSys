package com.bjtu.bookshop.controller;

import com.alibaba.fastjson.JSONObject;
import com.bjtu.bookshop.response.Response;
import com.bjtu.bookshop.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 李高丞
 * @version 1.0
 */

@RestController
@RequestMapping("/car")
public class CarController {

    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @RequestMapping(value = "/getlist", method = {RequestMethod.POST})
    public Response getCarList(@RequestBody JSONObject object) {
        return carService.getCarInfo(object.getIntValue("uid"), object.getString("token"));
    }

    @RequestMapping(value = "/addbook", method = {RequestMethod.POST})
    public Response addIntoCar(@RequestBody JSONObject object) {
        return carService.addBookIntoCar(object.getIntValue("uid"), object.getString("token"), object);
    }

    @RequestMapping(value = "/setlist", method = {RequestMethod.POST})
    public Response updateCarInfo(@RequestBody JSONObject object) {
        return carService.updateCarInfo(object.getIntValue("uid"), object.getString("token"), object);
    }

    @RequestMapping(value = "/submit", method = {RequestMethod.POST})
    public Response submitCar(@RequestBody JSONObject object) {
        return carService.submitCarInfo(object.getIntValue("uid"), object.getString("token"), object);
    }
}
