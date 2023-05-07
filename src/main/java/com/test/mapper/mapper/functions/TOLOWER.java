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
package com.test.mapper.mapper.functions;

import java.util.List;

import com.test.mapper.mapper.core.BaseFunctionFactory;
import com.test.mapper.mapper.expression.Expression;
import com.test.mapper.mapper.expression.parser.ParseException;
import com.test.mapper.mapper.v2.AtlasModelFactory;

public class TOLOWER extends BaseFunctionFactory {

    @Override
    public Expression create(List<Expression> args) throws ParseException {
        if (args.size() != 1) {
            throw new ParseException("TOLOWER expects 1 argument.");
        }
        Expression arg = args.get(0);
        return (ctx) -> {
            Object value = arg.evaluate(ctx).getValue();
            return AtlasModelFactory.wrapWithField(value == null ? null : value.toString().toLowerCase());
        };
    }

}
