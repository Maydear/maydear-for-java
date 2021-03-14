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
package com.maydear.core.springboot.web.controller;

import com.maydear.core.authorization.annotation.AllowAnonymous;
import com.maydear.core.framework.annotation.NotUsePackageObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 网络连接测试
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
@Slf4j
@RestController
@RequestMapping(path = "/ping")
@AllowAnonymous
public class PingController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @NotUsePackageObject
    public String ok() {
        return "OK";
    }
}
