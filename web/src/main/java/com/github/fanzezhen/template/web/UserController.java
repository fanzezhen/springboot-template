package com.github.fanzezhen.template.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.fanzezhen.template.common.annotation.LogParameter;
import com.github.fanzezhen.template.common.util.CommonUtil;
import com.github.fanzezhen.template.pojo.entry.SysUser;
import com.github.fanzezhen.template.pojo.model.JsonResult;
import com.github.fanzezhen.template.pojo.model.PageModel;
import com.github.fanzezhen.template.service.SysUserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
    @Resource
    private SysUserService sysUserService;

    @LogParameter
    @GetMapping("/log")
    @ResponseBody
    public String getLog(@RequestParam String param) {
        return "Hello Spring Security Log";
    }

    @RequestMapping("/info")
    public JsonResult<HashMap<String, Object>> getLoginUserInfo() {
        JsonResult<HashMap<String, Object>> jsonResult = new JsonResult<>();
        HashMap<String, Object> data = new HashMap<>();
        data.put("name", getSysUserDetail().getNickname());
        data.put("roles", getSysUserDetail().getRoleIds());
        data.put("avatar", getSysUserDetail().getAvatar());
        data.put("introduction", getSysUserDetail().getUsername());
        jsonResult.setData(data);
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
        if (!StringUtils.isEmpty(page.getParam().getUsername()))
            queryWrapper.like("username", page.getParam().getUsername());
        page.getParam().setUsername(null);
        queryWrapper.orderByDesc("update_time");
        sysUserService.page(page, queryWrapper);
        return page;
    }

    @GetMapping("/add")
    public String add() {
        return "user/member-add";
    }

    @PostMapping("/save")
    @ResponseBody
    public JsonResult save(@RequestBody SysUser sysUser) {
        // 密码加密
        sysUser.setPassword(CommonUtil.encrypt(sysUser.getPassword()));
        sysUserService.save(sysUser);
        return createJsonResult();
    }
}
