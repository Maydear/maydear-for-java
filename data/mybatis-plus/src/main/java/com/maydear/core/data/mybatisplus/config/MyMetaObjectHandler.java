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
package com.maydear.core.data.mybatisplus.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 拦截处理元数据
 *
 * @author kelvin.liang
 */
@Slf4j
@Configuration
public class MyMetaObjectHandler implements MetaObjectHandler {
    private static final String UID_FIELD_NAME = "uid";
    private static final String CREATE_TIME_FIELD_NAME = "createTime";
    private static final String MODIFY_TIME_FIELD_NAME = "modifyTime";

    @Override
    public void insertFill(MetaObject metaObject) {
        LocalDateTime now = LocalDateTime.now();

        //填充UID字段
        if (metaObject.hasGetter(UID_FIELD_NAME)) {
            fieldFill(metaObject, UID_FIELD_NAME, UUID.randomUUID().toString());
        }
        //填充创建时间(createTime)字段
        if (metaObject.hasGetter(CREATE_TIME_FIELD_NAME)) {
            fieldFill(metaObject, CREATE_TIME_FIELD_NAME, now);
        }
        //填充更新时间(modifyTime)字段
        if (metaObject.hasGetter(MODIFY_TIME_FIELD_NAME)) {
            fieldFill(metaObject, MODIFY_TIME_FIELD_NAME, now);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {

        //填充更新时间(modifyTime)字段
        if (metaObject.hasGetter(MODIFY_TIME_FIELD_NAME)) {
            this.setFieldValByName(MODIFY_TIME_FIELD_NAME, LocalDateTime.now(), metaObject);
        }
    }

    /**
     * 当对象内指定当字段没有值时，将值填充到指定对象内到指定字段
     *
     * @param metaObject 对象
     * @param fieldName  字段
     * @param value      值
     */
    private void fieldFill(MetaObject metaObject, String fieldName, Object value) {
        if (ObjectUtils.isEmpty(this.getFieldValByName(fieldName, metaObject))) {
            this.setFieldValByName(fieldName, value, metaObject);
        }
    }
}
