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
package com.maydear.core.framework.mapstruct;

import com.maydear.core.framework.ModelConverter;
import org.mapstruct.MappingTarget;

import java.util.Collection;

/**
 * Mapstruct 实体转换
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
public interface MapstructConverter<S, T> extends ModelConverter<S, T> {

    /**
     * 源对象修改目标对象
     *
     * @param source 源对象
     * @param target
     */
    void modifyTarget(S source, @MappingTarget T target);

    /**
     * 根据目标对象修改源对象
     *
     * @param target 目标对象
     * @param source 源对象
     */
    void modifySource(T target, @MappingTarget S source);

    /**
     * 将源对象集合从新填充目标对象集合
     *
     * @param sources 源对象集合
     * @param targets 目标对象集合
     */
    void modifyTarget(Collection<S> sources, @MappingTarget Collection<T> targets);

    /**
     *  将目标对象集合从新填充源对象集合
     *
     * @param targets 目标对象集合
     * @param sources 源对象集合
     */
    void modifySource(Collection<T> targets, @MappingTarget Collection<S> sources);
}
