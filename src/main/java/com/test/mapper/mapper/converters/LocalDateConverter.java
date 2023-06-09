/*
 * Copyright (C) 2017 Red Hat, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.test.mapper.mapper.converters;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.CharBuffer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.test.mapper.mapper.api.AtlasConversionException;
import com.test.mapper.mapper.spi.AtlasConversionConcern;
import com.test.mapper.mapper.spi.AtlasConversionInfo;
import com.test.mapper.mapper.spi.AtlasConverter;
import com.test.mapper.mapper.v2.FieldType;

public class LocalDateConverter implements AtlasConverter<LocalDate> {

    @AtlasConversionInfo(sourceType = FieldType.DATE, targetType = FieldType.DECIMAL)
    public BigDecimal toBigDecimal(LocalDate value) {
        return value != null ? BigDecimal.valueOf(getStartEpochMilli(value)) : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.DATE, targetType = FieldType.BIG_INTEGER)
    public BigInteger toBigInteger(LocalDate value) {
        return value != null ? BigInteger.valueOf(getStartEpochMilli(value)) : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.DATE, targetType = FieldType.BYTE,
            concerns = AtlasConversionConcern.RANGE)
    public Byte toByte(LocalDate value) throws AtlasConversionException {
        if (value == null) {
            return null;
        }
        Long longValue = getStartEpochMilli(value);
        if (longValue >= Byte.MIN_VALUE && longValue <= Byte.MAX_VALUE) {
            return longValue.byteValue();
        }
        throw new AtlasConversionException(
                String.format("LocalDate %s is greater than Byte.MAX_VALUE or less than Byte.MIN_VALUE", value));
    }

    @AtlasConversionInfo(sourceType = FieldType.DATE, targetType = FieldType.DATE_TIME_TZ)
    public Calendar toCalendar(LocalDate value) {
        return value != null ? GregorianCalendar.from(value.atStartOfDay(ZoneId.systemDefault())) : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.DATE, targetType = FieldType.DATE_TIME)
    public Date toDate(LocalDate value) {
        return value != null ? new Date(getStartEpochMilli(value)) : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.DATE, targetType = FieldType.DOUBLE)
    public Double toDouble(LocalDate value) {
        return value != null ? getStartEpochMilli(value).doubleValue() : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.DATE, targetType = FieldType.FLOAT)
    public Float toFloat(LocalDate value) {
        return value != null ? getStartEpochMilli(value).floatValue() : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.DATE, targetType = FieldType.DATE_TIME_TZ)
    public GregorianCalendar toGregorianCalendar(LocalDate value) {
        return value != null ? GregorianCalendar.from(value.atStartOfDay(ZoneId.systemDefault())) : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.DATE, targetType = FieldType.INTEGER,
            concerns = AtlasConversionConcern.RANGE)
    public Integer toInteger(LocalDate value) throws AtlasConversionException {
        if (value == null) {
            return null;
        }
        Long longValue = getStartEpochMilli(value);
        if (longValue > Integer.MAX_VALUE || longValue < Integer.MIN_VALUE) {
            throw new AtlasConversionException(String
                    .format("LocalDate %s is greater than Integer.MAX_VALUE or less than Integer.MIN_VALUE", value));
        }
        return longValue.intValue();
    }

    @AtlasConversionInfo(sourceType = FieldType.DATE, targetType = FieldType.DATE)
    public LocalDate toLocalDate(LocalDate value) {
        return value;
    }

    @AtlasConversionInfo(sourceType = FieldType.DATE, targetType = FieldType.DATE_TIME)
    public LocalDateTime toLocalDateTime(LocalDate value) {
        return value != null ? value.atStartOfDay() : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.DATE, targetType = FieldType.TIME)
    public LocalTime toLocalTime(LocalDate value) {
        return value != null ? value.atStartOfDay().toLocalTime() : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.DATE, targetType = FieldType.LONG)
    public Long toLong(LocalDate value) {
        return value != null ? getStartEpochMilli(value) : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.DATE, targetType = FieldType.SHORT,
            concerns = AtlasConversionConcern.RANGE)
    public Short toShort(LocalDate value) throws AtlasConversionException {
        if (value == null) {
            return null;
        }
        Long longValue = getStartEpochMilli(value);
        if (longValue > Short.MAX_VALUE || longValue < Short.MIN_VALUE) {
            throw new AtlasConversionException(
                    String.format("LocalDate %s is greater than Short.MAX_VALUE or less than Short.MIN_VALUE", value));
        }
        return longValue.shortValue();
    }

    @AtlasConversionInfo(sourceType = FieldType.DATE, targetType = FieldType.STRING)
    public String toString(LocalDate value) {
        return value != null ? value.toString() : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.DATE, targetType = FieldType.STRING)
    public CharBuffer toCharBuffer(LocalDate value) {
        return value != null ? CharBuffer.wrap(toString(value)) : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.DATE, targetType = FieldType.STRING)
    public CharSequence toCharSequence(LocalDate value) {
        return value != null ? toString(value) : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.DATE, targetType = FieldType.STRING)
    public StringBuffer toStringBuffer(LocalDate value) {
        return value != null ? new StringBuffer(toString(value)) : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.DATE, targetType = FieldType.STRING)
    public StringBuilder toStringBuilder(LocalDate value) {
        return value != null ? new StringBuilder(toString(value)) : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.DATE, targetType = FieldType.NUMBER)
    public Number toNumber(LocalDate value) {
        return value != null ? getStartEpochMilli(value) : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.DATE, targetType = FieldType.DATE)
    public java.sql.Date toSqlDate(LocalDate value) {
        return java.sql.Date.valueOf(value);
    }

    @AtlasConversionInfo(sourceType = FieldType.DATE, targetType = FieldType.DATE_TIME)
    public java.sql.Timestamp toSqlTimestamp(LocalDate value) {
        return value != null ? java.sql.Timestamp.valueOf(value.atStartOfDay()) : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.DATE, targetType = FieldType.DATE_TIME_TZ)
    public ZonedDateTime toZonedDateTime(LocalDate value) {
        return value != null ? value.atStartOfDay(ZoneId.systemDefault()) : null;
    }

    private Long getStartEpochMilli(LocalDate value) {
        return value != null ? value.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli() : null;
    }
}
