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

import java.util.Collection;

/**
 * 领域模型实体与目标实体转换类
 *
 * @param <S> 源实体
 * @param <T> 目标实体类（如POJO/DTO/VO/DO）
 * @author kelvin.liang
 * @version 1.0.0
 */
public interface ModelConverter<S, T> {

    /**
     * 源实体对象转为目标实体对象
     *
     * @param source 源实体对象
     * @return 返回目标实体类对象
     */
    T to(S source);

    /**
     * 目标实体对象转为源实体对象
     *
     * @param target 目标实体类对象
     * @return 返回领域模型实体对象
     */
    S from(T target);

    /**
     * 源实体对象集转为目标实体对象集
     *
     * @param sources 源实体类集
     * @return 返回目标实体类对象集
     */
    Collection<T> to(Collection<S> sources);

    /**
     * 目标实体对象集转为源实体对象集
     *
     * @param targets 目标实体类对象集
     * @return 返回源实体对象集
     */
    Collection<S> from(Collection<T> targets);
}
