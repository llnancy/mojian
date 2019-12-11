# sunchaser-boot-dependencies
微服务架构下，我们需要统一管理各个微服务的jar包依赖，避免同一个项目下的不同微服务引用的jar包版本不一致问题。

将一些常用jar包依赖使用dependencyManagement进行预定义管理，对外提供一个统一的pom依赖。

目前包含以下jar包依赖：

dependency | version
:---:|:---:
Spring Boot | 2.1.6
Spring Cloud | Greenwich.SR3
Spring Cloud Alibaba | 2.1.0.RELEASE
lombok | 1.18.10
guava | 28.1-jre
Apache commons lang3 | 3.9
Mybatis starter | 2.1.0
Druid starter | 1.1.10
Alibaba Fastjson | 1.2.60
okhttp | 3.11.0
Guava retrying | 2.0.0
Curator Framework | 4.1.0
Apache ShardingSphere Starter | 4.0.0-RC1

## 引用方式
```
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>com.sunchaser.boot</groupId>
                <artifactId>sunchaser-boot-dependencies</artifactId>
                <version>1.0-SNAPSHOT</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
```