package com.github.fanzezhen.template.pojo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JsonResult<T> {
    private String code = "20000";
    private T data;

    public JsonResult(T data) {
        this.data = data;
    }
}
