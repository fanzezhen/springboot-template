package com.github.fanzezhen.template.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.fanzezhen.template.common.annotation.LogParameter;
import com.github.fanzezhen.template.common.enums.CommonEnum;
import com.github.fanzezhen.template.common.util.CommonUtil;
import com.github.fanzezhen.template.pojo.entry.SysUser;
import com.github.fanzezhen.template.pojo.model.JsonResult;
import com.github.fanzezhen.template.pojo.model.PageModel;
import com.github.fanzezhen.template.pojo.model.SysUserDetail;
import com.github.fanzezhen.template.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
    @Resource
    private SysUserService sysUserService;

    @RequestMapping("/info")
    public JsonResult<Object> getLoginUserInfo(@RequestParam(required = false) Long userId) {
        JsonResult<Object> jsonResult = new JsonResult<>();
        if (userId == null) {
            HashMap<String, Object> data = new HashMap<>();
            data.put("name", getSysUserDetail().getNickname());
            data.put("roles", getSysUserDetail().getRoleIds());
            data.put("avatar", getSysUserDetail().getAvatar());
            data.put("introduction", getSysUserDetail().getUsername());
            jsonResult.setData(data);
        } else {
            jsonResult.setData(sysUserService.getById(userId));
        }
        return jsonResult;
    }

    @GetMapping("/list")
    public String list(@RequestParam(required = false, defaultValue = "") String start,
                       @RequestParam(required = false, defaultValue = "") String end,
                       @RequestParam(required = false, defaultValue = "") String username,
                       ModelMap modelMap) {
        modelMap.addAttribute("start", start);
        modelMap.addAttribute("end", end);
        modelMap.addAttribute("username", username);
        return "user/member-list";
    }

    @PostMapping("/list/page")
    @ResponseBody
    public Page<SysUser> listPage(@RequestBody PageModel<SysUser> page) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>(page.getParam());
        if (!StringUtils.isEmpty(page.getStartDate())) queryWrapper.ge("create_time", page.getStartDate());
        if (!StringUtils.isEmpty(page.getEndDate())) queryWrapper.le("create_time", page.getEndDate());
        if (!StringUtils.isEmpty(page.getParam().getUsername())) {
            queryWrapper.like("username", page.getParam().getUsername());
        }
        page.getParam().setUsername(null);
        queryWrapper.orderByDesc("update_time");
        sysUserService.page(page, queryWrapper);
        return page;
    }

    @GetMapping("/edit")
    public String edit(@RequestParam(required = false) Long userId, ModelMap modelMap) {
        modelMap.addAttribute("sysUser", userId == null ? new SysUser() : sysUserService.getById(userId));
        modelMap.addAttribute("title", userId == null ? "添加用户" : "修改用户");
        return "user/member-edit";
    }

    @PostMapping("/save")
    @ResponseBody
    public JsonResult<Boolean> save(@RequestBody SysUserDetail sysUserDetail) {
        if (!StringUtils.isEmpty(sysUserDetail.getPassword())) {
            if (StringUtils.isEmpty(sysUserDetail.getOldPassword()))
                return new JsonResult<>("保存失败, 原密码输入有误！", false);
            if (StringUtils.isEmpty(sysUserDetail.getId()))
                return new JsonResult<>("保存失败！", false);
            if (!CommonUtil.verifyPassword(sysUserDetail.getOldPassword(), sysUserService.getById(sysUserDetail.getId()).getPassword()))
                return new JsonResult<>("保存失败, 原密码输入有误！", false);
            sysUserDetail.setPassword(CommonUtil.encrypt(sysUserDetail.getPassword())); // 密码加密
        }
        boolean result = sysUserService.save(sysUserDetail);
        return new JsonResult<>(result ? "保存成功！" : "保存失败， 请刷新后重试！", result);
    }

    @PostMapping("/del/batch")
    @ResponseBody
    public JsonResult<HashMap<String, String>> delBatch(@RequestParam(value = "idList") List<Long> idList) {
        Collection<SysUser> sysUserList = sysUserService.listByIds(idList);
        for (SysUser sysUser : sysUserList) {
            sysUser.setDelFlag(CommonEnum.DeleteFlagEnum.YES.getCode());
        }
        sysUserService.saveBatch(sysUserList);
        return createJsonResult();
    }

    @GetMapping("/change-password")
    public String changePassword(@RequestParam Long userId, ModelMap modelMap) {
        modelMap.addAttribute("sysUser", sysUserService.getById(userId));
        return "user/member-password";
    }

}
