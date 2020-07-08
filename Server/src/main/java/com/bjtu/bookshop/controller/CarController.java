package com.bjtu.bookshop.controller;

import com.bjtu.bookshop.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void getCarList() {

    }

    @RequestMapping(value = "/addbook", method = {RequestMethod.POST})
    public void addIntoCar() {

    }

    @RequestMapping(value = "/setlist", method = {RequestMethod.POST})
    public void updateCarInfo() {

    }

    @RequestMapping(value = "/submit", method = {RequestMethod.POST})
    public void submitCar() {

    }
}
