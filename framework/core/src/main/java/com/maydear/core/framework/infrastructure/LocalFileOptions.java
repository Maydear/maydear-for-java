package com.maydear.core.framework.infrastructure;

import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * 本地文件选项
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class LocalFileOptions implements Serializable {

    private static final String SEQUENCE = "/";

    /**
     * 根目录
     */
    @Setter
    private String root;

    /**
     * 临时目录
     */
    @Setter
    private String tempDirectory = "temp";

    /**
     * 持久目录
     */
    @Setter
    private String persistenceDirectory = "persistence";

    /**
     * 获取全路径临时目录
     *
     * @return 返回全路径临时目录
     */
    public String getFullTempDirectory() {
        StringBuilder stringBuffer = new StringBuilder(SEQUENCE);

        if (StringUtils.isNotBlank(tempDirectory) && StringUtils.endsWithAny(SEQUENCE, tempDirectory)) {
            tempDirectory = StringUtils.substring(tempDirectory, 0, tempDirectory.length() - 1);
        }

        if (StringUtils.isNotBlank(tempDirectory) && StringUtils.startsWithAny(SEQUENCE, tempDirectory)) {
            tempDirectory = StringUtils.substring(tempDirectory, 1, tempDirectory.length());
        }

        if (StringUtils.isBlank(root)) {
            return "." + stringBuffer.append(tempDirectory).toString();
        } else {
            return stringBuffer.append(root).append(tempDirectory).toString();
        }
    }

    /**
     * 获取全路径持久目录
     *
     * @return 返回全路径持久目录
     */
    public String getFullPersistenceDirectory() {
        StringBuilder stringBuffer = new StringBuilder(SEQUENCE);


        if (StringUtils.isNotBlank(persistenceDirectory) && StringUtils.endsWithAny(SEQUENCE, persistenceDirectory)) {
            tempDirectory = StringUtils.substring(tempDirectory, 0, tempDirectory.length() - 1);
        }

        if (StringUtils.isNotBlank(persistenceDirectory) && StringUtils.startsWithAny(SEQUENCE, persistenceDirectory)) {
            tempDirectory = StringUtils.substring(persistenceDirectory, 1, tempDirectory.length());
        }

        if (StringUtils.isBlank(root)) {
            return "." + stringBuffer.append(persistenceDirectory).toString();
        } else {
            return stringBuffer.append(root).append(persistenceDirectory).toString();
        }
    }
}
