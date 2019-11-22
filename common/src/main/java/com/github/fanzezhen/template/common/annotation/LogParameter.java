package com.github.fanzezhen.template.common.annotation;

import java.lang.annotation.*;

/**
 * Created by Andy on 2019/1/14.
 * Desc:
 *      环绕日志注解，类/方法上添加该注解可以日志打印请求过程
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LogParameter {
}
