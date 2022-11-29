<div align="center">
    <img src="https://cdn.lilu.org.cn/sunchaser-logo.png" alt="logo" />
    <h2 align="center">sunchaser-mojian</h2>
    <div align="center">
        魔剑：微服务开发脚手架
        <br /><br />
        <img src="https://img.shields.io/circleci/project/github/badges/shields/master?color=%231ab1ad&label=master" alt="project" />
        <img src="https://img.shields.io/badge/JDK-8.0+-0e83c" alt="java-version" />
        <img src="https://img.shields.io/github/license/sunchaser-lilu/sunchaser-rpc?color=FF5531" alt="license" />
    </div>
</div>

# 魔剑

由景天的前世姜国太子龙阳所铸，因姜国公主龙葵的室女之血而成。

# 概述

`sunchaser-mojian` 是一个微服务业务开发脚手架，致力于让业务开发更高效。

# 组件列表

- `sunchaser-mojian-base`: 提供 `HTTP` 接口统一响应体、常用业务状态码枚举及常用工具类等。
- `sunchaser-mojian-web`: 提供 `Web` 业务开发常用功能，包含全局异常处理器、`Jackson` 日期格式化配置、`JSR303` 分组校验接口及快速失败配置。
- `sunchaser-mojian-log`: 基于 `Spring AOP` 和注解实现的日志记录组件。
