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
package com.test.mapper.mapper.core;

import com.test.mapper.mapper.api.AtlasConversionException;
import com.test.mapper.mapper.api.AtlasUnsupportedException;
import com.test.mapper.mapper.spi.AtlasConversionConcern;
import com.test.mapper.mapper.spi.AtlasConversionInfo;
import com.test.mapper.mapper.v2.FieldType;

/**
 */
public class MockPrimitiveConverter {

    @AtlasConversionInfo(sourceType = FieldType.STRING, targetType = FieldType.BOOLEAN, concerns = AtlasConversionConcern.RANGE)
    public Boolean convertToBoolean(String value) throws AtlasConversionException {
        if (value == null) {
            return null;
        }
        if ("0".equals(value) || "f".equalsIgnoreCase(value) || "false".equals(value)) {
            return Boolean.FALSE;
        } else if ("1".equals(value) || "t".equalsIgnoreCase(value) || "true".equals(value)) {
            return Boolean.TRUE;
        }
        throw new AtlasConversionException("String " + value + " cannot be converted to a Boolean");
    }

    @AtlasConversionInfo(sourceType = FieldType.STRING, targetType = FieldType.BYTE, concerns = AtlasConversionConcern.UNSUPPORTED)
    public Byte convertToByte(String value) throws AtlasConversionException {
        throw new AtlasConversionException(new AtlasUnsupportedException("not supported"));
    }

    public Character convertToCharacter(String value) {
        return 'a';
    }

    public Double convertToDouble(String value) {
        return Double.MAX_VALUE;
    }

    public Float convertToFloat(String value) {
        return Float.MAX_VALUE;
    }

    public Integer convertToInteger(String value) {
        return Integer.MAX_VALUE;
    }

    public Long convertToLong(String value) {
        return Long.MAX_VALUE;
    }

    public Short convertToShort(String value) {
        return Short.MAX_VALUE;
    }

    public String convertToString(String value) {
        return "aString";
    }
}
