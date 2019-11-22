package com.github.fanzezhen.template.pojo.entry;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class SysUserRole extends BaseEntry {
    private Long userId;
    private Long roleId;
}
