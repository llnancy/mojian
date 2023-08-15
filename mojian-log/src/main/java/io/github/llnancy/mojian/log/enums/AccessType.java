package io.github.llnancy.mojian.log.enums;

/**
 * access log type
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/07/10
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
