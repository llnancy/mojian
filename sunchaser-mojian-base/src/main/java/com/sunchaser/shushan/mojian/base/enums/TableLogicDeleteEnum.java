package com.sunchaser.shushan.mojian.base.enums;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

/**
 * 表逻辑删除字段 is_deleted 值枚举
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/7/17
 */
@Getter
@AllArgsConstructor
public enum TableLogicDeleteEnum {

    /**
     * normal
     */
    NORMAL(0, "正常"),

    /**
     * deleted
     */
    DELETED(1, "删除"),
    ;

    private final Integer state;

    private final String desc;

    private static final Map<Integer, TableLogicDeleteEnum> ENUM_MAP = Maps.newHashMap();

    static {
        for (TableLogicDeleteEnum tableLogicDeleteEnum : TableLogicDeleteEnum.values()) {
            ENUM_MAP.put(tableLogicDeleteEnum.state, tableLogicDeleteEnum);
        }
    }

    public static TableLogicDeleteEnum match(Integer state) {
        return ENUM_MAP.get(state);
    }
}
