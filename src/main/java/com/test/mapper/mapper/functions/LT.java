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
import com.test.mapper.mapper.expression.internal.ComparisonExpression;
import com.test.mapper.mapper.expression.parser.ParseException;

public class LT extends BaseFunctionFactory {

    @Override
    public Expression create(List<Expression> args) throws ParseException {
        if (args.size() != 2) {
            throw new ParseException("LT expects 2 arguments.");
        }
        return ComparisonExpression.createLessThan(args.get(0), args.get(1));
    }

}
