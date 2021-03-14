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

/**
 * 电子邮件基础设施
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
public interface EmailInfrastructure {

    /**
     * 发送邮件
     * @return 成功执行发送邮件的相关操作则返回true，反之则为false
     */
    Boolean send();
}
