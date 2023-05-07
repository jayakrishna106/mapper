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
package com.test.mapper.mapper.converters;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.jupiter.api.Test;

public class GregorianCalendarConverterTest {

    private GregorianCalendarConverter converter = new GregorianCalendarConverter();

    @Test
    public void convertFromGregorianCalendar() {
        Date date = converter.toDate((GregorianCalendar) GregorianCalendar.getInstance());
        assertNotNull(date);
    }

}