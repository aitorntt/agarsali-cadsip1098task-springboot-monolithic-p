package com.dedalow.cad.monolithic.commons.util;

import java.util.List;
import org.everit.json.schema.ValidationException;

public interface ISchemaValidator {

  boolean validate(String schemaPath, String source);

  boolean validate(
      String schemaPath, String source, List<ValidationException> validationExceptions);
}
