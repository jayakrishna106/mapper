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
import java.time.ZonedDateTime;
import java.util.Date;

import com.test.mapper.mapper.api.AtlasConversionException;
import com.test.mapper.mapper.spi.AtlasConversionConcern;
import com.test.mapper.mapper.spi.AtlasConversionInfo;
import com.test.mapper.mapper.spi.AtlasConverter;
import com.test.mapper.mapper.v2.FieldType;

public class StringBufferConverter implements AtlasConverter<StringBuffer> {

    private CharSequenceConverter delegate = new CharSequenceConverter();

    @AtlasConversionInfo(sourceType = FieldType.STRING, targetType = FieldType.DECIMAL,
            concerns = AtlasConversionConcern.FORMAT)
    public BigDecimal toBigDecimal(StringBuffer value) throws AtlasConversionException {
        return delegate.toBigDecimal(value);
    }

    @AtlasConversionInfo(sourceType = FieldType.STRING, targetType = FieldType.BIG_INTEGER,
            concerns = AtlasConversionConcern.FORMAT)
    public BigInteger toBigInteger(StringBuffer value) throws AtlasConversionException {
        return delegate.toBigInteger(value);
    }

    @AtlasConversionInfo(sourceType = FieldType.STRING, targetType = FieldType.BOOLEAN, concerns = AtlasConversionConcern.CONVENTION)
    public Boolean toBoolean(StringBuffer value, String sourceFormat, String targetFormat) {
        return delegate.toBoolean(value, sourceFormat, targetFormat);
    }

    @AtlasConversionInfo(sourceType = FieldType.STRING, targetType = FieldType.BYTE, concerns = {
            AtlasConversionConcern.RANGE, AtlasConversionConcern.FORMAT, AtlasConversionConcern.FRACTIONAL_PART})
    public Byte toByte(StringBuffer value) throws AtlasConversionException {
        return delegate.toByte(value);
    }

    @AtlasConversionInfo(sourceType = FieldType.STRING, targetType = FieldType.CHAR, concerns = AtlasConversionConcern.RANGE)
    public Character toCharacter(StringBuffer value) throws AtlasConversionException {
        return delegate.toCharacter(value);
    }

    @AtlasConversionInfo(sourceType = FieldType.STRING, targetType = FieldType.DATE_TIME)
    public Date toDate(StringBuffer date, String sourceFormat, String targetFormat) {
        return delegate.toDate(date, sourceFormat, targetFormat);
    }

    @AtlasConversionInfo(sourceType = FieldType.STRING, targetType = FieldType.DOUBLE, concerns = {
            AtlasConversionConcern.FORMAT, AtlasConversionConcern.RANGE })
    public Double toDouble(StringBuffer value) throws AtlasConversionException {
        return delegate.toDouble(value);
    }

    @AtlasConversionInfo(sourceType = FieldType.STRING, targetType = FieldType.FLOAT, concerns = {
            AtlasConversionConcern.FORMAT, AtlasConversionConcern.RANGE })
    public Float toFloat(StringBuffer value) throws AtlasConversionException {
        return delegate.toFloat(value);
    }

    @AtlasConversionInfo(sourceType = FieldType.STRING, targetType = FieldType.INTEGER, concerns = {
            AtlasConversionConcern.FORMAT, AtlasConversionConcern.RANGE, AtlasConversionConcern.FRACTIONAL_PART })
    public Integer toInteger(StringBuffer value) throws AtlasConversionException {
        return delegate.toInteger(value);
    }

    @AtlasConversionInfo(sourceType = FieldType.STRING, targetType = FieldType.DATE)
    public LocalDate toLocalDate(StringBuffer value) {
        return delegate.toLocalDate(value);
    }

    @AtlasConversionInfo(sourceType = FieldType.STRING, targetType = FieldType.TIME)
    public LocalTime toLocalTime(StringBuffer value) {
        return delegate.toLocalTime(value);
    }

    @AtlasConversionInfo(sourceType = FieldType.STRING, targetType = FieldType.DATE_TIME)
    public LocalDateTime toLocalDateTime(StringBuffer value) {
        return delegate.toLocalDateTime(value);
    }

    @AtlasConversionInfo(sourceType = FieldType.STRING, targetType = FieldType.LONG, concerns = {
            AtlasConversionConcern.FORMAT, AtlasConversionConcern.RANGE, AtlasConversionConcern.FRACTIONAL_PART })
    public Long toLong(StringBuffer value) throws AtlasConversionException {
        return delegate.toLong(value);
    }

    @AtlasConversionInfo(sourceType = FieldType.STRING, targetType = FieldType.SHORT, concerns = {
            AtlasConversionConcern.FORMAT, AtlasConversionConcern.RANGE, AtlasConversionConcern.FRACTIONAL_PART })
    public Short toShort(StringBuffer value) throws AtlasConversionException {
        return delegate.toShort(value);
    }

    @AtlasConversionInfo(sourceType = FieldType.STRING, targetType = FieldType.STRING)
    public CharBuffer toCharBuffer(StringBuffer value, String sourceFormat, String targetFormat) {
        return delegate.toCharBuffer(value, sourceFormat, targetFormat);
    }

    @AtlasConversionInfo(sourceType = FieldType.STRING, targetType = FieldType.STRING)
    public CharSequence toCharSequence(StringBuffer value, String sourceFormat, String targetFormat) {
        return delegate.toCharSequence(value, sourceFormat, targetFormat);
    }

    @AtlasConversionInfo(sourceType = FieldType.STRING, targetType = FieldType.STRING)
    public String toString(StringBuffer value, String sourceFormat, String targetFormat) {
        return delegate.toString(value, sourceFormat, targetFormat);
    }

    @AtlasConversionInfo(sourceType = FieldType.STRING, targetType = FieldType.STRING)
    public StringBuffer toStringBuffer(StringBuffer value, String sourceFormat, String targetFormat) {
        return delegate.toStringBuffer(value, sourceFormat, targetFormat);
    }

    @AtlasConversionInfo(sourceType = FieldType.STRING, targetType = FieldType.STRING)
    public StringBuilder toStringBuilder(StringBuffer value, String sourceFormat, String targetFormat) {
        return delegate.toStringBuilder(value, sourceFormat, targetFormat);
    }

    @AtlasConversionInfo(sourceType = FieldType.STRING, targetType = FieldType.NUMBER, concerns = {
            AtlasConversionConcern.FORMAT })
    public Number toNumber(StringBuffer value) throws AtlasConversionException {
        return delegate.toNumber(value);
    }

    @AtlasConversionInfo(sourceType = FieldType.STRING, targetType = FieldType.DATE_TIME_TZ)
    public ZonedDateTime toZonedDateTime(StringBuffer value) {
        return delegate.toZonedDateTime(value);
    }

}
