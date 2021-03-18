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

import com.maydear.core.framework.exception.NotFoundFileException;
import com.maydear.core.framework.io.FileSummary;
import com.maydear.core.framework.util.LocalFileUtils;
import com.maydear.core.framework.util.OsUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 本地文件存储基础设施
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
@Slf4j
public class LocalFileStorageInfrastructure implements FileStorageInfrastructure {

    /**
     *
     */
    private final LocalFileOptions options;

    /**
     * 构造函数
     *
     * @param options 本地文件系统选项
     */
    public LocalFileStorageInfrastructure(LocalFileOptions options) {
        this.options = options;
    }

    /**
     * 针对操作系统类型转换路径分隔符
     *
     * @param path 路径
     * @return 返回转换后的路径
     */
    private String conversionOsPath(String path) {
        String regexSeparator = "/";
        if (OsUtils.isWindows()) {
            regexSeparator = "\\\\";
        }
        return RegExUtils.replaceAll(path, regexSeparator, File.separator);
    }

    /**
     * @param absolutePath 路径
     * @param bytes        字节形式的文件内容
     */
    private void write(String absolutePath, byte[] bytes) {
        Path path = getPath(absolutePath);
        LocalFileUtils.write(path, bytes);
    }

    private Path getPath(String absolutePath) {
        return Paths.get(absolutePath);
    }

    /**
     * 将文件写入临时目录
     *
     * @param fileSummary 文件摘要
     * @param bytes       字节形式的文件内容
     */
    @Override
    public void writeTemp(FileSummary fileSummary, byte[] bytes) {
        fileSummary.setStorageDirectory(options.getFullTempDirectory());
        if (StringUtils.isBlank(fileSummary.getStoragePath())) {
            return;
        }
        log.info(fileSummary.getStoragePath());
        fileSummary.setType(FileSummary.TEMP_TYPE);
        write(fileSummary.getStoragePath(), bytes);
        if (StringUtils.isBlank(fileSummary.getMd5())) {
            String fileMd5HexString = LocalFileUtils.getMd5(fileSummary.getStoragePath());
            fileSummary.setMd5(fileMd5HexString);
        }

        if (fileSummary.getSize() <= 0) {
            Path path = getPath(fileSummary.getStoragePath());
            long fileSize = LocalFileUtils.getSize(path);
            fileSummary.setSize(fileSize);
        }
    }

    /**
     * 永久固化
     *
     * @param fileSummary 文件摘要
     */
    @Override
    public FileSummary persistence(final FileSummary fileSummary) {
        if (StringUtils.isBlank(fileSummary.getStoragePath())) {
            throw new NotFoundFileException();
        }
        FileSummary targetFileSummary = ObjectUtils.clone(fileSummary);
        Path sourcePath = getPath(fileSummary.getStoragePath());
        targetFileSummary.setStorageDirectory(options.getFullPersistenceDirectory());
        targetFileSummary.setType(FileSummary.PERSISTENCE_TYPE);
        Path targetPath = getPath(targetFileSummary.getStoragePath());
        LocalFileUtils.copy(sourcePath, targetPath);
        return targetFileSummary;
    }

    /**
     * 读取文件
     *
     * @param fileSummary 文件摘要
     * @return 返回文件字节
     */
    @Override
    public byte[] read(FileSummary fileSummary) {
        if (ObjectUtils.isEmpty(fileSummary)) {
            throw new NotFoundFileException();
        }
        return read(fileSummary.getStoragePath());
    }

    /**
     * 读取文件
     *
     * @param absolutePath 路径
     * @return 返回文件字节
     */
    private byte[] read(String absolutePath) {
        Path path = getPath(absolutePath);
        return LocalFileUtils.read(path);
    }
}
