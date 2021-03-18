package com.maydear.core.sample.web.model;

import lombok.*;

import java.io.Serializable;

/**
 * 附件
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Attachment implements Serializable {

    private static final long serialVersionUID = 8652668678809712984L;

    /**
     * 标签
     */
    private String label;

    /**
     * Base64编码的文件信息
     */
    private String code;
}