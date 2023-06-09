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
package com.test.mapper.mapper.validators;

import java.util.List;

import com.test.mapper.mapper.v2.Validation;
import com.test.mapper.mapper.v2.ValidationStatus;
import com.test.mapper.mapper.v2.Validations;

public class AtlasValidationTestHelper extends Validations {

    private static final long serialVersionUID = -7993298271986178508L;

    public void addValidation(Validation validation) {
        getValidation().add(validation);
    }

    public List<Validation> getAllValidations() {
        return validation;
    }

    public boolean hasErrors() {
        if (getValidation() != null && !getValidation().isEmpty()) {
            for (Validation validation : getValidation()) {
                if (ValidationStatus.ERROR.compareTo(validation.getStatus()) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasWarnings() {
        if (getValidation() != null && !getValidation().isEmpty()) {
            for (Validation validation : getValidation()) {
                if (ValidationStatus.WARN.compareTo(validation.getStatus()) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasInfos() {
        if (getValidation() != null && !getValidation().isEmpty()) {
            for (Validation validation : getValidation()) {
                if (ValidationStatus.INFO.compareTo(validation.getStatus()) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getCount() {
        if (getValidation() != null && !getValidation().isEmpty()) {
            return getValidation().size();
        }
        return 0;
    }

    public String allValidationsToString() {
        StringBuilder buf = new StringBuilder("[Validations ");
        getAllValidations().forEach(v -> buf.append(validationToString(v)));
        return buf.append("]").toString();
    }

    public static String validationToString(Validation validation) {
        String output = "[Validation ";

        if (validation == null) {
            return output + ">null< ]";
        }

        if (validation.getScope() != null) {
            output = output + " scope=" + validation.getScope().value();
        }
        if (validation.getId() != null) {
            output = output + " id=" + validation.getId();
        }
        if (validation.getStatus() != null) {
            output = output + " status=" + validation.getStatus().value();
        }
        if (validation.getMessage() != null) {
            output = output + " msg=" + validation.getMessage();
        }

        return output + "]";
    }
}
