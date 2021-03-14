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
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.PackageVersion;
import com.fasterxml.jackson.datatype.jsr310.deser.*;
import com.fasterxml.jackson.datatype.jsr310.deser.key.*;
import com.fasterxml.jackson.datatype.jsr310.ser.*;
import com.fasterxml.jackson.datatype.jsr310.ser.key.ZonedDateTimeKeySerializer;

import java.time.*;

/**
 * 自定义 Jackson module
 *
 * @author mechanic
 * @version 0.0.1
 */
@SuppressWarnings("javadoc")
public final class DefaultSimpleModule extends SimpleModule {

    private static final long serialVersionUID = -3259580709662579519L;

    /**
     * Constructors that should only be used for non-reusable
     * convenience modules used by app code: "real" modules should
     * use actual name and version number information.
     */
    public DefaultSimpleModule() {
        super(PackageVersion.VERSION);

        // // Instant variants:
        addDeserializer(Instant.class, InstantDeserializer.INSTANT);
        addDeserializer(OffsetDateTime.class, InstantDeserializer.OFFSET_DATE_TIME);
        addDeserializer(ZonedDateTime.class, InstantDeserializer.ZONED_DATE_TIME);

        // // Other deserializers
        addDeserializer(Duration.class, DurationDeserializer.INSTANCE);

        addDeserializer(MonthDay.class, MonthDayDeserializer.INSTANCE);
        addDeserializer(OffsetTime.class, OffsetTimeDeserializer.INSTANCE);
        addDeserializer(Period.class, JSR310StringParsableDeserializer.PERIOD);
        addDeserializer(Year.class, YearDeserializer.INSTANCE);
        addDeserializer(YearMonth.class, YearMonthDeserializer.INSTANCE);
        addDeserializer(ZoneId.class, JSR310StringParsableDeserializer.ZONE_ID);
        addDeserializer(ZoneOffset.class, JSR310StringParsableDeserializer.ZONE_OFFSET);

        // then serializers:
        addSerializer(Duration.class, DurationSerializer.INSTANCE);
        addSerializer(Instant.class, InstantSerializer.INSTANCE);

        addSerializer(MonthDay.class, MonthDaySerializer.INSTANCE);
        addSerializer(OffsetDateTime.class, OffsetDateTimeSerializer.INSTANCE);
        addSerializer(OffsetTime.class, OffsetTimeSerializer.INSTANCE);
        addSerializer(Period.class, new ToStringSerializer(Period.class));
        addSerializer(Year.class, YearSerializer.INSTANCE);
        addSerializer(YearMonth.class, YearMonthSerializer.INSTANCE);

        /* 27-Jun-2015, tatu: This is the real difference from the old
         *  {@link JSR310Module}: default is to produce ISO-8601 compatible
         *  serialization with timezone offset only, not timezone id.
         *  But this is configurable.
         */
        addSerializer(ZonedDateTime.class, ZonedDateTimeSerializer.INSTANCE);

        // note: actual concrete type is `ZoneRegion`, but that's not visible:
        addSerializer(ZoneId.class, new ToStringSerializer(ZoneId.class));

        addSerializer(ZoneOffset.class, new ToStringSerializer(ZoneOffset.class));

        // key serializers
        addKeySerializer(ZonedDateTime.class, ZonedDateTimeKeySerializer.INSTANCE);

        // key deserializers
        addKeyDeserializer(Duration.class, DurationKeyDeserializer.INSTANCE);
        addKeyDeserializer(Instant.class, InstantKeyDeserializer.INSTANCE);
        addKeyDeserializer(LocalDateTime.class, LocalDateTimeKeyDeserializer.INSTANCE);
        addKeyDeserializer(LocalDate.class, LocalDateKeyDeserializer.INSTANCE);
        addKeyDeserializer(LocalTime.class, LocalTimeKeyDeserializer.INSTANCE);
        addKeyDeserializer(MonthDay.class, MonthDayKeyDeserializer.INSTANCE);
        addKeyDeserializer(OffsetDateTime.class, OffsetDateTimeKeyDeserializer.INSTANCE);
        addKeyDeserializer(OffsetTime.class, OffsetTimeKeyDeserializer.INSTANCE);
        addKeyDeserializer(Period.class, PeriodKeyDeserializer.INSTANCE);
        addKeyDeserializer(Year.class, YearKeyDeserializer.INSTANCE);
        addKeyDeserializer(YearMonth.class, YearMothKeyDeserializer.INSTANCE);
        addKeyDeserializer(ZonedDateTime.class, ZonedDateTimeKeyDeserializer.INSTANCE);
        addKeyDeserializer(ZoneId.class, ZoneIdKeyDeserializer.INSTANCE);
        addKeyDeserializer(ZoneOffset.class, ZoneOffsetKeyDeserializer.INSTANCE);
    }
}
