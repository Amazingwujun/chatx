package com.chatx.commons.utils;

import com.chatx.commons.entity.Area;
import com.chatx.commons.exception.ChatxException;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 中国行政区划工具类
 *
 * @author Jun
 * @since 1.0.0
 */
public class AreaUtils {
    //@formatter:off

    public static final String CHINA_CODE = "CHN";
    public static final Area CHINA = new Area(CHINA_CODE, "中国", null);
    public static final List<Area> ALL = new ArrayList<>();
    public static final List<Area> PROVINCES;
    public static final List<Area> CITIES;
    public static final List<Area> DISTRICTS;
    public static final Map<String, List<Area>> CODE_CHILDREN_MAP;
    public static final Map<String, Area> CODE_AREA_MAP;

    //@formatter:on

    static {
        List<String> lines;
        try {
            final var res = new ClassPathResource("china_region.txt");
            final var buf = new BufferedReader(new InputStreamReader(res.getInputStream()));
            lines = buf.lines().toList();
        } catch (IOException e) {
            throw new ChatxException("行政区划数据初始化失败: %s", e.getMessage());
        }

        var areas = lines.stream()
                .filter(s -> !s.startsWith("#")) // 跳过注释
                .map(s -> s.split("\\t"))
                .filter(strs -> strs.length == 2)
                .map(strs -> {
                    var code = strs[0].trim();
                    var name = strs[1].trim();
                    var parentCode = parentCode(code);
                    return new Area(code, name, parentCode);
                })
                .toList();
        ALL.addAll(areas);
        ALL.add(CHINA);
        PROVINCES = ALL.stream().filter(Area::isProvince).toList();
        CITIES = ALL.stream().filter(Area::isCity).toList();
        DISTRICTS = ALL.stream().filter(Area::isDistrict).toList();
        CODE_CHILDREN_MAP = ALL.stream().filter(area -> CHINA != area).collect(Collectors.groupingBy(Area::parentCode));
        CODE_AREA_MAP = ALL.stream().collect(Collectors.toMap(Area::code, Function.identity()));
    }

    /**
     * 通过区划代码获取 {@link Area} 对象
     *
     * @param code 区划代码
     */
    public static Area area(String code) {
        return CODE_AREA_MAP.get(code);
    }

    /**
     * 省和直辖市
     *
     * @param code 行政区划代码
     * @return true if code belongs province
     */
    public static boolean isProvince(String code) {
        return "0000".equals(code.substring(2));
    }

    /**
     * 判断当前区划是否属于下属类型
     * <ol>
     *     <li>地级市</li>
     *     <li>省管县</li>
     *     <li>直辖市下辖区县</li>
     * </ol>
     *
     * @param code 行政区划代码
     * @return true if code belongs city
     */
    public static boolean isCity(String code) {
        if (CHINA_CODE.equals(code)) {
            return false;
        }

        return !isProvince(code) &&
                (
                        "00".equals(code.substring(4)) // 地级市
                                || code.charAt(2) == '9' // 省管县
                                || isMunicipality(code.substring(0, 2) + "0000") // 直辖市下辖区县
                );
    }

    /**
     * 当前区划是否为县、区
     *
     * @param code 行政区划代码
     * @return true if code belongs district(County)
     */
    public static boolean isDistrict(String code) {
        if (CHINA_CODE.equals(code)) {
            return false;
        }

        return !isProvince(code) && !isCity(code);
    }

    /**
     * 判断区划代码是否为直辖市
     *
     * @param code 行政区划代码
     * @return true if code belongs municipality
     */
    private static boolean isMunicipality(String code) {
        boolean r;
        switch (code) {
            case "110000", "120000", "500000", "310000" -> r = true;
            default -> r = false;
        }
        return r;
    }

    /**
     * 获取指定行政区划的下级
     *
     * @param code 行政区划代码
     * @return 当前区划的下辖区划
     */
    public static List<Area> children(String code) {
        return CODE_CHILDREN_MAP.get(code);
    }

    /**
     * 获取执行行政区划代码的上级行政区划代码
     *
     * @param code 行政区划嗲吗
     * @return parent area code
     */
    public static String parentCode(String code) {
        // 省
        if (isProvince(code)) {
            return CHINA_CODE;
        }

        // 市
        if (isCity(code)) {
            return code.substring(0, 2) + "0000";
        }

        // 区、县
        return code.substring(0, 4) + "00";
    }
}
