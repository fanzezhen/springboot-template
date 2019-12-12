package com.github.fanzezhen.template.pojo.model;

import com.github.fanzezhen.template.pojo.entry.SysPermission;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class SysPermissionDetail extends SysPermission {
    private List<String> roleIds;

    public SysPermissionDetail(SysPermission sysPermission) {
        BeanUtils.copyProperties(sysPermission, this);
    }

    public static void main(String[] args) {
        SysPermission sysPermission = new SysPermission();
        sysPermission.setName("test");
        SysPermissionDetail sysPermissionDetail = new SysPermissionDetail();
        BeanUtils.copyProperties(sysPermission, sysPermissionDetail);
        System.out.println(sysPermissionDetail.getName());
    }
}
