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

import com.google.common.collect.Lists;
import com.maydear.core.framework.util.LambdaUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * 分页上下文
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
@Data
@AllArgsConstructor
public class PageContext implements Serializable {

    private static final long serialVersionUID = 2799657600039324655L;

    /**
     * 构造一个分页查询类
     */
    public PageContext()
    {
        pageSize = DEFAULT_PAGE_SIZE;
        pageIndex = DEFAULT_PAGE_INDEX;
        sorts = Lists.newLinkedList();
    }

    /**
     * 默认当前页
     */
    public static final int DEFAULT_PAGE_INDEX = 1;

    /**
     * 默认分页页宽
     */
    public static final int DEFAULT_PAGE_SIZE = 50;

    /**
     * 页宽
     */
    private int pageSize;

    /**
     * 当前页
     */
    private int pageIndex;

    /**
     * 偏移量
     * @return 返回跳过行数
     */
    public int getOffset() {
        return (pageIndex - 1) * pageSize;
    }

    /**
     * 获取记录数量
     * @return 返回获取记录数量
     */
    public int getLimit(){
        return  pageSize;
    }

    /**
     * 排序
     */
    @Getter
    public List<SortContext> sorts;

    /**
     * 排序
     *
     * @param isDescending 是否降序
     * @param action 获取排序属性的lambda表达式
     */
    protected <R> void sort(boolean isDescending, SerializableFunction<R, ?> action) {
        String propertyName = LambdaUtils.getPropertyName(action);
        if (StringUtils.isNotBlank(propertyName)) {
            sorts.add(SortContext.builder().descending(isDescending).propertyName(propertyName).build());
        }
    }
}
