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

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.test.mapper.mapper.api.AtlasConstants;
import com.test.mapper.mapper.expression.Expression;
import com.test.mapper.mapper.expression.ExpressionException;
import com.test.mapper.mapper.spi.AtlasModule;
import com.test.mapper.mapper.v2.AtlasModelFactory;
import com.test.mapper.mapper.v2.AuditStatus;
import com.test.mapper.mapper.v2.ConstantField;
import com.test.mapper.mapper.v2.Field;
import com.test.mapper.mapper.v2.FieldGroup;
import com.test.mapper.mapper.v2.PropertyField;
import com.test.mapper.mapper.v2.SimpleField;

public class DefaultAtlasExpressionProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultAtlasExpressionProcessor.class);

    public static void processExpression(DefaultAtlasSession session, String expression) {
        if (expression == null || expression.trim().isEmpty()) {
            return;
        }

        try {
            Map<String, Field> sourceFieldMap = new HashMap<>();
            Field parent = session.head().getSourceField();
            if (parent != null && !AtlasUtil.isEmpty(parent.getDocId()) && !AtlasUtil.isEmpty(parent.getPath())) {
                 sourceFieldMap.put(parent.getDocId() + ":" + parent.getPath(), parent);
            }
            // Anonymous FieldGroup is just a wrapping, peel it off
            if (parent instanceof FieldGroup && AtlasUtil.isEmpty(parent.getPath())) {
                FieldGroup parentGroup = FieldGroup.class.cast(parent);
                for (Field child : parentGroup.getField()) {
                    if (!(AtlasUtil.isEmpty(child.getDocId()) && AtlasUtil.isEmpty(child.getPath()))) {
                         sourceFieldMap.put(child.getDocId() + ":" + child.getPath(), child);
                    }
                }
            }

            Expression parsedExpression = Expression.parse(expression, DefaultAtlasFunctionResolver.getInstance());
            Object answer = parsedExpression.evaluate((path) -> {
                if (path == null || path.isEmpty()) {
                    return null;
                }
                try {
                    Field f = sourceFieldMap.get(path);
                    if (f == null) {
                        return null;
                    }
                    AtlasModule sourceModule;
                    Map<String, AtlasModule> sourceModules = session.getAtlasContext().getSourceModules();
                    if (f instanceof ConstantField) {
                        sourceModule = sourceModules.get(AtlasConstants.CONSTANTS_DOCUMENT_ID);
                    } else if (f instanceof PropertyField) {
                        sourceModule = sourceModules.get(AtlasConstants.PROPERTIES_SOURCE_DOCUMENT_ID);
                    } else {
                        String[] splitted = path.split(":", 2);
                        sourceModule = sourceModules.get(splitted[0]);
                    }
                    if (sourceModule == null) {
                        throw new ExpressionException(String.format("Module for the path '%s' is not found", path));
                    }
                    session.head().setSourceField(f);
                    sourceModule.readSourceValue(session);
                    return session.head().getSourceField();
                } catch (Exception e) {
                    throw new ExpressionException(e);
                }
            });
            if (answer instanceof Field) {
                session.head().setSourceField((Field)answer);
            } else {
                Field from = session.head().getSourceField();
                SimpleField to = new SimpleField();
                AtlasModelFactory.copyField(from, to, false);
                to.setValue(answer);
                session.head().setSourceField(to);
            }
        } catch (Exception e) {
            AtlasUtil.addAudit(session, expression,
                    String.format("Expression processing error [%s]: %s",
                            expression, e.getMessage()),
                    AuditStatus.ERROR, null);
            if (LOG.isDebugEnabled()) {
                LOG.debug("", e);
            }
        }
    }

}
