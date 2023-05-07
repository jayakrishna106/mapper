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
package com.test.mapper.mapper.json.module;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.test.mapper.mapper.core.validate.BaseModuleValidationService;
import com.test.mapper.mapper.json.v2.JsonField;
import com.test.mapper.mapper.spi.AtlasConversionService;
import com.test.mapper.mapper.spi.AtlasFieldActionService;
import com.test.mapper.mapper.spi.AtlasModuleDetail;
import com.test.mapper.mapper.spi.AtlasValidator;
import com.test.mapper.mapper.spi.FieldDirection;
import com.test.mapper.mapper.v2.Validation;
import com.test.mapper.mapper.v2.ValidationScope;
import com.test.mapper.mapper.v2.ValidationStatus;
import com.test.mapper.mapper.validators.NonNullValidator;

public class JsonValidationService extends BaseModuleValidationService<JsonField> {

    private static Map<String, AtlasValidator> validatorMap = new HashMap<>();
    private AtlasModuleDetail moduleDetail = JsonModule.class.getAnnotation(AtlasModuleDetail.class);

    public JsonValidationService(AtlasConversionService conversionService, AtlasFieldActionService fieldActionService) {
        super(conversionService, fieldActionService);
        init();
    }

    public void init() {
        NonNullValidator javaFilePathNonNullValidator = new NonNullValidator(ValidationScope.MAPPING,
                "The path element must not be null nor empty");
        NonNullValidator inputFieldTypeNonNullValidator = new NonNullValidator(ValidationScope.MAPPING,
                "Field type should not be null nor empty");
        NonNullValidator outputFieldTypeNonNullValidator = new NonNullValidator(ValidationScope.MAPPING,
                "Field type should not be null nor empty");
        NonNullValidator fieldTypeNonNullValidator = new NonNullValidator(ValidationScope.MAPPING,
                "Filed type should not be null nor empty");

        validatorMap.put("json.field.type.not.null", fieldTypeNonNullValidator);
        validatorMap.put("json.field.path.not.null", javaFilePathNonNullValidator);
        validatorMap.put("input.field.type.not.null", inputFieldTypeNonNullValidator);
        validatorMap.put("output.field.type.not.null", outputFieldTypeNonNullValidator);
    }

    public void destroy() {
        validatorMap.clear();
    }

    @Override
    protected AtlasModuleDetail getModuleDetail() {
        return moduleDetail;
    }

    @Override
    protected Class<JsonField> getFieldType() {
        return JsonField.class;
    }

    @Override
    protected String getModuleFieldName(JsonField field) {
        return field.getName() != null ? field.getName() : field.getPath();
    }

    @Override
    protected void validateModuleField(String mappingId, JsonField field, FieldDirection direction, List<Validation> validations) {
        // TODO check that it is a valid type on the AtlasContext

        validatorMap.get("json.field.type.not.null").validate(field, validations, mappingId, ValidationStatus.WARN);

        if (field.getPath() == null) {
            validatorMap.get("json.field.path.not.null").validate(field.getPath(), validations, mappingId);
        }
    }
}
