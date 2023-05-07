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
package com.test.mapper.mapper.actions;

import static com.test.mapper.mapper.v2.AtlasModelFactory.unwrapField;
import static com.test.mapper.mapper.v2.AtlasModelFactory.wrapWithField;

import java.util.List;

import com.test.mapper.mapper.core.DefaultAtlasFunctionResolver;
import com.test.mapper.mapper.expression.Expression;
import com.test.mapper.mapper.expression.ExpressionException;
import com.test.mapper.mapper.spi.AtlasActionProcessor;
import com.test.mapper.mapper.spi.AtlasFieldAction;
import com.test.mapper.mapper.v2.Field;

public class ExpressionFieldAction implements AtlasFieldAction {

    @AtlasActionProcessor
    public static Object process(com.test.mapper.mapper.v2.Expression action, List<Object> args) throws ExpressionException {
        if (action.getExpression() == null || action.getExpression().trim().isEmpty()) {
            return null;
        }

        Expression parsedExpression = Expression.parse(action.getExpression(), DefaultAtlasFunctionResolver.getInstance());
        Field answer = parsedExpression.evaluate((index) -> {
            try {
                return wrapWithField(args.get(Integer.parseInt(index)));
            } catch (Throwable e) {
                throw new ExpressionException("Invalid variable: " + index);
            }
        });
        return unwrapField(answer);
    }

}
