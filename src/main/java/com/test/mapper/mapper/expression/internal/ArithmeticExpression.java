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
package com.test.mapper.mapper.expression.internal;

import static com.test.mapper.mapper.v2.AtlasModelFactory.wrapWithField;

import com.test.mapper.mapper.expression.Expression;
import com.test.mapper.mapper.expression.ExpressionContext;
import com.test.mapper.mapper.expression.ExpressionException;
import com.test.mapper.mapper.v2.Field;

/**
 * An expression which performs an operation on two expression values.
 * 
 * @version $Revision: 1.2 $
 */
public abstract class ArithmeticExpression extends BinaryExpression {

    protected static final int INTEGER = 1;
    protected static final int LONG = 2;
    protected static final int DOUBLE = 3;
    boolean convertStringExpressions = false;

    /**
     * Constructor.
     * @param left The left {@link Expression}
     * @param right The right {@link Expression}
     */
    public ArithmeticExpression(Expression left, Expression right) {
        super(left, right);
        convertStringExpressions = ComparisonExpression.CONVERT_STRING_EXPRESSIONS.get()!=null;
    }

    public static Expression createPlus(Expression left, Expression right) {
        return new ArithmeticExpression(left, right) {
            protected Field evaluate(Field lfield, Field rfield) {
                Object lvalue = lfield.getValue();
                Object rvalue = rfield.getValue();
                if (lvalue instanceof String) {
                    String text = (String)lvalue;
                    String answer = text + rvalue;
                    return wrapWithField(answer);
                } else {
                    return wrapWithField(plus(asNumber(lvalue), asNumber(rvalue)));
                }
            }

            public String getExpressionSymbol() {
                return "+";
            }
        };
    }

    public static Expression createMinus(Expression left, Expression right) {
        return new ArithmeticExpression(left, right) {
            protected Field evaluate(Field lfield, Field rfield) {
                Object lvalue = lfield.getValue();
                Object rvalue = rfield.getValue();
                return wrapWithField(minus(asNumber(lvalue), asNumber(rvalue)));
            }

            public String getExpressionSymbol() {
                return "-";
            }
        };
    }

    public static Expression createMultiply(Expression left, Expression right) {
        return new ArithmeticExpression(left, right) {

            protected Field evaluate(Field lfield, Field rfield) {
                Object lvalue = lfield.getValue();
                Object rvalue = rfield.getValue();
                return wrapWithField(multiply(asNumber(lvalue), asNumber(rvalue)));
            }

            public String getExpressionSymbol() {
                return "*";
            }
        };
    }

    public static Expression createDivide(Expression left, Expression right) {
        return new ArithmeticExpression(left, right) {

            protected Field evaluate(Field lfield, Field rfield) {
                Object lvalue = lfield.getValue();
                Object rvalue = rfield.getValue();
                return wrapWithField(divide(asNumber(lvalue), asNumber(rvalue)));
            }

            public String getExpressionSymbol() {
                return "/";
            }
        };
    }

    public static Expression createMod(Expression left, Expression right) {
        return new ArithmeticExpression(left, right) {

            protected Field evaluate(Field lfield, Field rfield) {
                Object lvalue = lfield.getValue();
                Object rvalue = rfield.getValue();
                return wrapWithField(mod(asNumber(lvalue), asNumber(rvalue)));
            }

            public String getExpressionSymbol() {
                return "%";
            }
        };
    }

    protected Number plus(Number left, Number right) {
        switch (numberType(left, right)) {
        case INTEGER:
            return new Integer(left.intValue() + right.intValue());
        case LONG:
            return new Long(left.longValue() + right.longValue());
        default:
            return new Double(left.doubleValue() + right.doubleValue());
        }
    }

    protected Number minus(Number left, Number right) {
        switch (numberType(left, right)) {
        case INTEGER:
            return new Integer(left.intValue() - right.intValue());
        case LONG:
            return new Long(left.longValue() - right.longValue());
        default:
            return new Double(left.doubleValue() - right.doubleValue());
        }
    }

    protected Number multiply(Number left, Number right) {
        switch (numberType(left, right)) {
        case INTEGER:
            return new Integer(left.intValue() * right.intValue());
        case LONG:
            return new Long(left.longValue() * right.longValue());
        default:
            return new Double(left.doubleValue() * right.doubleValue());
        }
    }

    protected Number divide(Number left, Number right) {
        return new Double(left.doubleValue() / right.doubleValue());
    }

    protected Number mod(Number left, Number right) {
        return new Double(left.doubleValue() % right.doubleValue());
    }

    private int numberType(Number left, Number right) {
        if (isDouble(left) || isDouble(right)) {
            return DOUBLE;
        } else if (left instanceof Long || right instanceof Long) {
            return LONG;
        } else {
            return INTEGER;
        }
    }

    private boolean isDouble(Number n) {
        return n instanceof Float || n instanceof Double;
    }

    protected Number asNumber(Object value) {
        if (value instanceof Number) {
            return (Number)value;
        } else {
            if( convertStringExpressions && value instanceof String) {
                String v = (String) value;
                try {
                    if( v.contains(".") ) {
                        return new Double(v);
                    } else {
                        return new Long(v);
                    }
                } catch (NumberFormatException e) {
                    throw new RuntimeException("Cannot convert value: " + value + " into a number");
                }
            }
            throw new RuntimeException("Cannot convert value: " + value + " into a number");
        }
    }

    public Field evaluate(ExpressionContext message) throws ExpressionException {
        Field lfield = left.evaluate(message);
        if (lfield == null || lfield.getValue() == null) {
            return wrapWithField(null);
        }
        Field rfield = right.evaluate(message);
        if (rfield == null || rfield.getValue() == null) {
            return null;
        }
        return evaluate(lfield, rfield);
    }

    /**
     * Evaluate expression.
     * @param lvalue {@link Field} represents left value
     * @param rvalue {@link Field} represents right value
     * @return {@link Field} reporesents a result
     */
    protected abstract Field evaluate(Field lvalue, Field rvalue);

}
