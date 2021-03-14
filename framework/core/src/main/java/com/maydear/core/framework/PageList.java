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

import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 分页列表
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
@AllArgsConstructor
public class PageList<T extends Serializable> implements Page<T> {

    /**
     * 每页记录数
     */
    private int pageSize;

    /**
     * 当前页
     */
    private int pageIndex;

    /**
     * 获取总记录条数
     */
    private long recordCount;

    /**
     * 记录列表
     */
    private List<T> records;

    /**
     * 获取分页记录列表
     *
     * @return 分页对象记录列表
     */
    @Override
    public List<T> getRecords() {
        return this.records;
    }

    /**
     * 获取每页最多显示的记录条数
     *
     * @return 每页最多显示的记录条数
     */
    @Override
    public int getPageSize() {
        return this.pageSize;
    }

    /**
     * 获取页码
     * @return 页码
     */
    @Override
    public int getPageIndex() {
        return this.pageIndex;
    }

    /**
     * 获取记录数
     * @return 记录数
     */
    @Override
    public long getRecordCount() {
        return this.recordCount;
    }

    /**
     * 构造对象
     * @param collect 集合
     * @param <R>
     * @return  分页集合
     */
    @Override
    @SuppressWarnings("unchecked")
    public <R extends Serializable> Page<R> build(List<R> collect) {
        return new PageList(pageSize, pageIndex, recordCount, collect);
    }

    /**
     * 对象转换
     * @param converter
     * @param <R>
     * @return
     */
    @SuppressWarnings("unchecked")
    public <R extends Serializable> Page<R> convert(ModelConverter<R, T> converter) {
        return new PageList(pageSize, pageIndex, recordCount, (List<R>)converter.from(getRecords()));
    }

    /**
     * 对象转换
     * @param mapper
     * @param <R>
     * @return
     */
    @SuppressWarnings("unchecked")
    public <R extends Serializable> Page<R> map(Function<? super T, ? extends R> mapper) {
        return new PageList(pageSize, pageIndex, recordCount, records.stream().map(mapper).collect(Collectors.toList()));
    }


}
