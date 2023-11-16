package com.xiang.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiang.exception.MyFrameworkException;
import com.xiang.pojo.JsonResult;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

//@Slf4j
@Component
public final class ResponseUtils {
    private static ObjectMapper objectMapper;
    //通过构造器注入ObjectMapper
    public ResponseUtils(ObjectMapper objectMapper) {
        ResponseUtils.objectMapper = objectMapper;
    }

    public static void responseJson(HttpServletResponse response, JsonResult<?> jsonResult) {
        //设置响应头
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        //设置编码
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        try {
            response.getWriter().write(
                  objectMapper.writeValueAsString(jsonResult)
            );
        } catch (IOException e) {
            //抛出自己的异常
            throw new MyFrameworkException("序列化json出现异常" + e.getMessage(),e);
        }
    }

}
