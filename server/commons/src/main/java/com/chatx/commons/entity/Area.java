package com.chatx.commons.entity;

import com.chatx.commons.utils.AreaUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 行政区划
 */
public record Area(String code, String name, String parentCode) {

    /** @see AreaUtils#isProvince(String) */
    @JsonIgnore
    public boolean isProvince(){
        return AreaUtils.isProvince(code);
    }

    /** @see AreaUtils#isCity(String)  */
    @JsonIgnore
    public boolean isCity(){
        return AreaUtils.isCity(code);
    }

    /** @see AreaUtils#isDistrict(String) */
    @JsonIgnore
    public boolean isDistrict(){
        return AreaUtils.isDistrict(code);
    }
}
