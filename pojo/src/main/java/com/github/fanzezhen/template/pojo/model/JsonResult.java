package com.github.fanzezhen.template.pojo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JsonResult<T> {
    private String code = "20000";
    private String msg = "成功";
    private T data;

    public JsonResult(T data) {
        this.data = data;
    }

    public JsonResult(String msg, T data) {
        this.msg = msg;
        this.data = data;
    }
}
