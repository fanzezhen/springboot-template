package com.github.fanzezhen.template.web;

import com.github.fanzezhen.template.common.annotation.LogParameter;
import com.github.fanzezhen.template.pojo.model.JsonResult;
import com.github.fanzezhen.template.service.SysUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    @Resource
    private SysUserService sysUserService;
    @LogParameter
    @GetMapping("/log")
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
}
