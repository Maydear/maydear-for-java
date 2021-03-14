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
package com.maydear.core.framework.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 操作系统工具类
 *
 * @author phil
 * @version 1.0.0
 */
public class OsUtils {

    /**
     * 静态工具类不应该被实例化
     */
    private OsUtils() {
        throw new IllegalStateException("Utility class");
    }

    private static final String OS = System.getProperty("os.name").toLowerCase();

    /**
     * 是否Linux操作系统
     * @return 如果为Linux系统则返回true，反之则为false；
     */
    public static boolean isLinux(){
        return StringUtils.containsIgnoreCase(OS, "linux");
    }

    /**
     * 是否MacOS操作系统
     * @return 如果为MacOS系统则返回true，反之则为false；
     */
    public static boolean isMacOs(){
        return StringUtils.containsIgnoreCase(OS, "mac");
    }

    /**
     * 是否windows操作系统
     * @return 如果为windows系统则返回true，反之则为false；
     */
    public static boolean isWindows(){
        return StringUtils.containsIgnoreCase(OS, "windows");
    }

    /**
     * 是否OS/2操作系统
     * @return 如果为OS/2系统则返回true，反之则为false；
     */
    public static boolean isOs2(){
        return StringUtils.containsIgnoreCase(OS, "os/2");
    }
    /**
     * 是否solaris操作系统
     * @return 如果为solaris系统则返回true，反之则为false；
     */
    public static boolean isSolaris(){
        return StringUtils.containsIgnoreCase(OS, "solaris");
    }

    /**
     * 是否sunos操作系统
     * @return 如果为sunos系统则返回true，反之则为false；
     */
    public static boolean isSunOs(){
        return StringUtils.containsIgnoreCase(OS, "sunos");
    }

    /**
     * 是否mpe/ix操作系统
     * @return 如果为mpe/ix系统则返回true，反之则为false；
     */
    public static boolean isMpeiX(){
        return StringUtils.containsIgnoreCase(OS, "mpe/ix");
    }

    /**
     * 是否hp-ux操作系统
     * @return 如果为hp-ux系统则返回true，反之则为false；
     */
    public static boolean isHpux(){
        return StringUtils.containsIgnoreCase(OS, "hp-ux");
    }

    /**
     * 是否aix操作系统
     * @return 如果为aix系统则返回true，反之则为false；
     */
    public static boolean isAix(){
        return StringUtils.containsIgnoreCase(OS, "aix");
    }

    /**
     * 是否os/390操作系统
     * @return 如果为os/390系统则返回true，反之则为false；
     */
    public static boolean isOs390(){
        return StringUtils.containsIgnoreCase(OS, "os/390");
    }

    /**
     * 是否freebsd操作系统
     * @return 如果为freebsd系统则返回true，反之则为false；
     */
    public static boolean isFreeBsd(){
        return StringUtils.containsIgnoreCase(OS, "freebsd");
    }

    /**
     * 是否irix操作系统
     * @return 如果为irix系统则返回true，反之则为false；
     */
    public static boolean isIrix(){
        return StringUtils.containsIgnoreCase(OS, "irix");
    }

    /**
     * 是否digital操作系统
     * @return 如果为digital系统则返回true，反之则为false；
     */
    public static boolean isDigitalUnix(){
        return StringUtils.containsIgnoreCase(OS, "digital");
    }

    /**
     * 是否netware操作系统
     * @return 如果为netware系统则返回true，反之则为false；
     */
    public static boolean isNetWare(){
        return StringUtils.containsIgnoreCase(OS, "netware");
    }
    /**
     * 是否osf1操作系统
     * @return 如果为osf1系统则返回true，反之则为false；
     */
    public static boolean isOsf1(){
        return StringUtils.containsIgnoreCase(OS, "osf1");
    }

    /**
     * 是否openvms操作系统
     * @return 如果为openvms系统则返回true，反之则为false；
     */
    public static boolean isOpenVms(){
        return StringUtils.containsIgnoreCase(OS, "openvms");
    }
}
