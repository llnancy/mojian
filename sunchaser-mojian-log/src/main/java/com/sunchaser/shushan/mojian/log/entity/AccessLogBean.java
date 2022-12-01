package com.sunchaser.shushan.mojian.log.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * access log bean
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/10/29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccessLogBean {

    /**
     * app id
     */
    private String appId;

    /**
     * env
     */
    private String env;

    /**
     * traceId
     */
    private String traceId;

    /**
     * spanId
     */
    private String spanId;

    /**
     * request ip
     */
    private String requestIp;

    /**
     * device type
     */
    private String deviceType;

    /**
     * browser
     */
    private String browser;

    /**
     * os
     */
    private String os;

    /**
     * region
     */
    private String region;

    /**
     * request uri
     */
    private String requestUri;

    /**
     * request method
     */
    private String requestMethod;

    /**
     * 请求类名
     */
    private String className;

    /**
     * 请求方法名
     */
    private String methodName;

    /**
     * request status
     */
    private RequestStatus status;

    /**
     * request parameters
     */
    private String parameters;

    /**
     * response
     */
    private String response;

    /**
     * exception msg
     */
    private String exception;

    /**
     * start time
     */
    private LocalDateTime startTime;

    /**
     * end time
     */
    private LocalDateTime endTime;

    /**
     * request time. unit: ms
     */
    private Long rt;

    /**
     * 请求状态枚举
     */
    public enum RequestStatus {

        /**
         * 请求状态
         */
        SUCCESS, EXCEPTION
    }
}
