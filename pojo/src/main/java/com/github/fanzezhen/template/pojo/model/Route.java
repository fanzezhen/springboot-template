package com.github.fanzezhen.template.pojo.model;

import lombok.Data;

import java.util.List;

@Data
public class Route {
    private String permissionId;
    private String name;
    private String path;
    private String redirect;
    private String component;
    private boolean hidden;
    private boolean alwaysShow;
    private Meta meta;

    private List<Route> children;

    public void setChildren(List<Route> children) {
        this.children = children;
        if (children != null && children.size() > 0)
            this.redirect = children.get(0).getPath();
    }
}
