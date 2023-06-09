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

public class LocalTimeConverter implements AtlasConverter<LocalTime> {

    @AtlasConversionInfo(sourceType = FieldType.TIME, targetType = FieldType.DECIMAL)
    public BigDecimal toBigDecimal(LocalTime value) {
        return value != null ? BigDecimal.valueOf(getTodaysEpochMilli(value)) : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.TIME, targetType = FieldType.BIG_INTEGER)
    public BigInteger toBigInteger(LocalTime value) {
        return value != null ? BigInteger.valueOf(getTodaysEpochMilli(value)) : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.TIME, targetType = FieldType.BYTE,
            concerns = AtlasConversionConcern.RANGE)
    public Byte toByte(LocalTime value) throws AtlasConversionException {
        if (value == null) {
            return null;
        }
        Long longValue = getTodaysEpochMilli(value);
        if (longValue >= Byte.MIN_VALUE && longValue <= Byte.MAX_VALUE) {
            return longValue.byteValue();
        }
        throw new AtlasConversionException(
                String.format("LocalTime %s of today is greater than Byte.MAX_VALUE or less than Byte.MIN_VALUE", value));
    }

    @AtlasConversionInfo(sourceType = FieldType.TIME, targetType = FieldType.DATE_TIME_TZ)
    public Calendar toCalendar(LocalTime value) {
        return value != null ? GregorianCalendar.from(value.atDate(LocalDate.now()).atZone(ZoneId.systemDefault())) : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.TIME, targetType = FieldType.DATE_TIME)
    public Date toDate(LocalTime value) {
        return value != null ? new Date(getTodaysEpochMilli(value)) : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.TIME, targetType = FieldType.DOUBLE)
    public Double toDouble(LocalTime value) {
        return value != null ? getTodaysEpochMilli(value).doubleValue() : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.TIME, targetType = FieldType.FLOAT)
    public Float toFloat(LocalTime value) {
        return value != null ? getTodaysEpochMilli(value).floatValue() : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.TIME, targetType = FieldType.DATE_TIME_TZ)
    public GregorianCalendar toGregorianCalendar(LocalTime value) {
        return value != null ? GregorianCalendar.from(value.atDate(LocalDate.now()).atZone(ZoneId.systemDefault())) : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.TIME, targetType = FieldType.INTEGER,
            concerns = AtlasConversionConcern.RANGE)
    public Integer toInteger(LocalTime value) throws AtlasConversionException {
        if (value == null) {
            return null;
        }
        Long longValue = getTodaysEpochMilli(value);
        if (longValue > Integer.MAX_VALUE || longValue < Integer.MIN_VALUE) {
            throw new AtlasConversionException(String
                    .format("LocalTime nano-of-day %s is greater than Integer.MAX_VALUE or less than Integer.MIN_VALUE", longValue));
        }
        return longValue.intValue();
    }

    @AtlasConversionInfo(sourceType = FieldType.TIME, targetType = FieldType.DATE_TIME)
    public LocalDateTime toLocalDateTime(LocalTime value) {
        return value != null ? value.atDate(LocalDate.now()) : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.TIME, targetType = FieldType.TIME)
    public LocalTime toLocalTime(LocalTime value) {
        return value;
    }

    @AtlasConversionInfo(sourceType = FieldType.TIME, targetType = FieldType.LONG)
    public Long toLong(LocalTime value) {
        return value != null ? getTodaysEpochMilli(value) : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.TIME, targetType = FieldType.SHORT,
            concerns = AtlasConversionConcern.RANGE)
    public Short toShort(LocalTime value) throws AtlasConversionException {
        if (value == null) {
            return null;
        }
        Long longValue = getTodaysEpochMilli(value);
        if (longValue > Short.MAX_VALUE || longValue < Short.MIN_VALUE) {
            throw new AtlasConversionException(
                    String.format("LocalTime nano-of-day %s is greater than Short.MAX_VALUE or less than Short.MIN_VALUE", longValue));
        }
        return longValue.shortValue();
    }

    @AtlasConversionInfo(sourceType = FieldType.TIME, targetType = FieldType.STRING)
    public String toString(LocalTime value) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }

    @AtlasConversionInfo(sourceType = FieldType.TIME, targetType = FieldType.STRING)
    public CharBuffer toCharBuffer(LocalTime value) {
        return value != null ? CharBuffer.wrap(toString(value)) : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.TIME, targetType = FieldType.STRING)
    public CharSequence toCharSequence(LocalTime value) {
        return value != null ? toString(value) : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.TIME, targetType = FieldType.STRING)
    public StringBuffer toStringBuffer(LocalTime value) {
        return value != null ? new StringBuffer(toString(value)) : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.TIME, targetType = FieldType.STRING)
    public StringBuilder toStringBuilder(LocalTime value) {
        return value != null ? new StringBuilder(toString(value)) : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.TIME, targetType = FieldType.NUMBER)
    public Number toNumber(LocalTime value) {
        if (value == null) {
            return null;
        }
        return getTodaysEpochMilli(value);
    }

    @AtlasConversionInfo(sourceType = FieldType.TIME, targetType = FieldType.TIME)
    public java.sql.Time toSqlTime(LocalTime value) {
        return java.sql.Time.valueOf(value);
    }

    @AtlasConversionInfo(sourceType = FieldType.TIME, targetType = FieldType.DATE_TIME)
    public java.sql.Timestamp toSqlTimestamp(LocalTime value) {
        return value != null ? java.sql.Timestamp.valueOf(value.atDate(LocalDate.now())) : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.TIME, targetType = FieldType.DATE_TIME_TZ)
    public ZonedDateTime toZonedDateTime(LocalTime value) {
        return value != null ? value.atDate(LocalDate.now()).atZone(ZoneId.systemDefault()) : null;
    }

    private Long getTodaysEpochMilli(LocalTime value) {
        return value.atDate(LocalDate.now()).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}
