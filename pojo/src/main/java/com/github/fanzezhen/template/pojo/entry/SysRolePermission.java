package com.github.fanzezhen.template.pojo.entry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class SysRolePermission extends BaseEntry {
    private String roleId;

    private String permissionId;
}
