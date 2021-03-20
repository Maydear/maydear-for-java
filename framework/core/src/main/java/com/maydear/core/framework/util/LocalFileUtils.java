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
package com.maydear.core.framework.util;

import com.maydear.core.framework.exception.FailedCopyFileException;
import com.maydear.core.framework.exception.FailedReadFileException;
import com.maydear.core.framework.exception.FailedWriteFileException;
import com.maydear.core.framework.exception.NotFoundFileException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.*;

/**
 * 本地磁盘文件工具类
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
public class LocalFileUtils {
    /**
     * 文件名与扩展名的分隔符
     */
    private static final String FILENAME_SEPARATOR = ".";

    private LocalFileUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 验证路径是否文件
     *
     * @param path 路径
     * @return boolean
     */
    public static boolean isFile(String path) {
        return isFile(Paths.get(path));
    }

    /**
     * 验证路径是否文件
     *
     * @param path 路径
     * @return boolean
     */
    public static boolean isFile(Path path) {
        return path.toFile().isFile() && path.toFile().exists();
    }

    /**
     * 创建指定路径中不存在的目录和文件，如果文件存在，则会删除已有的文件后创建新文件。
     *
     * @param path 路径
     * @return Path
     * @throws IOException 创建文件发生错误时抛出的异常
     */
    public static Path createFileIfExists(String path) throws IOException {
        return createFileIfExists(Paths.get(path));
    }

    /**
     * 创建指定路径中不存在的目录和文件，如果文件存在，则会删除已有的文件后创建新文件。
     *
     * @param path 路径
     * @return Path
     * @throws IOException 创建文件发生错误时抛出的异常
     */
    public static Path createFileIfExists(Path path) throws IOException {
        //在JDK 8中，Files.exists方法的性能明显较差，并且在用于检查实际上不存在的文件时，可能会严重降低应用程序的速度。
        if (!path.toFile().exists()) {
            Files.createDirectories(path);
        }
        Files.deleteIfExists(path);
        return Files.createFile(path);
    }

    /**
     * 文件是否存在
     *
     * @param path 路径对象
     * @return 如果路径指定文件或文件夹存在则返回true，反之则为false;
     */
    public static boolean exists(Path path) {
        return path.toFile().exists();
    }

    /**
     * 获取带.的文件扩展名
     *
     * @param path 文件路径
     * @return String
     */
    public static String getExtension(String path) {
        if (StringUtils.isBlank(path) ||
            !StringUtils.contains(path, FILENAME_SEPARATOR)) {
            return "";
        }
        return FILENAME_SEPARATOR + getShotExtension(path);
    }

    /**
     * 获取不带.的文件扩展名
     *
     * @param path 文件路径
     * @return String
     */
    public static String getShotExtension(String path) {
        if (StringUtils.isBlank(path) ||
            !StringUtils.contains(path, FILENAME_SEPARATOR)) {
            return "";
        }
        return StringUtils.substringAfterLast(path, FILENAME_SEPARATOR);
    }

    /**
     * 复制文件
     *
     * @param source 原始路径
     * @param target 目标路径
     */
    public static void copy(Path source, Path target) {
        Assert.isNotNull(source, "[原始路径]不能为空。");
        Assert.isNotNull(target, "[目标路径]不能为空。");
        try {
            if (!Files.exists(source)) {
                return;
            }
            LocalFileUtils.createFileIfExists(target);
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new FailedCopyFileException();
        }
    }

    /**
     * 将文件写入磁盘
     *
     * @param bytes 字节形式的文件内容
     * @param path  路径
     */
    public static void write(Path path, byte[] bytes) {
        Assert.isNotNull(path, "[路径]不能为空。");
        Assert.isNotNull(bytes, "[文件内容]不能为空。");
        try {
            LocalFileUtils.createFileIfExists(path);
            Files.write(path, bytes);
        } catch (IOException e) {
            throw new FailedWriteFileException();
        }
    }

    /**
     * 读取文件
     *
     * @param path 路径
     */
    public static byte[] read(Path path) {
        Assert.isNotNull(path, "[路径]不能为空。");
        if (!exists(path)) {
            throw new NotFoundFileException();
        }
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new FailedReadFileException();
        }
    }

    /**
     * 获取文件大小
     *
     * @param path
     * @return
     */
    public static long getSize(Path path) {
        try {

            return Files.size(path);
        } catch (IOException e) {
            throw new FailedReadFileException();
        }
    }

    /**
     * 获取文件的Md5值
     *
     * @param path 文件路径
     * @return
     */
    public static String getMd5(Path path) {
        try {
            if (path.toFile().isFile()) {
                return DigestUtils.md5Hex(Files.newInputStream(path, StandardOpenOption.READ));
            }
            return StringUtils.EMPTY;
        } catch (IOException e) {
            throw new NotFoundFileException();
        }

    }

}
