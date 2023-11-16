package com.xiang.controller;

import com.xiang.pojo.JsonResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class HomeController {
    @GetMapping(value = "index", produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResult<String> index() {
        return JsonResult.success("成功", "欢迎来到首页");
    }
}