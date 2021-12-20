package com.sly.myplugin.validate.annotation;

import com.sly.myplugin.validate.constraint.IntValueConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 数字值
 *
 * @author SLY
 * @date 2021/11/25
 */
@Constraint(validatedBy = {IntValueConstraintValidator.class})
@Documented
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface IntValue {

    /**
     * 默认提示信息
     */
    String message() default "{com.cbyzs.eccloud.serverapi.common.valid.IntValue.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 允许值
     */
    int[] values() default {};
}
