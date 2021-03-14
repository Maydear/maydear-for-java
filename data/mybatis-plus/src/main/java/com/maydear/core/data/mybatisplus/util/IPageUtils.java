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
package com.maydear.core.data.mybatisplus.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maydear.core.framework.ModelConverter;
import com.maydear.core.framework.PageContext;
import com.maydear.core.framework.PageList;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 分页工具类
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
public class IPageUtils {

    /**
     * 静态工具类不应该被实例化
     */
    private IPageUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 获取分页
     *
     * @param context 分页查询实体
     * @param <T>     分页实体
     * @return 分页信息
     */
    @SuppressWarnings("unchecked")
    public static <T> IPage<T> getIPage(PageContext context) {
        Page<T> page = new Page();
        page.setCurrent(context.getPageIndex());
        page.setSize(context.getPageSize());
        return page;
    }

    /**
     * 获取分页
     *
     * @param current 当前页
     * @param <T>     分页实体
     * @return 分页信息
     */
    @SuppressWarnings("unchecked")
    public static <T> IPage<T> getIPage(int current) {
        Page<T> page = new Page();
        page.setCurrent(current);
        return page;
    }

    /**
     * 获取分页
     *
     * @param current 分页实体
     * @param <T>     分页实体
     * @return 分页信息
     */
    @SuppressWarnings("unchecked")
    public static <T> IPage<T> getIPage(int current, int size) {
        Page<T> page = new Page<>();
        page.setCurrent(current);
        page.setSize(size);
        return page;
    }

    /**
     * 获取分页
     *
     * @param context    当前页
     * @param orderItems 排序字段
     * @param <T>        分页实体
     * @return 分页信息
     */
    @SuppressWarnings("unchecked")
    public static <T> IPage<T> getIPage(PageContext context, List<OrderItem> orderItems) {
        Page<T> page = new Page();
        page.setCurrent(context.getPageIndex());
        page.setSize(context.getPageSize());
        page.setOrders(orderItems);
        return page;
    }


    /**
     * 获取分页
     *
     * @param current    当前页
     * @param size       分页记录数
     * @param orderItems 排序字段
     * @param <T>        分页实体
     * @return 分页信息
     */
    @SuppressWarnings("unchecked")
    public static <T> IPage<T> getIPage(int current, int size, List<OrderItem> orderItems) {
        Page<T> page = new Page();
        page.setCurrent(current);
        page.setSize(size);
        page.setOrders(orderItems);
        return page;
    }

    /**
     * 将entity分页转成 dto分页
     *
     * @param sourcePage ENTITY 分页信息
     * @param records    记录
     * @param <T>        DTO
     * @param <S>        ENTITY
     * @return DTO 分页信息
     */
    @SuppressWarnings("unchecked")
    public static <T, S> IPage<T> getIPage(IPage<S> sourcePage, List<T> records) {
        Page<T> page = new Page(sourcePage.getCurrent(), sourcePage.getSize(), sourcePage.getTotal());
        page.setRecords(records);
        return page;
    }

    /**
     * 将entity分页转成 dto分页
     *
     * @param sourcePage ENTITY 分页信息
     * @param mapper     Lambda映射
     * @param <T>        DTO
     * @param <S>        ENTITY
     * @return DTO 分页信息
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable, S> com.maydear.core.framework.Page<T> getIPage(IPage<S> sourcePage, Function<S, T> mapper) {
        return new PageList((int) sourcePage.getSize(), (int) sourcePage.getCurrent(), sourcePage.getTotal(), sourcePage.getRecords().stream().map(mapper).collect(Collectors.toList()));
    }

    /**
     * 将entity分页通过ModelConverter转成 Model分页
     *
     * @param sourcePage ENTITY 分页信息
     * @param converter  转换对象
     * @param <T>        DTO
     * @param <S>        ENTITY
     * @return DTO 分页信息
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable, S> com.maydear.core.framework.Page<T> getPageConverterTo(IPage<S> sourcePage, ModelConverter<S, T> converter) {
        return new PageList((int) sourcePage.getSize(), (int) sourcePage.getCurrent(), sourcePage.getTotal(), (List<T>) converter.to(sourcePage.getRecords()));
    }

    /**
     * 将entity分页通过ModelConverter转成Model分页
     *
     * @param sourcePage ENTITY 分页信息
     * @param converter  转换对象
     * @param <T>        DTO
     * @param <S>        ENTITY
     * @return DTO 分页信息
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable, S> com.maydear.core.framework.Page<T> getPageByConverterFrom(IPage<S> sourcePage, ModelConverter<T, S> converter) {
        return new PageList((int) sourcePage.getSize(), (int) sourcePage.getCurrent(), sourcePage.getTotal(), (List<T>) converter.from(sourcePage.getRecords()));
    }

}
