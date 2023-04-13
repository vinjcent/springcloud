package com.vinjcent.controller.portal;

import com.vinjcent.api.status.response.ResponseResult;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author vinjcent
 * @description 订单控制层
 * @since 2023/4/10 13:59
 */
@Api(tags = "portal-订单接口")
@RestController
@RequestMapping(OrderController.REQUEST_MAPPING)
public class OrderController {

    public static final String REQUEST_MAPPING = "/portal";

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping(value = "/get")
    public ResponseResult<String> get(String id) {
        return ResponseResult.success(id);
    }
}
