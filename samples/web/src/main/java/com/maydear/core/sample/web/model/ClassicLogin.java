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
package com.maydear.core.sample.web.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 经典登录（账户密码）
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ClassicLogin implements Serializable {

    private static final long serialVersionUID = -8127876207127724473L;

    /**
     * 随机id，需要和获取验证码一致
     */
    @NotBlank(message = "[验证码随机号]不能为空")
    private String uid;

    /**
     * 手机号
     */
    @NotBlank(message = "[手机号]不能为空")
    private String mobile;

    /**
     * 密码
     */
    @NotBlank(message = "[密码]不能为空")
    private String password;

    /**
     * 验证码
     */
    @NotBlank(message = "[验证码]不能为空")
    private String captchaCode;

}
