package com.chatx.commons.constrait;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 用于数值校验
 *
 * @author Jun
 * @since 1.0.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = Digital.DigitalValidator.class)
public @interface Digital {

    String message() default "数值校验不合法";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int[] include() default {};

    int[] exclude() default {};

    int max() default 0;

    int min() default 0;

    /**
     * 校验对象为 null 时，是否需要检查.
     * <ul>
     *    <li>需要检查：当前校验结果失败</li>
     *    <li>不需要：跳过校验，返回成功</li>
     * </ul>
     */
    boolean nullCheck() default true;

    class DigitalValidator implements ConstraintValidator<Digital, Integer> {

        private int max;

        private int min;

        private int[] include;

        private int[] exclude;

        private boolean nullCheck;

        @Override
        public void initialize(Digital constraintAnnotation) {
            this.max = constraintAnnotation.max();
            this.min = constraintAnnotation.min();
            this.include = constraintAnnotation.include();
            this.exclude = constraintAnnotation.exclude();
            this.nullCheck = constraintAnnotation.nullCheck();
        }

        @Override
        public boolean isValid(Integer value, ConstraintValidatorContext context) {
            //先判断数值大小
            if (value == null) {
                return !nullCheck;
            }

            for (int i : include) {
                if (i == value) return true;
            }

            for (int i : exclude) {
                if (i == value) return false;
            }

            return !(max == min) && value >= min && value <= max;
        }
    }
}
