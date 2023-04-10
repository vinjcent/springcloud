package com.vinjcent.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author vinjcent
 * @description 订单控制层
 * @since 2023/4/10 13:59
 */
@RestController
@RequestMapping("/api")
public class OrderController {

    @GetMapping("hello")
    public String hello() {
        return "hello";
    }

}
