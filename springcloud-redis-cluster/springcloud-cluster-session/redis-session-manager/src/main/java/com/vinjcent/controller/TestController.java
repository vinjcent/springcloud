package com.vinjcent.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/test")
    public void test(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 从redis中获取
        List<String> list = (List<String>) request.getSession().getAttribute("list");

        if (list == null) {
            list = new ArrayList<>();
            // 这里将list存入redis,是该方法结束之后才存入redis,所以由下面的操作jvm将size赋值为了1之后才存入(地址的概念)
            request.getSession().setAttribute("list",list);
        }

        // 这里的添加操作一直是在jvm中进行,但一直都是从redis中获取的"list",并没有及时更新redis中的缓存信息
        list.add("xxxx");
        request.getSession().setAttribute("list",list);     // 每次session变化都要同步session
        // 所以每次获取"list"是从redis中获取,而add操作则是jvm中,add操作之后没有及时更新redis缓存中的信息!
        response.getWriter().println("size: " + list.size());
        // 因为sessionId是从redis中获取的,所以值一直不变
        response.getWriter().println("sessionId: " + request.getSession().getId());

    }

}
