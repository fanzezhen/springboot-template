package com.github.fanzezhen.template.web;

import com.github.fanzezhen.template.pojo.util.SysSecurityUtil;
import org.aspectj.lang.Signature;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Created by Andy on 2019/1/14.
 * Desc:
 * 用于统一为所有返回页面的请求填充ModelMap
 */
@Component
@Aspect
public class ControllerAspect extends BaseController {

    private Logger logger = LoggerFactory.getLogger(ControllerAspect.class);
    private final String ExpGetResultDataPoint = "execution(* com.github.fanzezhen.template.web..*(..))";

    @Pointcut(ExpGetResultDataPoint)
    private void controller() {
    }

    /**
     * 定制一个环绕通知
     */
    @Around("controller()")
    public Object advice(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取请求方法
        Signature sig = joinPoint.getSignature();
        String method = joinPoint.getTarget().getClass().getName() + "." + sig.getName();

        logger.info("地址：" + getRequest().getRequestURI());
        //获取请求的参数
        Object[] args = joinPoint.getArgs();
        //打印请求参数
        logger.info("参数:" + method + ":" + Arrays.toString(args));

        //执行方法
        Object result = joinPoint.proceed(); //执行到这里开始走进来的方法体（必须声明）

        //打印返回结果
        logger.info("结果:" + method + ":" + result);
        return result;
    }

    //方法可以带参数，可以同时设置多个方法用&&
//    @Before("controller()")
//    public void record(JoinPoint joinPoint) {
//        logger.info("Before" + Arrays.toString(joinPoint.getArgs()));
//    }

//    @After("controller()")
//    public void after() {
//        System.out.println("After");
//    }

    /**
     * 后置返回通知
     * 这里需要注意的是:
     * 如果参数中的第一个参数为JoinPoint，则第二个参数为返回值的信息
     * 如果参数中的第一个参数不为JoinPoint，则第一个参数为returning中对应的参数
     * returning 限定了只有目标方法返回值与通知方法相应参数类型时才能执行后置返回通知，否则不执行，对于returning对应的通知方法参数为Object类型将匹配任何目标返回值
     */
    @AfterReturning(value = ExpGetResultDataPoint)
    public void doAfterReturningAdvice1(JoinPoint joinPoint) {
        if (!(joinPoint.getTarget() instanceof BaseController)) return;
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof ModelMap) {
                ModelMap modelMap = (ModelMap) arg;
                modelMap.addAttribute("loginUser", SysSecurityUtil.getSysUserDetail());
            }
        }
    }
}
