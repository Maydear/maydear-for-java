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

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

/**
 * 分页对象
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
public interface Page<T extends Serializable> extends Serializable {

    /**
     * 获取分页记录列表
     *
     * @return 分页对象记录列表
     */
    List<T> getRecords();

    /**
     * 获取每页最多显示的记录条数
     *
     * @return 每页最多显示的记录条数
     */
    int getPageSize();

    /**
     * 获取当前页码
     *
     * @return
     */
    int getPageIndex();

    /**
     * 获取返回页数
     *
     * @return 返回页数
     */
    default int getPageCount() {
        if (this.getRecordCount() == 0) {
            return 0;
        }
        if (this.getPageSize() == 0) {
            return 0;
        }
        return (int) Math.ceil((double) this.getRecordCount() / this.getPageSize());
    }

    /**
     * 获取当前满足条件总记录条数
     *
     * @return 总记录条数
     */
    long getRecordCount();


    /**
     * 构造新得实体
     *
     * @param collect 集合
     * @param <R>     实体对象
     * @return 分页实体
     */
    <R extends Serializable> Page<R> build(List<R> collect);

    /**
     * Page 的泛型转换
     *
     * @param mapper 转换函数
     * @param <R>    转换后的泛型
     * @return 转换泛型后的 Page
     */
    default <R extends Serializable> Page<R> convert(Function<? super T, ? extends R> mapper) {
        List<R> collect = this.getRecords().stream().map(mapper).collect(toList());
        if (collect == null) {
            throw new NullPointerException();
        }
        return this.build(collect);
    }
}
