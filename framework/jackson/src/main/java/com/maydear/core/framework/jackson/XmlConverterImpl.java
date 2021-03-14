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
package com.maydear.core.framework.jackson;

import com.maydear.core.framework.XmlConverter;
import com.maydear.core.framework.jackson.mapper.XmlMapper;

import java.io.InputStream;

/**
 * xml 转换
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
public class XmlConverterImpl implements XmlConverter {
    @Override
    public String toXml(Object object) {
        return XmlMapper.INSTANCE.toXml(object);
    }

    @Override
    public <T> T fromXml(String content, Class<T> clazz) {
        return XmlMapper.INSTANCE.fromXml(content,clazz);
    }

    @Override
    public <T> T fromXml(InputStream stream, Class<T> clazz) {
        return XmlMapper.INSTANCE.fromXml(stream,clazz);
    }
}
