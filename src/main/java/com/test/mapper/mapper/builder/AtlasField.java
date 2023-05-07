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
package com.test.mapper.mapper.builder;

import java.util.List;

import com.test.mapper.mapper.api.AtlasException;
import com.test.mapper.mapper.core.ConstantModule;
import com.test.mapper.mapper.core.DefaultAtlasConversionService;
import com.test.mapper.mapper.core.DefaultAtlasFieldActionService;
import com.test.mapper.mapper.core.DefaultAtlasSession;
import com.test.mapper.mapper.core.PropertyModule;
import com.test.mapper.mapper.spi.ActionProcessor;
import com.test.mapper.mapper.spi.AtlasModule;
import com.test.mapper.mapper.spi.AtlasModuleMode;
import com.test.mapper.mapper.v2.Constant;
import com.test.mapper.mapper.v2.Field;
import com.test.mapper.mapper.v2.PropertyField;

/**
 * A part of custom mapping builder API to implement custom mapping logic in Java code.
 * This class wraps raw {@link Field} and provide some utility methods to introspect
 * underlying field tree. {@link DefaultAtlasMappingBuilder#read(String, String)}
 * reads from source document and creates AtlasField.
 * @see DefaultAtlasMappingBuilder
 */
public class AtlasField {

    private DefaultAtlasSession session;
    private DefaultAtlasConversionService conversionService;
    private DefaultAtlasFieldActionService fieldActionService;
    private Field rawField;

    public AtlasField(DefaultAtlasSession session) {
        this.session = session;
        this.conversionService = session.getAtlasContext().getContextFactory().getConversionService();
        this.fieldActionService = session.getAtlasContext().getContextFactory().getFieldActionService();
    }

    public AtlasField read(String docId, String path) throws AtlasException {
        AtlasModule module = session.resolveModule(docId);
        if (module == null) {
            throw new AtlasException(String.format("Source document '%s' doesn't exist", docId));
        }
        if (module.getMode() != AtlasModuleMode.SOURCE) {
            throw new AtlasException(String.format(
                    "Unable to read from %s Document '%s'", module.getMode(), docId));
        }
        Field sourceField = module.createField();
        sourceField.setDocId(docId);
        sourceField.setPath(path);
        session.head().setSourceField(sourceField);
        module.readSourceValue(session);
        setRawField(sourceField);
        return this;
    }

    public AtlasField readConstant(String name) throws AtlasException {
        ConstantModule module = session.getConstantModule();
        List<Constant> constants = session.getMapping().getConstants().getConstant();
        for (Constant constant : constants) {
            if (constant.getName() != null && constant.getName().equals(name)) {
                Field sourceField = module.createField();
                sourceField.setName(constant.getName());
                sourceField.setFieldType(constant.getFieldType());
                sourceField.setValue(constant.getValue());
                session.head().setSourceField(sourceField);
                module.readSourceValue(session);
                setRawField(sourceField);
                return this;
            }
        }
        throw new AtlasException(String.format("Constant '%s' not found", name));
    }

    public AtlasField readProperty(String scope, String name) throws AtlasException {
        PropertyModule module = session.getSourcePropertyModule();
        PropertyField sourceField = module.createField();
        sourceField.setScope(scope);
        sourceField.setName(name);
        session.head().setSourceField(sourceField);
        module.readSourceValue(session);
        setRawField(sourceField);
        return this;
    }

    public void write(String docId, String path) throws AtlasException {
        AtlasModule module = session.resolveModule(docId);
        if (module == null) {
            throw new AtlasException(String.format("Target document '%s' doesn't exist", docId));
        }
        if (module.getMode() != AtlasModuleMode.TARGET) {
            throw new AtlasException(String.format(
                    "Unable to write to %s Document '%s'", module.getMode(), docId));
        }
        Field f = module.createField();
        f.setDocId(docId);
        f.setPath(path);
        session.head().setSourceField(getRawField());
        session.head().setTargetField(f);
        module.populateTargetField(session);
        module.writeTargetValue(session);
    }

    public void writeProperty(String scope, String name) throws AtlasException {
        PropertyModule module = session.getTargetPropertyModule();
        PropertyField f = module.createField();
        f.setScope(scope);
        f.setName(name);
        session.head().setSourceField(getRawField());
        session.head().setTargetField(f);
        module.populateTargetField(session);
        module.writeTargetValue(session);
    }

    public AtlasField action(String actionName, List<Object> parameters) {
        Object value = parameters != null && parameters.size() > 1 ? parameters.get(parameters.size()-1) : null;
        ActionProcessor ap = this.fieldActionService.findActionProcessor(actionName, value);
        
        return this;
    }

    public Field getRawField() {
        return this.rawField;
    }

    public AtlasField setRawField(Field f) {
        this.rawField = f;
        return this;
    }
}
