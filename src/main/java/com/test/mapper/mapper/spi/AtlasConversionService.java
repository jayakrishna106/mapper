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
package com.test.mapper.mapper.spi;

import java.util.Optional;

import com.test.mapper.mapper.api.AtlasConversionException;
import com.test.mapper.mapper.v2.FieldType;

public interface AtlasConversionService {

    Optional<AtlasConverter<?>> findMatchingConverter(FieldType source, FieldType target);

    Optional<AtlasConverter<?>> findMatchingConverter(String sourceClassName, String targetClassName);

    Object copyPrimitive(Object sourceValue);

    /**
     * Perform type conversion with specifying {@link FieldType}. This method focuses on
     * conversion between different field types with using AtlasMap field type representative classes.
     * Use {@link #convertType(Object, String, Class, String)} to convert into specific Java class.
     * @see #convertType(Object, String, Class, String)
     *
     * @param sourceValue source value to convert
     * @param origSourceType {@link FieldType} of source field
     * @param targetType {@link FieldType} of target field
     * @return converted value
     * @throws AtlasConversionException conversion failed
     */
    Object convertType(Object sourceValue, FieldType origSourceType, FieldType targetType)
            throws AtlasConversionException;

    /**
     * Perform type conversion with specifying {@link FieldType}. This method focuses on
     * conversion between different field types with using AtlasMap field type representative classes.
     * Use {@link #convertType(Object, String, Class, String)} to convert into specific Java class.
     * @see #convertType(Object, String, Class, String)
     *
     * @param sourceValue source value to convert
     * @param sourceFormat source value format
     * @param targetType {@link FieldType} of target field
     * @param targetFormat target value format
     * @return converted value
     * @throws AtlasConversionException conversion failed
     */
    Object convertType(Object sourceValue, String sourceFormat, FieldType targetType, String targetFormat)
            throws AtlasConversionException;

    /**
     * Perform type conversion with specifying target {@link Class} regardless of AtlasMap {@link FieldType}.
     * @see #convertType(Object, FieldType, FieldType)
     *
     * @param sourceValue source value to convert
     * @param sourceFormat source value format
     * @param targetType {@link Class} of target value
     * @param targetFormat target value format
     * @return converted value
     * @throws AtlasConversionException conversion failed
     */
    Object convertType(Object sourceValue, String sourceFormat, Class<?> targetType, String targetFormat)
            throws AtlasConversionException;

    boolean isConvertionAvailableFor(Object sourceValue, Class<?> targetType);

    Class<?> boxOrUnboxPrimitive(Class<?> clazz);

    Class<?> boxOrUnboxPrimitive(String clazzName);

    Class<?> classFromFieldType(FieldType fieldType);

    FieldType fieldTypeFromClass(Class<?> clazz);

    FieldType fieldTypeFromClass(String className);

    Boolean isPrimitive(String className);

    Boolean isPrimitive(Class<?> clazz);

    Boolean isPrimitive(FieldType fieldType);

    Boolean isBoxedPrimitive(Class<?> clazz);

    Boolean isAssignableFieldType(FieldType source, FieldType target);

}
