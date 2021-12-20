package com.sly.myplugin.preventrepeat.annotation;

import java.lang.annotation.*;

/**
 * 防重复提交注解
 * @author SLY
 * @date 2021/11/25
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PreventRepeat {
    /** 延时时间 单位ms（默认1000ms） */
    int value() default 1000;
}
