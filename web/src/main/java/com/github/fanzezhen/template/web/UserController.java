package com.github.fanzezhen.template.web;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.fanzezhen.template.common.annotation.LogParameter;
import com.github.fanzezhen.template.pojo.entry.SysUser;
import com.github.fanzezhen.template.pojo.model.JsonResult;
import com.github.fanzezhen.template.service.SysUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

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
    public String list(ModelMap modelMap){
        Page<SysUser> page = new Page<>(1,5);  // 查询第1页，每页返回5条
        modelMap.addAttribute("page", sysUserService.page(page));
        return "user/member-list";
    }

    @PostMapping("/list/page")
    @ResponseBody
    public Page<SysUser> listPage(){
        Page<SysUser> page = new Page<>(1,5);  // 查询第1页，每页返回5条
        sysUserService.page(page);
        return page;
    }
}
