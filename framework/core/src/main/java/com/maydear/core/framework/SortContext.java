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

import lombok.*;

import java.io.Serializable;

/**
 * 排序上下文
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SortContext implements Serializable {

    private static final long serialVersionUID = 4271192658805540337L;

    /**
     * 降序【false：升序，true：降序】
     */
    @Builder.Default
    private boolean descending = true;

    /**
     * 属性名
     */
    private String propertyName;
}
