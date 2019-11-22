package com.github.fanzezhen.template.pojo.model;

import lombok.Data;

import java.util.List;

@Data
public class Meta{
    private String icon;
    private String title;
    private List<Long> roles;
}
