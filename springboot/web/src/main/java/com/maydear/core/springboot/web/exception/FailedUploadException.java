package com.maydear.core.springboot.web.exception;

import com.maydear.core.framework.exception.StatusCodeException;

/**
 * （10008）文件上传失败时抛出的异常
 *
 * @author joshua.jiang
 */
public class FailedUploadException extends StatusCodeException {

    /**
     * 异常码
     */
    private static final int CODE = 3003;

    /**
     * 异常消息
     */
    private static final String MESSAGE = "文件上传出错咯";

    /**
     * 构造无参数异常
     */
    public FailedUploadException() {
        super(CODE, MESSAGE);
    }

    /**
     * 构造带异常信息的异常
     *
     * @param cause 异常信息
     */
    public FailedUploadException(Throwable cause) {
        super(CODE, MESSAGE, cause);
    }
}
