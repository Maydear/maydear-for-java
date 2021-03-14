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
package com.maydear.core.framework.jackson.module;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.PackageVersion;
import com.maydear.core.framework.jackson.serializer.ObjectToStringSerializer;

/**
 * 数值序列化模型
 *
 * @author phil
 * @version 1.0.0
 */
public class CharSequenceModule extends SimpleModule {
    public CharSequenceModule() {
        super(PackageVersion.VERSION);
        addSerializers();
    }

    /**
     * 添加序列化解析器
     */
    private void addSerializers() {
        addSerializer(Long.class, new ObjectToStringSerializer());
    }
}
