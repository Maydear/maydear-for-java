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
package com.maydear.core.framework.infrastructure;

import lombok.*;

import java.util.Map;

/**
 * 语音信息内容
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class VoiceMessageContent {

    /**
     * 接收电话号码（支持手机或固话）
     */
    private String phoneNumber;

    /**
     * 内容
     */
    private String content;

    /**
     * 参数
     */
    private Map<String,Object> parameters;

    /**
     * 模板编码，可空
     */
    private String templateCode;

    /**
     * 播放次数
     */
    private Integer playTimes;

    /**
     * 扩展参数
     */
    private Map<String,Object> extended;
}
