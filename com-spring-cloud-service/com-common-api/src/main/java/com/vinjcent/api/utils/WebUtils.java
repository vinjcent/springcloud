package com.vinjcent.api.utils;

import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


/**
 * @author vinjcent
 * web 相应工具类
 */
public class WebUtils {


    /**
     * 将字符渲染到客户端
     *
     * @param response 渲染对象
     * @param message  待渲染的字符
     */
    public static void renderString(HttpServletResponse response, String message) {

        try {

            response.setStatus(HttpStatus.OK.value());
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(message);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置下载响应头
     *
     * @param fileName 文件名称
     * @param response 响应
     */
    public static void setDownLoadHeader(String fileName, HttpServletResponse response) throws UnsupportedEncodingException {
        // 这里注意--->有同学反应使用 swagger 会导致各种问题,请直接用浏览器或者用postman
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyExcel没有关系
        String fName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        // 必须暴露,响应首部 Access-Control-Expose-Headers 就是控制“暴露”的开关
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setHeader("Content-Disposition", "attachment;filename=" + fName + ".xlsx");
    }
}
