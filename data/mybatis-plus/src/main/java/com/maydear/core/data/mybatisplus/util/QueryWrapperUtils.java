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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.maydear.core.framework.SortContext;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * 查询包装工具类
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
public class QueryWrapperUtils {

    /**
     * 静态工具类不应该被实例化
     */
    private QueryWrapperUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 获取列名
     * @param columnMapper 列映射
     * @param propertyName 属性名称
     * @return 返回列名称
     */
    private static String getColumnName(final Map<String, String> columnMapper, String propertyName) {
        return columnMapper.get(StringUtils.upperCase(propertyName));
    }

    /**
     * 是否存在
     *
     * @param propertyName 列名
     * @return 存在属性则返回true，否则false
     */
    private static boolean hasColumn(final Map<String, String> columnMapper, String propertyName) {
        return columnMapper.containsKey(StringUtils.upperCase(propertyName));
    }

    /**
     * 添加排序
     *
     * @param queryWrapper 查询分页
     * @param sort         排序对象
     */
    public static void addSort(final Map<String, String> columnMapper, QueryWrapper<?> queryWrapper, SortContext sort) {
        if (ObjectUtils.isEmpty(sort)) {
            return;
        }
        if (hasColumn(columnMapper, sort.getPropertyName())) {
            if (sort.isDescending()) {
                queryWrapper.orderByDesc(getColumnName(columnMapper, sort.getPropertyName()));
            } else {
                queryWrapper.orderByAsc(getColumnName(columnMapper, sort.getPropertyName()));
            }
        }
    }
}
