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

public class LocalDateTimeConverter implements AtlasConverter<LocalDateTime> {

    @AtlasConversionInfo(sourceType = FieldType.DATE_TIME, targetType = FieldType.DECIMAL)
    public BigDecimal toBigDecimal(LocalDateTime value) {
        return value != null ? BigDecimal.valueOf(getEpochMilli(value)) : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.DATE_TIME, targetType = FieldType.BIG_INTEGER)
    public BigInteger toBigInteger(LocalDateTime value) {
        return value != null ? BigInteger.valueOf(getEpochMilli(value)) : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.DATE_TIME, targetType = FieldType.BYTE,
            concerns = AtlasConversionConcern.RANGE)
    public Byte toByte(LocalDateTime value) throws AtlasConversionException {
        if (value == null) {
            return null;
        }
        Long longValue = getEpochMilli(value);
        if (longValue >= Byte.MIN_VALUE && longValue <= Byte.MAX_VALUE) {
            return longValue.byteValue();
        }
        throw new AtlasConversionException(
                String.format("LocalDateTime %s is greater than Byte.MAX_VALUE or less than Byte.MIN_VALUE", value));
    }

    @AtlasConversionInfo(sourceType = FieldType.DATE_TIME, targetType = FieldType.DATE_TIME_TZ)
    public Calendar toCalendar(LocalDateTime value) {
        return value != null ? GregorianCalendar.from(value.atZone(ZoneId.systemDefault())) : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.DATE_TIME, targetType = FieldType.DATE_TIME)
    public Date toDate(LocalDateTime value) {
        return value != null ? new Date(getEpochMilli(value)) : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.DATE_TIME, targetType = FieldType.DOUBLE)
    public Double toDouble(LocalDateTime value) {
        return value != null ? getEpochMilli(value).doubleValue() : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.DATE_TIME, targetType = FieldType.FLOAT)
    public Float toFloat(LocalDateTime value) {
        return value != null ? getEpochMilli(value).floatValue() : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.DATE_TIME, targetType = FieldType.DATE_TIME_TZ)
    public GregorianCalendar toGregorianCalendar(LocalDateTime value) {
        return value != null ? GregorianCalendar.from(value.atZone(ZoneId.systemDefault())) : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.DATE_TIME, targetType = FieldType.INTEGER,
            concerns = AtlasConversionConcern.RANGE)
    public Integer toInteger(LocalDateTime value) throws AtlasConversionException {
        if (value == null) {
            return null;
        }
        Long longValue = getEpochMilli(value);
        if (longValue > Integer.MAX_VALUE || longValue < Integer.MIN_VALUE) {
            throw new AtlasConversionException(String
                    .format("LocalDateTime %s is greater than Integer.MAX_VALUE or less than Integer.MIN_VALUE", value));
        }
        return longValue.intValue();
    }

    @AtlasConversionInfo(sourceType = FieldType.DATE_TIME, targetType = FieldType.DATE)
    public LocalDate toLocalDate(LocalDateTime value) {
        return value != null ? value.toLocalDate() : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.DATE_TIME, targetType = FieldType.DATE_TIME)
    public LocalDateTime toLocalDateTime(LocalDateTime value) {
        return value;
    }

    @AtlasConversionInfo(sourceType = FieldType.DATE_TIME, targetType = FieldType.TIME)
    public LocalTime toLocalTime(LocalDateTime value) {
        return value != null ? value.toLocalTime() : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.DATE_TIME, targetType = FieldType.LONG)
    public Long toLong(LocalDateTime value) {
        return value != null ? getEpochMilli(value) : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.DATE_TIME, targetType = FieldType.SHORT,
            concerns = AtlasConversionConcern.RANGE)
    public Short toShort(LocalDateTime value) throws AtlasConversionException {
        if (value == null) {
            return null;
        }
        Long longValue = getEpochMilli(value);
        if (longValue > Short.MAX_VALUE || longValue < Short.MIN_VALUE) {
            throw new AtlasConversionException(
                    String.format("LocalDateTime %s is greater than Short.MAX_VALUE or less than Short.MIN_VALUE", value));
        }
        return longValue.shortValue();
    }

    @AtlasConversionInfo(sourceType = FieldType.DATE_TIME, targetType = FieldType.STRING)
    public String toString(LocalDateTime value) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }

    @AtlasConversionInfo(sourceType = FieldType.DATE_TIME, targetType = FieldType.STRING)
    public CharBuffer toCharBuffer(LocalDateTime value) {
        return value != null ? CharBuffer.wrap(toString(value)) : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.DATE_TIME, targetType = FieldType.STRING)
    public CharSequence toCharSequence(LocalDateTime value) {
        return value != null ? toString(value) : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.DATE_TIME, targetType = FieldType.STRING)
    public StringBuffer toStringBuffer(LocalDateTime value) {
        return value != null ? new StringBuffer(toString(value)) : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.DATE_TIME, targetType = FieldType.STRING)
    public StringBuilder toStringBuilder(LocalDateTime value) {
        return value != null ? new StringBuilder(toString(value)) : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.DATE_TIME, targetType = FieldType.NUMBER)
    public Number toNumber(LocalDateTime value) {
        return value != null ? getEpochMilli(value) : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.DATE_TIME, targetType = FieldType.DATE)
    public java.sql.Date toSqlDate(LocalDateTime value) {
        return value != null ? java.sql.Date.valueOf(value.toLocalDate()) : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.DATE_TIME, targetType = FieldType.TIME)
    public java.sql.Time toSqlTime(LocalDateTime value) {
        return value != null ? java.sql.Time.valueOf(value.toLocalTime()) : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.DATE_TIME, targetType = FieldType.DATE_TIME)
    public java.sql.Timestamp toSqlTimestamp(LocalDateTime value) {
        return value != null ? java.sql.Timestamp.valueOf(value) : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.DATE_TIME, targetType = FieldType.DATE_TIME_TZ)
    public ZonedDateTime toZonedDateTime(LocalDateTime value) {
        return value != null ? value.atZone(ZoneId.systemDefault()) : null;
    }

    private Long getEpochMilli(LocalDateTime value) {
        return value.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

}
