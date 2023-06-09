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
/* Generated By:JavaCC: Do not edit this line. ParserConstants.java */
/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.test.mapper.mapper.expression.parser;


/**
 * Token literal values and constants.
 * Generated by org.javacc.parser.OtherFilesGen#start()
 */
public interface ParserConstants {

    /** End of File. */
    int EOF = 0;
    /** RegularExpression Id. */
    int LINE_COMMENT = 6;
    /** RegularExpression Id. */
    int BLOCK_COMMENT = 7;
    /** RegularExpression Id. */
    int AND = 8;
    /** RegularExpression Id. */
    int OR = 9;
    /** RegularExpression Id. */
    int TRUE = 10;
    /** RegularExpression Id. */
    int FALSE = 11;
    /** RegularExpression Id. */
    int NULL = 12;
    /** RegularExpression Id. */
    int DECIMAL_LITERAL = 13;
    /** RegularExpression Id. */
    int ZERO = 14;
    /** RegularExpression Id. */
    int HEX_LITERAL = 15;
    /** RegularExpression Id. */
    int OCTAL_LITERAL = 16;
    /** RegularExpression Id. */
    int FLOATING_POINT_LITERAL = 17;
    /** RegularExpression Id. */
    int EXPONENT = 18;
    /** RegularExpression Id. */
    int STRING_LITERAL = 19;
    /** RegularExpression Id. */
    int ID = 20;
    /** RegularExpression Id. */
    int VARIABLE = 21;

    /** Lexical state. */
    int DEFAULT = 0;

    /** Literal token values. */
    String[] tokenImage = {
        "<EOF>",
        "\" \"",
        "\"\\t\"",
        "\"\\n\"",
        "\"\\r\"",
        "\"\\f\"",
        "<LINE_COMMENT>",
        "<BLOCK_COMMENT>",
        "\"&&\"",
        "\"||\"",
        "\"TRUE\"",
        "\"FALSE\"",
        "\"NULL\"",
        "<DECIMAL_LITERAL>",
        "\"0\"",
        "<HEX_LITERAL>",
        "<OCTAL_LITERAL>",
        "<FLOATING_POINT_LITERAL>",
        "<EXPONENT>",
        "<STRING_LITERAL>",
        "<ID>",
        "<VARIABLE>",
        "\"==\"",
        "\"!=\"",
        "\">\"",
        "\">=\"",
        "\"<\"",
        "\"<=\"",
        "\"+\"",
        "\"-\"",
        "\"*\"",
        "\"/\"",
        "\"%\"",
        "\"!\"",
        "\"(\"",
        "\")\"",
        "\",\"",
    };

}
