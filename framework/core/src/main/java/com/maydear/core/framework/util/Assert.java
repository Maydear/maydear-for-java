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

import com.maydear.core.framework.exception.BadRequestException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;

/**
 * 验证参数助手类
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
public class Assert {

    /**
     * 防止静态对象被new
     */
    private Assert() {
        throw new IllegalStateException("Assert");
    }

    /**
     * 声明一个布尔表达式，如果该表达式的求值为{@code false}，则抛出一个{@code BadRequestException}。
     * <pre class="code">Assert.isTrue(i &gt; 0, "该值必须大于零");</pre>
     *
     * @param expression 布尔表达式
     * @param message    断言失败时使用的异常消息
     * @throws BadRequestException 如果 {@code expression} 为 {@code false} 将抛出{@code BadRequestException} 异常。
     */
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new BadRequestException(message);
        }
    }

    /**
     * 声明一个布尔表达式，如果该表达式的求值为{@code false}，则抛出一个{@code BadRequestException}。
     * <pre class="code">
     * Assert.isTrue(i &gt; 0, () -&gt; "The value '" + i + "' must be greater than zero");
     * </pre>
     *
     * @param expression      布尔表达式
     * @param messageSupplier 断言失败时使用的异常消息的提供者
     * @throws BadRequestException 如果 {@code expression} 为 {@code false} 将抛出{@code BadRequestException} 异常。
     */
    public static void isTrue(boolean expression, Supplier<String> messageSupplier) {
        if (!expression) {
            throw new BadRequestException(nullSafeGet(messageSupplier));
        }
    }

    /**
     * 断言一个对象为{@code null}。
     * <pre class="code">Assert.isNull(value, "该值必须为空");</pre>
     *
     * @param object  待检查的对象
     * @param message 断言失败时使用的异常消息
     * @throws BadRequestException 如果对象不是{@code null}，则抛出一个{@code BadRequestException} 异常。
     */
    public static void isNull(@Nullable Object object, String message) {
        if (object != null) {
            throw new BadRequestException(message);
        }
    }

    /**
     * 断言一个对象为{@code null}。
     * <pre class="code">
     * Assert.isNull(value, () -&gt; "该值 '" + value + "' 必须为空");
     * </pre>
     *
     * @param object          待检查的对象
     * @param messageSupplier 断言失败时使用的异常消息的提供者
     * @throws BadRequestException 如果对象不是{@code null}，则抛出一个{@code BadRequestException} 异常。
     */
    public static void isNull(@Nullable Object object, Supplier<String> messageSupplier) {
        if (object != null) {
            throw new BadRequestException(nullSafeGet(messageSupplier));
        }
    }

    /**
     * 断言对象不是{@code null}。
     * <pre class="code">Assert.isNotNull(clazz, "该类不能为null");</pre>
     *
     * @param object  待检查的对象
     * @param message 断言失败时使用的异常消息
     * @throws BadRequestException 如果对象是{@code null}，则抛出一个{@code BadRequestException} 异常。
     */
    public static void isNotNull(@Nullable Object object, String message) {
        if (object == null) {
            throw new BadRequestException(message);
        }
    }

    /**
     * 断言对象不是{@code null}。
     * <pre class="code">
     * Assert.isNotNull(clazz, () -&gt; "该类 '" + clazz.getName() + "' 不能为null");
     * </pre>
     *
     * @param object          待检查的对象
     * @param messageSupplier 断言失败时使用的异常消息的提供者
     * @throws BadRequestException 如果对象是{@code null}，则抛出一个{@code BadRequestException} 异常。
     * @since 5.0
     */
    public static void isNotNull(@Nullable Object object, Supplier<String> messageSupplier) {
        if (object == null) {
            throw new BadRequestException(nullSafeGet(messageSupplier));
        }
    }

    /**
     * 断言给定的String不为空；也就是说，它不能为{@code null}，也不能为空String。
     * <pre class="code">Assert.isNotEmpty(name, "Name 不能为空");</pre>
     *
     * @param text    待检查的字符
     * @param message 断言失败时使用的异常消息
     * @throws BadRequestException 如果字符是{@code null}或为空串，则抛出一个{@code BadRequestException} 异常。
     * @see StringUtils#isNotEmpty
     */
    public static void isNotEmpty(@Nullable String text, String message) {
        if (!StringUtils.isNotEmpty(text)) {
            throw new BadRequestException(message);
        }
    }

    /**
     * 断言给定的String不为空；也就是说，它不能为{@code null}，也不能为空String。
     * <pre class="code">
     * Assert.isNotEmpty(name, () -&gt; "帐户名称'" + account.getId() + "' 不能为空");
     * </pre>
     *
     * @param text            待检查的字符
     * @param messageSupplier 断言失败时使用的异常消息的提供者
     * @throws BadRequestException 如果字符是{@code null}或为空串，则抛出一个{@code BadRequestException} 异常。
     * @see StringUtils#isNotEmpty
     */
    public static void isNotEmpty(@Nullable String text, Supplier<String> messageSupplier) {
        if (!StringUtils.isNotEmpty(text)) {
            throw new BadRequestException(nullSafeGet(messageSupplier));
        }
    }

    /**
     * 断言给定的字符串包含有效的文本内容；也就是说，它不能为{@code null}，并且必须至少包含一个非空白字符。
     * <pre class="code">Assert.isNotBlank(name, "'name' 不能为空");</pre>
     *
     * @param text    待检查的字符
     * @param message 断言失败时使用的异常消息
     * @throws BadRequestException 如果文本不包含有效的文本内容，则抛出一个{@code BadRequestException} 异常。
     * @see StringUtils#isNotBlank
     */
    public static void isNotBlank(@Nullable String text, String message) {
        if (!StringUtils.isNotBlank(text)) {
            throw new BadRequestException(message);
        }
    }

    /**
     * 断言给定的字符串包含有效的文本内容；也就是说，它不能为{@code null}，并且必须至少包含一个非空白字符。
     * <pre class="code">
     * Assert.isNotBlank(name, () -&gt; "帐户名称 '" + account.getId() + "' 不能为空");
     * </pre>
     *
     * @param text            待检查的字符
     * @param messageSupplier 断言失败时使用的异常消息的提供者
     * @throws BadRequestException 如果文本不包含有效的文本内容，则抛出一个{@code BadRequestException} 异常。
     * @see StringUtils#isNotBlank
     */
    public static void isNotBlank(@Nullable String text, Supplier<String> messageSupplier) {
        if (!StringUtils.isNotBlank(text)) {
            throw new BadRequestException(nullSafeGet(messageSupplier));
        }
    }

    /**
     * 断言给定的文本不包含给定的子字符串。
     * <pre class="code">Assert.doesNotContain(name, "rod", "名称不得包含 'rod'");</pre>
     *
     * @param textToSearch 要搜索的文字
     * @param substring    在文本中找到的子字符串
     * @param message      断言失败时使用的异常消息
     * @throws BadRequestException 如果文本包含子字符串，则抛出一个{@code BadRequestException} 异常。
     */
    public static void doesNotContain(@Nullable String textToSearch, String substring, String message) {
        if (StringUtils.isNotBlank(textToSearch) && StringUtils.isNotBlank(substring) &&
                textToSearch.contains(substring)) {
            throw new BadRequestException(message);
        }
    }

    /**
     * 断言给定的文本不包含给定的子字符串。
     * <pre class="code">
     * Assert.doesNotContain(name, forbidden, () -&gt; "名称不得包含 '" + forbidden + "'");
     * </pre>
     *
     * @param textToSearch    要搜索的文字
     * @param substring       在文本中找到的子字符串
     * @param messageSupplier 断言失败时使用的异常消息的提供者
     * @throws BadRequestException 如果文本包含子字符串，则抛出一个{@code BadRequestException} 异常。
     */
    public static void doesNotContain(@Nullable String textToSearch, String substring, Supplier<String> messageSupplier) {
        if (StringUtils.isNotBlank(textToSearch) && StringUtils.isNotBlank(substring) &&
                textToSearch.contains(substring)) {
            throw new BadRequestException(nullSafeGet(messageSupplier));
        }
    }

    /**
     * 断言一个数组包含至少1个元素；也就是说，它不能为{@code null}，并且必须至少包含一个元素。
     * <pre class="code">Assert.isNotEmpty(array, "数组必须包含元素");</pre>
     *
     * @param array   待检查的数组
     * @param message 断言失败时使用的异常消息
     * @throws BadRequestException 如果对象数组为{@code null}或不包含任何元素，则抛出一个{@code BadRequestException} 异常。
     */
    public static void isNotEmpty(@Nullable Object[] array, String message) {
        if (ObjectUtils.isEmpty(array)) {
            throw new BadRequestException(message);
        }
    }

    /**
     * 断言一个数组包含元素；也就是说，它不能为{@code null}，并且必须至少包含一个元素。
     * <pre class="code">
     * Assert.isNotEmpty(array, () -&gt; arrayType + " 数组必须包含元素");
     * </pre>
     *
     * @param array           待检查的数组
     * @param messageSupplier 断言失败时使用的异常消息的提供者
     * @throws BadRequestException 如果对象数组为{@code null}或不包含任何元素，则抛出一个{@code BadRequestException} 异常。
     */
    public static void isNotEmpty(@Nullable Object[] array, Supplier<String> messageSupplier) {
        if (ObjectUtils.isEmpty(array)) {
            throw new BadRequestException(nullSafeGet(messageSupplier));
        }
    }

    /**
     * 断言一个数组不包含任何{@code null}元素。
     * <p>注意：如果数组为空，则不会检查!
     * <pre class="code">Assert.noNullElements(array, "数组必须包含非null元素");</pre>
     *
     * @param array   待检查的数组
     * @param message 断言失败时使用的异常消息
     * @throws BadRequestException 如果对象数组包含{@code null}元素，则抛出一个{@code BadRequestException} 异常。
     */
    public static void noNullElements(@Nullable Object[] array, String message) {
        if (array != null) {
            for (Object element : array) {
                if (element == null) {
                    throw new BadRequestException(message);
                }
            }
        }
    }

    /**
     * 断言一个数组不包含任何{@code null}元素。
     * <p>注意：如果数组为空，则不会检查!
     * <pre class="code">
     * Assert.noNullElements(array, () -&gt; arrayType + " 数组必须包含非null元素");
     * </pre>
     *
     * @param array           待检查的数组
     * @param messageSupplier 断言失败时使用的异常消息的提供者
     * @throws BadRequestException 如果对象数组包含{@code null}元素，则抛出一个{@code BadRequestException} 异常。
     */
    public static void noNullElements(@Nullable Object[] array, Supplier<String> messageSupplier) {
        if (array != null) {
            for (Object element : array) {
                if (element == null) {
                    throw new BadRequestException(nullSafeGet(messageSupplier));
                }
            }
        }
    }

    /**
     * 断言一个集合包含元素；也就是说，它不能为{@code null}，并且必须至少包含一个元素。
     * <pre class="code">Assert.isNotEmpty(collection, "集合必须包含元素");</pre>
     *
     * @param collection 待检查的集合
     * @param message    断言失败时使用的异常消息
     * @throws BadRequestException 如果集合为{@code null}或不包含任何元素，则抛出一个{@code BadRequestException} 异常。
     */
    public static void isNotEmpty(@Nullable Collection<?> collection, String message) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new BadRequestException(message);
        }
    }

    /**
     * 断言一个集合包含元素；也就是说，它不能为{@code null}，并且必须至少包含一个元素。
     * <pre class="code">
     * Assert.isNotEmpty(collection, () -&gt; "The " + collectionType + " collection must contain elements");
     * </pre>
     *
     * @param collection      待检查的集合
     * @param messageSupplier 断言失败时使用的异常消息的提供者
     * @throws BadRequestException 如果集合为{@code null}或不包含任何元素，则抛出一个{@code BadRequestException} 异常。
     */
    public static void isNotEmpty(@Nullable Collection<?> collection, Supplier<String> messageSupplier) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new BadRequestException(nullSafeGet(messageSupplier));
        }
    }

    /**
     * 断言键值映射包含条目；也就是说，它不能为{@code null}，并且必须至少包含一个条目。
     * <pre class="code">Assert.isNotEmpty(map, "键值映射必须包含条目");</pre>
     *
     * @param map     待检查的键值映射
     * @param message 断言失败时使用的异常消息
     * @throws BadRequestException 如果键值映射为{@code null}或不包含任何条目，则抛出一个{@code BadRequestException} 异常。
     */
    public static void isNotEmpty(@Nullable Map<?, ?> map, String message) {
        if (MapUtils.isEmpty(map)) {
            throw new BadRequestException(message);
        }
    }

    /**
     * 断言键值映射包含条目；也就是说，它不能为{@code null}，并且必须至少包含一个条目。
     * <pre class="code">
     * Assert.isNotEmpty(map, () -&gt; mapType + " 地图必须包含条目");
     * </pre>
     *
     * @param map             待检查的键值映射
     * @param messageSupplier 断言失败时使用的异常消息的提供者
     * @throws BadRequestException 如果键值映射为{@code null}或不包含任何条目，则抛出一个{@code BadRequestException} 异常。
     * @since 5.0
     */
    public static void isNotEmpty(@Nullable Map<?, ?> map, Supplier<String> messageSupplier) {
        if (MapUtils.isEmpty(map)) {
            throw new BadRequestException(nullSafeGet(messageSupplier));
        }
    }

    /**
     * 断言所提供的对象是所提供类的实例。
     * <pre class="code">Assert.instanceOf(Foo.class, foo, "Foo expected");</pre>
     *
     * @param type    期待的类型
     * @param obj     待检查的实例
     * @param message 断言失败时使用的异常消息
     * @throws BadRequestException 如果该对象不是所提供类的实例，则抛出一个{@code BadRequestException} 异常。
     */
    public static void isInstanceOf(Class<?> type, @Nullable Object obj, String message) {
        isNotNull(type, "期待的类型不能为空");
        if (!type.isInstance(obj)) {
            instanceCheckFailed(type, obj, message);
        }
    }

    /**
     * 断言所提供的对象是所提供类的实例。
     * <pre class="code">
     * Assert.instanceOf(Foo.class, foo, () -&gt; "Processing " + Foo.class.getSimpleName() + ":");
     * </pre>
     *
     * @param type            期待的类型
     * @param obj             待检查的实例
     * @param messageSupplier 断言失败时使用的异常消息的提供者
     * @throws BadRequestException 如果该对象不是所提供类的实例，则抛出一个{@code BadRequestException} 异常。
     * @since 5.0
     */
    public static void isInstanceOf(Class<?> type, @Nullable Object obj, Supplier<String> messageSupplier) {
        isNotNull(type, "期待的类型不能为空");
        if (!type.isInstance(obj)) {
            instanceCheckFailed(type, obj, nullSafeGet(messageSupplier));
        }
    }

    /**
     * 断言所提供的对象是所提供类的实例。
     * <pre class="code">Assert.instanceOf(Foo.class, foo);</pre>
     *
     * @param type 期待的类型
     * @param obj  待检查的实例
     * @throws BadRequestException 如果该对象不是所提供类的实例，则抛出一个{@code BadRequestException} 异常。
     */
    public static void isInstanceOf(Class<?> type, @Nullable Object obj) {
        isInstanceOf(type, obj, "");
    }

    /**
     * 断言{@code superType.isAssignableFrom（subType）}为{@code true}。
     * <pre class="code">Assert.isAssignable(Number.class, myClass, "Number expected");</pre>
     *
     * @param superType 父类
     * @param subType   子类
     * @param message   断言失败时使用的异常消息
     * @throws BadRequestException 如果子类不是所提供类的子集，则抛出一个{@code BadRequestException} 异常。
     */
    public static void isAssignable(Class<?> superType, @Nullable Class<?> subType, String message) {
        isNotNull(superType, "父类不能为null");
        if (subType == null || !superType.isAssignableFrom(subType)) {
            assignableCheckFailed(superType, subType, message);
        }
    }

    /**
     * 断言{@code superType.isAssignableFrom（subType）}为{@code true}。
     * <pre class="code">
     * Assert.isAssignable(Number.class, myClass, () -&gt; "Processing " + myAttributeName + ":");
     * </pre>
     *
     * @param superType       父类
     * @param subType         子类
     * @param messageSupplier 断言失败时使用的异常消息的提供者
     * @throws BadRequestException 如果子类不是所提供类的子集，则抛出一个{@code BadRequestException} 异常。
     */
    public static void isAssignable(Class<?> superType, @Nullable Class<?> subType, Supplier<String> messageSupplier) {
        isNotNull(superType, "父类不能为null");
        if (subType == null || !superType.isAssignableFrom(subType)) {
            assignableCheckFailed(superType, subType, nullSafeGet(messageSupplier));
        }
    }

    /**
     * 断言{@code superType.isAssignableFrom（subType）}为{@code true}。
     * <pre class="code">Assert.isAssignable(Number.class, myClass);</pre>
     *
     * @param superType 父类
     * @param subType   子类
     * @throws BadRequestException 如果子类不是所提供类的子集，则抛出一个{@code BadRequestException} 异常。
     */
    public static void isAssignable(Class<?> superType, Class<?> subType) {
        isAssignable(superType, subType, "");
    }

    /**
     * 检查类型对是否存在参数
     *
     * @param type
     * @param obj
     * @param msg
     */
    private static void instanceCheckFailed(Class<?> type, @Nullable Object obj, @Nullable String msg) {
        String className = (obj != null ? obj.getClass().getName() : "null");
        String result = messageWithTypeName(msg, className);
        result = result + ("对象 [" + className + "] 必须是 " + type + "的实例");
        throw new BadRequestException(result);
    }

    /**
     * @param superType
     * @param subType
     * @param msg
     */
    private static void assignableCheckFailed(Class<?> superType, @Nullable Class<?> subType, @Nullable String msg) {
        String result = messageWithTypeName(msg, subType);
        result = result + (subType + " 不是 " + superType + "的子类");
        throw new BadRequestException(result);
    }

    /**
     * @param msg
     * @return
     */
    private static boolean endsWithSeparator(String msg) {
        return (msg.endsWith(":") || msg.endsWith(";") || msg.endsWith(",") || msg.endsWith("."));
    }

    private static String messageWithTypeName(String msg, @Nullable Object typeName) {
        String result = "";
        if (StringUtils.isNotBlank(msg)) {
            if (endsWithSeparator(msg)) {
                result = msg + " ";
            } else {
                result = msg + (msg.endsWith(" ") ? "" : ": ") + typeName;
            }
        }
        return result;
    }

    @Nullable
    private static String nullSafeGet(@Nullable Supplier<String> messageSupplier) {
        return (messageSupplier != null ? messageSupplier.get() : null);
    }
}
