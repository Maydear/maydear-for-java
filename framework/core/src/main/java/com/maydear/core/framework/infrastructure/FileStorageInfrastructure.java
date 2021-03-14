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
     * 将文件写入
     *
     * @param bytes       字节形式的文件内容
     * @param fileSummary 文件摘要
     */
    void write(FileSummary fileSummary, byte[] bytes);

    /**
     * 复制文件
     *
     * @param targetDirectory   目标目录
     * @param sourceFileSummary 源文件信息
     * @return 返回复制后得文件信息
     */
    FileSummary copy(FileSummary sourceFileSummary, final String targetDirectory);

    /**
     * 将文件读取
     *
     * @param fileSummary 文件摘要
     * @return 返回文件的字节
     */
    byte[] read(FileSummary fileSummary);
}
