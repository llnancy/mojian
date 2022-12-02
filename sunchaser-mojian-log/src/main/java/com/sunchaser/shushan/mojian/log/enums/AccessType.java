package com.sunchaser.shushan.mojian.log.enums;

/**
 * access log type
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/12/2
 */
public enum AccessType {

    /**
     * 查询
     */
    SELECT,

    /**
     * 新增
     */
    INSERT,

    /**
     * 更新
     */
    UPDATE,

    /**
     * 删除
     */
    DELETE,

    /**
     * 登录
     */
    LOGIN,

    /**
     * 导入
     */
    IMPORT,

    /**
     * 导出
     */
    EXPORT,

    /**
     * 更新状态
     */
    STATUS,

    /**
     * 清除数据
     */
    CLEAN,

    /**
     * 其它
     */
    OTHER,
    ;
}
