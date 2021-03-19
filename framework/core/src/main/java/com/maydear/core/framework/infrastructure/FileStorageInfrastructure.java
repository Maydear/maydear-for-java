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
package com.maydear.core.framework.infrastructure;

import com.maydear.core.framework.io.FileSummary;

/**
 * 文件存储基础设施
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
public interface FileStorageInfrastructure {

    /**
     * 将文件写入临时目录
     *
     * @param bytes       字节形式的文件内容
     * @param fileSummary 文件摘要
     */
    void writeTemp(FileSummary fileSummary, byte[] bytes);

    /**
     * 将文件直接写入永久存储目录
     *
     * @param bytes       字节形式的文件内容
     * @param fileSummary 文件摘要
     */
    void writePersistence(FileSummary fileSummary, byte[] bytes);

    /**
     * 将文件从临时目录写入永久存储目录
     *
     * @param fileSummary 文件摘要
     * @return 返回写入永久存储目录的路径
     */
    FileSummary writePersistence(final FileSummary fileSummary);

    /**
     * 将永久存储目录的文件读取为文件字节数组
     *
     * @param fileSummary 文件摘要
     * @return 返回文件的字节数组
     */
    byte[] read(FileSummary fileSummary);
}
