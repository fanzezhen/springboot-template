package com.github.fanzezhen.template.web;

import com.github.fanzezhen.template.service.SysPermissionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class CommonController extends BaseController {
    @Resource
    private SysPermissionService sysPermissionService;

    @GetMapping("/")
    public String empty() {
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String index(ModelMap modelMap) {
        modelMap.addAttribute("permissionList", sysPermissionService.listByRoleIds(getSysUserDetail().getRoleIds()));
        return "index";
    }

    @GetMapping("/welcome")
    public String welcome(ModelMap modelMap) {
        return "welcome";
    }

    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "hello";
    }

}
