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
package com.maydear.core.framework.io;

import com.maydear.core.framework.exception.FileSummaryParseException;
import com.maydear.core.framework.util.Base64Utils;
import com.maydear.core.framework.util.DateTimeUtils;
import com.maydear.core.framework.util.UUIDUtils;
import lombok.*;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.StringJoiner;

/**
 * 文件摘要信息
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
@Data
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
@Builder
@With
public class FileSummary implements Serializable {

    private static final long serialVersionUID = 5942953451852501636L;

    /**
     * 分割符号
     */
    private static final char SEPARATOR = ':';

    /**
     * 串结构长度
     */
    private static final int ARRAY_LENGTH = 12;

    /**
     * 文件标志
     */
    private String id;

    /**
     * 摘要类型
     */
    private String type;

    /**
     * 标志
     */
    private String tag;

    /**
     * 文件MD5
     */
    private String md5;

    /**
     * 文件contentType
     */
    private String contentType;

    /**
     * 源文件名
     */
    private String originalFilename;

    /**
     * 文件扩展名
     */
    private String extendFilename;

    /**
     * 扩展信息
     */
    private String extended;

    /**
     * 存储路径
     */
    @Setter
    private String storagePath;

    /**
     * 存储根目录
     */
    private String storageDirectory;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 文件大小
     */
    private long size;

    /**
     * 解析对象
     *
     * @param fileSummaryBase64Str 获取文件信息
     * @return 返回文件摘要信息
     */
    public static FileSummary parse(String fileSummaryBase64Str) {
        String fileSummaryString = Base64Utils.decode(fileSummaryBase64Str);
        String[] fileSummaryStringArray = StringUtils.split(fileSummaryString, SEPARATOR);

        if (fileSummaryStringArray.length == ARRAY_LENGTH) {
            try {
                return FileSummary.builder()
                    .type(fileSummaryStringArray[0])
                    .id(fileSummaryStringArray[1])
                    .tag(fileSummaryStringArray[2])
                    .md5(fileSummaryStringArray[3])
                    .contentType(fileSummaryStringArray[4])
                    .originalFilename(fileSummaryStringArray[5])
                    .extendFilename(fileSummaryStringArray[6])
                    .size(Long.parseLong(fileSummaryStringArray[7]))
                    .storagePath(fileSummaryStringArray[8])
                    .storageDirectory(fileSummaryStringArray[9])
                    .createTime(DateTimeUtils.formUnixTimeMilliseconds(Long.parseLong(fileSummaryStringArray[10])))
                    .extended(fileSummaryStringArray[11])
                    .build();
            } catch (NumberFormatException numberFormatException) {
                throw new FileSummaryParseException();
            }
        } else {
            throw new FileSummaryParseException();
        }
    }

    /**
     * 生成存储路径
     */
    public void buildStoragePath() {
        if (ObjectUtils.isEmpty(createTime)) {
            createTime = LocalDateTime.now();
        }
        if (StringUtils.isBlank(id)) {
            id = UUIDUtils.generateNoUnderline();
        }
        String dateDirectory = MessageFormat.format("{0}/{1}/{2}", createTime.getYear(), createTime.getMonth(), createTime.getDayOfMonth());
        storagePath = MessageFormat.format("{0}/{1}/{2}", storageDirectory, dateDirectory, id);
    }

    /**
     * 获取存储路径
     *
     * @return 返回完整存储路径
     */
    public String getStoragePath() {
        if (StringUtils.isBlank(storagePath)) {
            buildStoragePath();
        }

        return storagePath;
    }

    /**
     * 编码成Base64
     *
     * @return 返回编码后的数据
     */
    public String encodeBase64() {
        StringJoiner stringJoiner = new StringJoiner(String.valueOf(SEPARATOR))
            .add(getType())
            .add(getId())
            .add(getTag())
            .add(getMd5())
            .add(getContentType())
            .add(getOriginalFilename())
            .add(getExtendFilename())
            .add(String.valueOf(getSize()))
            .add(getStoragePath())
            .add(getStorageDirectory())
            .add(String.valueOf(DateTimeUtils.toUnixTimeMilliseconds(getCreateTime())))
            .add(getExtended());

        return Base64Utils.encode(stringJoiner.toString());
    }
}
