package com.github.fanzezhen.template.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CommonController extends BaseController {
    @GetMapping("/")
    public String empty() {
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String index(ModelMap modelMap) {
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
