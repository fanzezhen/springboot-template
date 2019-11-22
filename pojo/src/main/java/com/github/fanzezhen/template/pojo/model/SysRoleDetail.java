package com.github.fanzezhen.template.pojo.model;

import com.github.fanzezhen.template.pojo.entry.SysRole;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class SysRoleDetail extends SysRole {
    private Long key;
    private String name;

    private List<Route> routes;

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        if (getId() == null){
            setId(key);
        }
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (getRoleName() == null){
            setRoleName(name);
        }
        this.name = name;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }
}
