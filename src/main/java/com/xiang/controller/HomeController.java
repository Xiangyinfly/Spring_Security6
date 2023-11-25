package com.xiang.controller;

import com.xiang.pojo.JsonResult;
import com.xiang.service.HomeService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
@AllArgsConstructor
public class HomeController {
    private final HomeService homeService;
    @GetMapping(value = "index", produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResult<String> index() {
        return JsonResult.success("成功", homeService.sayHello());
    }
}