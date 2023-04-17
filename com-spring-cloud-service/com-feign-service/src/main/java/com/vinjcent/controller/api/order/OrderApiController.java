package com.vinjcent.controller.api.order;

import com.vinjcent.api.status.response.ResponseResult;
import com.vinjcent.endpoint.IOrderEndpoint;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author vinjcent
 * @description 为feign提供的controller接口
 * @since 2023/4/11 16:34
 */
@Api(tags = "API-订单接口")
@RestController
@RequestMapping(OrderApiController.REQUEST_MAPPING)
public class OrderApiController {

    public static final String REQUEST_MAPPING = "/api/order";

    private final IOrderEndpoint orderEndpoint;

    @Autowired
    public OrderApiController(IOrderEndpoint orderEndpoint) {
        this.orderEndpoint = orderEndpoint;
    }

    @GetMapping("/hello")
    public String hello() {
        return orderEndpoint.hello();
    }

    @GetMapping(value = "/get")
    public ResponseResult<String> get(String id) {
        return orderEndpoint.get(id);
    }
}
