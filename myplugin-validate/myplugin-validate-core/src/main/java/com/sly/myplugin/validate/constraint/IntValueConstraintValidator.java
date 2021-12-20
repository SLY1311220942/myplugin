package com.sly.myplugin.validate.constraint;

import com.sly.myplugin.validate.annotation.IntValue;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

/**
 * 整数值验证类
 *
 * @author SLY
 * @date 2021/11/25
 */
public class IntValueConstraintValidator implements ConstraintValidator<IntValue, Integer> {

    /**
     * 值set集合
     */
    private final Set<Integer> valueSet = new HashSet<>();

    @Override
    public void initialize(IntValue constraintAnnotation) {
        for (int val : constraintAnnotation.values()) {
            valueSet.add(val);
        }
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return valueSet.contains(value);
    }
}
