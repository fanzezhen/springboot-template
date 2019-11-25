package com.github.fanzezhen.template.web;

import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Component
public class ErrorPageResolver implements ErrorViewResolver {
    @Override
    public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model) {
        ModelAndView mv = new ModelAndView();
        /*
        switch (status) {
            case NOT_FOUND:
                mv.setViewName("redirect:/err404.html");
                break;
            case INTERNAL_SERVER_ERROR:
                mv.setViewName("redirect:/oauth/error/" + HttpStatus.INTERNAL_SERVER_ERROR.value());
                break;
            default:
                mv.setViewName("redirect:/err404.html");
        }
         */
        mv.setViewName("redirect:/oauth/error/" + status.value());
        return mv;
    }
}
