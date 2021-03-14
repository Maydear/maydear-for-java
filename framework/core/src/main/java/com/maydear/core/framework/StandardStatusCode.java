/*
 * Copyright 2008-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.maydear.core.framework;

import lombok.Getter;

/**
 * 标准状态码
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
@Getter
public enum StandardStatusCode {

    /**
     * (2000)正常请求
     */
    OK(2000, null),

    /**
     * (2400)运行时错误
     */
    BAD_REQUEST(2400, "运行时错误"),

    /**
     * (2401)需要授权访问资源
     */
    UNAUTHORIZED(2401, "需要授权访问资源"),

    /**
     * (2403)访问资源被拒绝
     */
    FORBIDDEN(2403, "访问资源被拒绝"),

    /**
     * (2404)未找到资源
     */
    NOT_FOUND(2404, "未找到资源"),

    /**
     *  (2406)授权已过期，请刷新授权
     */
    AUTHORIZED_EXPIRED(2406, "授权已过期，请刷新授权"),

    /**
     * (2410)被请求的资源在服务器上已经不再可用
     */
    GONE(2410, "被请求的资源在服务器上已经不再可用"),

    /**
     * (2422)无法处理的实体,
     */
    UNPROCESSABLE_ENTITY(2422, "无法处理的实体"),

    /**
     * (2412)请求的头缺少必须字段
     */
    PRECONDITION_FAILED(2412, "请求的头缺少必须字段"),

    /**
     * (2500)请求的头缺少必须字段
     */
    NOT_IMPLEMENTED(2500, "未实现方法"),

    /**
     * (2900)加解密异常，轻联系管理员查看日志
     */
    ENCRYPTION_ERROR(2900, "加解密异常，请联系管理员查看日志！"),

    /**
     * (2901)文件摘要解析异常，请联系管理员查看日志！
     */
    FILE_SUMMARY_PARSE(2901, "文件摘要解析异常，请联系管理员查看日志！"),

    /**
     * (2902)复制文件异常
     */
    FAILED_COPY_FILE(2902, "复制文件异常，请联系管理员查看日志！"),

    /**
     * (2903)写入文件异常
     */
    FAILED_WRITE_FILE(2903, "写入文件异常，请联系管理员查看日志！"),

    /**
     * (2904)读取文件异常
     */
    FAILED_READ_FILE(2904, "读取文件异常，请联系管理员查看日志！"),

    /**
     * (2905)文件不存在的异常
     */
    NOT_FOUND_FILE(2905, "文件不存在的异常，请联系管理员查看日志！"),

    /**
     * (2906)执行反射操作错误
     */
    REFLECTION_ERROR(2906, "执行反射操作错误。"),

    /**
     * (2907)解析Lambda表达式错误
     */
    SERIALIZED_LAMBDA_ERROR(2907, "解析Lambda表达式错误。"),

    /**
     * (99999)系统异常
     */
    SYSTEM_ERROR(99999, "系统异常")

    ;

    /**
     * 编码
     */
    private int code;

    /**
     * 消息
     */
    private String message;

    /**
     * 构造函数
     *
     * @param code 错误码
     * @param message 错误信息
     */
    StandardStatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
