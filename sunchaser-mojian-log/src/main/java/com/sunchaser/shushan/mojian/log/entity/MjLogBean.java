package com.sunchaser.shushan.mojian.log.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * mj log bean
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/10/29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MjLogBean {

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
     * user-agent
     */
    private String userAgent;

    /**
     * request ip
     */
    private String requestIp;

    /**
     * request uri
     */
    private String requestUri;

    /**
     * request method
     */
    private String requestMethod;

    /**
     * 请求类名 + 方法名
     */
    private String service;

    /**
     * request status
     */
    private RequestStatus requestStatus;

    /**
     * request parameters
     */
    private String parameters;

    /**
     * response
     */
    private String response;

    /**
     * error msg
     */
    private String errorMsg;

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
