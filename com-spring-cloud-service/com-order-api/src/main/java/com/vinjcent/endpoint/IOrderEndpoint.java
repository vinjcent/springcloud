package com.vinjcent.endpoint;

import com.vinjcent.api.status.response.ResponseResult;
import com.vinjcent.constants.OrderApiConfig;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author vinjcent
 * @description Feign服务接口
 * <br>@FeignClient参数说明</br>
 * <br>name：指定调用rest接口所对应的服务名</br>
 * <br>path：指定调用rest接口所在的Controller指定的@RequestMapping</br>
 * <br>value/name：指定提供者的微服务名称</br>
 * <br>url：直接指定请求的路径地址</br>
 * <br>decode404：是否应该编码或者抛出FeignException异常</br>
 * <br>configuration：配置feign.codec.Decoder、feign.codec.Encoder、feign.Contract</br>
 * <br>fallback:指定发送异常调用或者超时时应该调用那个类来执行备用方法</br>
 * <br>fallbackFactory：提供统一的异常熔断处理，避免重复代码的编写</br>
 * <br>path：当服务提供者使用了server.context.path时</br>
 * <br>contextId：用来唯一标识当一个微服务中存在多个FeignClient接口调用同一个服务提供者时的场景(若是不提供该属性值，则在程序启动时会启动失败，并提示如下信息</br>
 * @since 2023/4/11 15:51
 */
@FeignClient(name = OrderApiConfig.PLACE_HOLD_SERVICE_NAME, contextId = "IOrderEndpoint", path = IOrderEndpoint.REQUEST_MAPPING)
public interface IOrderEndpoint {

    String REQUEST_MAPPING = "/order/portal";

    /**
     * 返回输入的id
     *
     * @param id 输入id
     * @return 输入id
     */
    @ApiImplicitParam(name = "id", value = "id")
    @ApiOperation(value = "返回id", notes = "返回id", httpMethod = "GET")
    @GetMapping(value = "/get")
    ResponseResult<String> get(@RequestParam("id") String id);

    /**
     * 测试接口
     *
     * @return 返回"hello"字符串
     */
    @GetMapping(value = "/hello")
    String hello();
}
