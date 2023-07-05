package com.chatx.useradmin.constants;

/**
 * 性别枚举
 *
 * @author Jun
 * @since 1.0.0
 */
public enum GenderEnum {
    male(1), female(2);

    private final int val;

    GenderEnum(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }
}
