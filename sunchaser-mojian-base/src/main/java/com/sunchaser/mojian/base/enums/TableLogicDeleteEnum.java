package com.sunchaser.mojian.base.enums;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

/**
 * 表state状态枚举
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/7/17
 */
@Getter
@AllArgsConstructor
public enum TableLogicDeleteEnum {
    NORMAL(0, "正常"),
    DELETED(1, "已删除")
    ;
    private final Integer state;
    private final String desc;
    private static final Map<Integer, TableLogicDeleteEnum> enumMap = Maps.newHashMap();

    static {
        for (TableLogicDeleteEnum tableLogicDeleteEnum : TableLogicDeleteEnum.values())
            enumMap.put(tableLogicDeleteEnum.state, tableLogicDeleteEnum);
    }

    public static TableLogicDeleteEnum getTableLoginDeleteEnumByState(Integer state) {
        return enumMap.get(state);
    }
}
