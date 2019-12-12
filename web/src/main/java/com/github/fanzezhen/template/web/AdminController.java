package com.github.fanzezhen.template.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.fanzezhen.template.common.enums.CommonEnum;
import com.github.fanzezhen.template.common.util.CommonUtil;
import com.github.fanzezhen.template.pojo.entry.SysRole;
import com.github.fanzezhen.template.pojo.entry.SysUser;
import com.github.fanzezhen.template.pojo.model.JsonResult;
import com.github.fanzezhen.template.pojo.model.PageModel;
import com.github.fanzezhen.template.pojo.model.SysUserDetail;
import com.github.fanzezhen.template.service.SysPermissionService;
import com.github.fanzezhen.template.service.SysRoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {
    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private SysPermissionService sysPermissionService;

    @GetMapping("/role")
    public String role() {
        return "admin/admin-role";
    }

    @PostMapping("/role/list/page")
    @ResponseBody
    public Page<SysRole> roleListPage(@RequestBody PageModel<SysRole> page) {
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>(page.getParam());
        if (!StringUtils.isEmpty(page.getStartDate())) queryWrapper.ge("create_time", page.getStartDate());
        if (!StringUtils.isEmpty(page.getEndDate())) queryWrapper.le("create_time", page.getEndDate());
        if (!StringUtils.isEmpty(page.getParam().getRoleName())) {
            queryWrapper.like("role_name", page.getParam().getRoleName());
        }
        page.getParam().setRoleName(null);
        queryWrapper.orderByDesc("update_time");
        sysRoleService.page(page, queryWrapper);
        return page;
    }

    @GetMapping("/role/edit")
    public String roleEdit(@RequestParam(required = false) String roleId, ModelMap modelMap) {
        modelMap.addAttribute("sysRole", roleId == null ? new SysRole() : sysRoleService.getById(roleId));
        modelMap.addAttribute("title", roleId == null ? "添加角色" : "修改角色");
        return "admin/admin-edit";
    }

    @PostMapping("/role/save")
    @ResponseBody
    public JsonResult<Boolean> save(@RequestBody SysRole sysRole) {
        boolean result = sysRoleService.save(sysRole);
        return new JsonResult<>(result ? "保存成功！" : "保存失败， 请刷新后重试！", result);
    }

    @PostMapping("/role/del/batch")
    @ResponseBody
    public JsonResult<HashMap<String, String>> roleDelBatch(@RequestParam(value = "idList") List<String> idList) {
        Collection<SysRole> sysRoleCollection = sysRoleService.listByIds(idList);
        for (SysRole sysRole : sysRoleCollection) {
            sysRole.setDelFlag(CommonEnum.DeleteFlagEnum.YES.getCode());
        }
        sysRoleService.saveBatch(sysRoleCollection);
        return createJsonResult();
    }

}
