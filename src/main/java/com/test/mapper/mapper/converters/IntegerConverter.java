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
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import com.test.mapper.mapper.api.AtlasConversionException;
import com.test.mapper.mapper.api.AtlasUnsupportedException;
import com.test.mapper.mapper.spi.AtlasConversionConcern;
import com.test.mapper.mapper.spi.AtlasConversionInfo;
import com.test.mapper.mapper.spi.AtlasConverter;
import com.test.mapper.mapper.v2.FieldType;

public class IntegerConverter implements AtlasConverter<Integer> {

    @AtlasConversionInfo(sourceType = FieldType.INTEGER, targetType = FieldType.DECIMAL)
    public BigDecimal toBigDecimal(Integer value) {
        return value != null ? BigDecimal.valueOf(value) : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.INTEGER, targetType = FieldType.BIG_INTEGER)
    public BigInteger toBigInteger(Integer value) {
        return value != null ? BigInteger.valueOf(value) : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.INTEGER, targetType = FieldType.BOOLEAN, concerns = {
            AtlasConversionConcern.CONVENTION })
    public Boolean toBoolean(Integer value) {
        if (value == null) {
            return null;
        }
        return value == 0 ? Boolean.FALSE : Boolean.TRUE;
    }

    @AtlasConversionInfo(sourceType = FieldType.INTEGER, targetType = FieldType.BYTE, concerns = AtlasConversionConcern.RANGE)
    public Byte toByte(Integer value) throws AtlasConversionException {
        if (value == null) {
            return null;
        }
        if (value >= Byte.MIN_VALUE && value <= Byte.MAX_VALUE) {
            return value.byteValue();
        }
        throw new AtlasConversionException(new AtlasUnsupportedException(
                String.format("Integer %s is greater than Byte.MAX_VALUE or less than Byte.MIN_VALUE", value)));
    }

    @AtlasConversionInfo(sourceType = FieldType.INTEGER, targetType = FieldType.CHAR, concerns = {
            AtlasConversionConcern.RANGE, AtlasConversionConcern.CONVENTION })
    public Character toCharacter(Integer value) throws AtlasConversionException {
        if (value == null) {
            return null;
        }
        if (value < Character.MIN_VALUE || value > Character.MAX_VALUE) {
            throw new AtlasConversionException(String
                    .format("Integer %s is greater than Character.MAX_VALUE or less than Character.MIN_VALUE", value));
        }
        return Character.valueOf((char) value.intValue());
    }

    @AtlasConversionInfo(sourceType = FieldType.INTEGER, targetType = FieldType.DATE_TIME)
    public Date toDate(Integer value) {
        if (value >= Instant.MIN.getEpochSecond()) {
            return Date.from(Instant.ofEpochMilli(value));
        }
        return new Date(value);
    }

    @AtlasConversionInfo(sourceType = FieldType.INTEGER, targetType = FieldType.DOUBLE)
    public Double toDouble(Integer value) {
        return value != null ? value.doubleValue() : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.INTEGER, targetType = FieldType.FLOAT, concerns = AtlasConversionConcern.RANGE)
    public Float toFloat(Integer value) {
        return value != null ? value.floatValue() : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.INTEGER, targetType = FieldType.INTEGER)
    public Integer toInteger(Integer value) {
        return value != null ? Integer.valueOf(value) : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.INTEGER, targetType = FieldType.DATE)
    public LocalDate toLocalDate(Integer value) {
        return value != null ? Instant.ofEpochMilli(value).atZone(ZoneId.systemDefault()).toLocalDate() : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.INTEGER, targetType = FieldType.TIME)
    public LocalTime toLocalTime(Integer value) {
        return value != null ? Instant.ofEpochMilli(value).atZone(ZoneId.systemDefault()).toLocalTime() : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.INTEGER, targetType = FieldType.DATE_TIME)
    public LocalDateTime toLocalDateTime(Integer value) {
        return value != null ? Instant.ofEpochMilli(value).atZone(ZoneId.systemDefault()).toLocalDateTime() : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.INTEGER, targetType = FieldType.LONG)
    public Long toLong(Integer value) {
        return value != null ? value.longValue() : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.INTEGER, targetType = FieldType.SHORT, concerns = AtlasConversionConcern.RANGE)
    public Short toShort(Integer value) throws AtlasConversionException {
        if (value == null) {
            return null;
        }
        if (value > Short.MAX_VALUE || value < Short.MIN_VALUE) {
            throw new AtlasConversionException(
                    String.format("Integer %s is greater than Short.MAX_VALUE or less than Short.MIN_VALUE", value));
        }
        return value.shortValue();
    }

    @AtlasConversionInfo(sourceType = FieldType.INTEGER, targetType = FieldType.STRING)
    public String toString(Integer value) {
        return value != null ? String.valueOf(value) : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.INTEGER, targetType = FieldType.STRING)
    public CharBuffer toCharBuffer(Integer value) {
        return value != null ? CharBuffer.wrap(toString(value)) : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.INTEGER, targetType = FieldType.STRING)
    public CharSequence toCharSequence(Integer value) {
        return value != null ? toString(value) : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.INTEGER, targetType = FieldType.STRING)
    public StringBuffer toStringBuffer(Integer value) {
        return value != null ? new StringBuffer(toString(value)) : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.INTEGER, targetType = FieldType.STRING)
    public StringBuilder toStringBuilder(Integer value) {
        return value != null ? new StringBuilder(toString(value)) : null;
    }

    @AtlasConversionInfo(sourceType = FieldType.INTEGER, targetType = FieldType.NUMBER)
    public Number toNumber(Integer value) {
        return value;
    }

    @AtlasConversionInfo(sourceType = FieldType.INTEGER, targetType = FieldType.DATE_TIME_TZ)
    public ZonedDateTime toZonedDateTime(Integer value) {
        return value != null ? Instant.ofEpochMilli(value).atZone(ZoneId.systemDefault()) : null;
    }

}
