package com.github.fanzezhen.template.pojo.entry;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@TableName("sys_permission")
public class SysPermission extends BaseAloneEntry {
    private String name;

    private String icon;

    private String pid;

    private Integer status;

    @Column(name = "operation_url")
    private String operationUrl;

    private Integer type;

    @Column(name = "order_num")
    private Integer orderNum;
}
