package com.chatx.commons.constrait;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import javax.validation.constraints.Size;
import java.lang.annotation.*;

/**
 * 用于检查 {@link java.util.Collection}. 功能为 {@link javax.validation.constraints.NotEmpty} 和 {@link javax.validation.constraints.Size}
 * 的组合
 *
 * @author Jun
 * @see javax.validation.constraints.NotEmpty
 * @see javax.validation.constraints.Size
 * @since 1.0.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = NotEmptyAndSize.NotEmptyAndSizeValidator.class)
public @interface NotEmptyAndSize {

    String message() default "集合参数为空或长度超过限制";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * @see Size#max()
     */
    int max() default Integer.MAX_VALUE;

    /**
     * 最小长度限制.
     * 注意：min 小于 <strong>1</strong> 没有意义，因为我们会执行
     * <pre>
     *     var value = collection;
     *     if (value == null || value.isEmpty()) return false;
     * </pre>
     * @see Size#min()
     */
    int min() default 1;

    class NotEmptyAndSizeValidator implements ConstraintValidator<NotEmptyAndSize, java.util.Collection<?>> {

        private int max;
        private int min;

        @Override
        public void initialize(NotEmptyAndSize constraintAnnotation) {
            this.max = constraintAnnotation.max();
            this.min = constraintAnnotation.min();
        }

        @Override
        public boolean isValid(java.util.Collection<?> value, ConstraintValidatorContext context) {
            if (value == null || value.isEmpty()) {
                return false;
            }
            final var size = value.size();
            return size >= min && size <= max;
        }
    }
}
