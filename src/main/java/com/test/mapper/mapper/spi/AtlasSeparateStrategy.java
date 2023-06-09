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

import java.util.List;

/**
 * @deprecated COMBINE/SEPARATE mode has been deprecated. Use transformations with multiple field selection.
 */
@Deprecated
public interface AtlasSeparateStrategy {

    default String getName() {
        return this.getClass().getSimpleName();
    }

    StringDelimiter getDelimiter();

    void setDelimiter(StringDelimiter delimiter);

    Integer getLimit();

    void setLimit(Integer limit);

    List<String> separateValue(String value);

    List<String> separateValue(String value, StringDelimiter delimiter);

    List<String> separateValue(String value, StringDelimiter delimiter, Integer limit);
}
