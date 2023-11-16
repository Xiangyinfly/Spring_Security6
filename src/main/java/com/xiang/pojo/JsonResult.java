package com.xiang.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "create")
public class JsonResult<T> {
    /*
    0:success
    others:failure
     */
    private Integer code;
    private String message;
    private T data;

    public static <T> JsonResult<T> success(String message,T data) {
        return create(0,message,data);
    }

    public static <T> JsonResult<T> success(String message) {
        return create(0,message,null);
    }

    public static <T> JsonResult<T> failure(String message) {
        return create(1,message,null);
    }
}
