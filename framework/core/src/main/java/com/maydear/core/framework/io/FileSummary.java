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
public class FileSummary implements Serializable, Cloneable {

    private static final long serialVersionUID = 5942953451852501636L;

    /**
     * 分割符号
     */
    private static final char SEPARATOR = ':';

    /**
     * 串结构长度
     */
    private static final int ARRAY_LENGTH = 10;

    /**
     * 临时
     */
    public static final String TEMP_TYPE = "T";

    /**
     * 持久
     */
    public static final String PERSISTENCE_TYPE = "P";

    /**
     * 文件标志
     */
    private String id;

    /**
     * 摘要类型
     */
    private String type = TEMP_TYPE;

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
     * 扩展信息
     */
    private String extended;

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
        int typeIndex = 0;
        int idIndex = 1;
        int tagIndex = 2;
        int md5Index = 3;
        int contentTypeIndex = 4;
        int originalFilenameIndex = 5;
        int sizeIndex = 6;
        int storageDirectoryIndex = 7;
        int createTimeIndex = 8;
        int extendedIndex = 9;
        if (fileSummaryStringArray.length == ARRAY_LENGTH) {
            try {
                FileSummary fileSummary = new FileSummary();
                if (StringUtils.isNotBlank(fileSummaryStringArray[typeIndex])) {
                    fileSummary.setType(fileSummaryStringArray[typeIndex]);
                }
                if (StringUtils.isNotBlank(fileSummaryStringArray[idIndex])) {
                    fileSummary.setId(fileSummaryStringArray[idIndex]);
                }
                if (StringUtils.isNotBlank(fileSummaryStringArray[tagIndex])) {
                    fileSummary.setTag(fileSummaryStringArray[tagIndex]);
                }
                if (StringUtils.isNotBlank(fileSummaryStringArray[md5Index])) {
                    fileSummary.setMd5(fileSummaryStringArray[md5Index]);
                }
                if (StringUtils.isNotBlank(fileSummaryStringArray[contentTypeIndex])) {
                    fileSummary.setContentType(fileSummaryStringArray[contentTypeIndex]);
                }
                if (StringUtils.isNotBlank(fileSummaryStringArray[originalFilenameIndex])) {
                    fileSummary.setOriginalFilename(fileSummaryStringArray[originalFilenameIndex]);
                }

                if (StringUtils.isNotBlank(fileSummaryStringArray[sizeIndex])) {
                    fileSummary.setSize(Long.parseLong(fileSummaryStringArray[sizeIndex]));
                }
                if (StringUtils.isNotBlank(fileSummaryStringArray[storageDirectoryIndex])) {
                    fileSummary.setStorageDirectory(fileSummaryStringArray[storageDirectoryIndex]);
                }
                if (StringUtils.isNotBlank(fileSummaryStringArray[createTimeIndex])) {
                    fileSummary.setCreateTime(DateTimeUtils.formUnixTimeMilliseconds(Long.parseLong(fileSummaryStringArray[createTimeIndex])));
                }
                if (StringUtils.isNotBlank(fileSummaryStringArray[extendedIndex])) {
                    fileSummary.setExtended(fileSummaryStringArray[extendedIndex]);
                }
                return fileSummary;
            } catch (NumberFormatException numberFormatException) {
                throw new FileSummaryParseException();
            }
        } else {
            throw new FileSummaryParseException();
        }
    }

    /**
     * 获取存储路径
     *
     * @return 返回完整存储路径
     */
    public String getStoragePath() {
        if (ObjectUtils.isEmpty(createTime)) {
            createTime = LocalDateTime.now();
        }
        if (StringUtils.isBlank(id)) {
            id = UUIDUtils.generateNoUnderline();
        }
        String dateDirectory = MessageFormat.format("{0}/{1}/{2}", String.valueOf(createTime.getYear()), String.valueOf(createTime.getMonth().getValue()), String.valueOf(createTime.getDayOfMonth()));
        if (StringUtils.isNotBlank(storageDirectory)) {
            return MessageFormat.format("{0}/{1}/{2}", storageDirectory, dateDirectory, id);
        }
        return MessageFormat.format("{0}/{1}", dateDirectory, id);
    }

    /**
     * 文件扩展名
     */
    public String getExtendFilename() {
        if (StringUtils.isNotBlank(originalFilename)) {
            return StringUtils.substringBeforeLast(originalFilename, ".");
        }
        return null;
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
            .add(String.valueOf(getSize()))
            .add(getStorageDirectory())
            .add(String.valueOf(DateTimeUtils.toUnixTimeMilliseconds(getCreateTime())))
            .add(getExtended());

        return Base64Utils.encode(stringJoiner.toString());
    }

    public boolean isTempFile() {
        return StringUtils.equalsAnyIgnoreCase(type, "");
    }


    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
