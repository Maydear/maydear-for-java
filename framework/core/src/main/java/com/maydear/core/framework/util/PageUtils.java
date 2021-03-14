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

import com.maydear.core.framework.ModelConverter;
import com.maydear.core.framework.Page;
import com.maydear.core.framework.PageList;

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
public class PageUtils {

    /**
     * 静态工具类不应该被实例化
     */
    private PageUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 映射成新分页
     *
     * @param sourcePage 源分页对象
     * @param mapper     Lambda映射
     * @param <T>        目标数据
     * @param <S>        来源数据
     * @return 转换后的分页信息
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable, S extends Serializable> Page<T> getPage(Page<S> sourcePage, Function<? super S, ? extends T> mapper) {
        return new PageList(sourcePage.getPageSize(), sourcePage.getPageIndex(), sourcePage.getRecordCount(), sourcePage.getRecords().stream().map(mapper).collect(Collectors.toList()));
    }

    /**
     * 将entity分页通过ModelConverter.to转成 dto分页
     *
     * @param sourcePage ENTITY 分页信息
     * @param converter  ModelConverter转换对象
     * @param <T>        DTO
     * @param <S>        ENTITY
     * @return DTO 分页信息
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable, S extends Serializable> Page<T> getPageByConverterTo(Page<S> sourcePage, ModelConverter<S, T> converter) {
        return new PageList(sourcePage.getPageSize(), sourcePage.getPageIndex(), sourcePage.getRecordCount(), (List<T>) converter.to(sourcePage.getRecords()));
    }

    /**
     * 将entity分页通过ModelConverter.from转成 dto分页
     *
     * @param sourcePage ENTITY 分页信息
     * @param converter  ModelConverter转换对象
     * @param <T>        DTO
     * @param <S>        ENTITY
     * @return DTO 分页信息
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable, S extends Serializable> Page<T> getPageByConverterFrom(Page<S> sourcePage, ModelConverter<T, S> converter) {
        return new PageList(sourcePage.getPageSize(), sourcePage.getPageIndex(), sourcePage.getRecordCount(), (List<T>) converter.from(sourcePage.getRecords()));
    }
}
