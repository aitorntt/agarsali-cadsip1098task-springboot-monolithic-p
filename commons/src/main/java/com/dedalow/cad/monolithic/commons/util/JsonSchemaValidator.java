package com.dedalow.cad.monolithic.commons.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JsonSchemaValidator implements ISchemaValidator {

  @Override
  public boolean validate(String schemaPath, String source) {
    return validate(schemaPath, source, new ArrayList<ValidationException>());
  }

  @Override
  public boolean validate(
      String schemaPath, String source, List<ValidationException> validationExceptions) {
    Schema schema = null;
    JsonParser parser = new JsonParser();
    try {
      InputStream inputStream = getClass().getResourceAsStream(schemaPath);
      if (inputStream != null) {
        JSONObject rawSchema = new JSONObject(new JSONTokener(inputStream));
        JsonElement jse = parser.parse(source);
        schema = SchemaLoader.load(rawSchema);
        if (jse.isJsonObject()) {
          JSONObject jsonObject = new JSONObject(source);
          schema.validate(jsonObject);
        } else if (jse.isJsonArray()) {
          JSONArray jsonArray = new JSONArray(source);
          schema.validate(jsonArray);
        } else {
          validationExceptions.add(
              new ValidationException(
                  schema, "Received response is not a valid JSONObject or JSONArray"));
          return false;
        }
      }
    } catch (ValidationException e) {
      validationExceptions.add(e);
      return false;
    } catch (Exception e) {
      validationExceptions.add(new ValidationException(schema, e.getMessage()));
      return false;
    }

    return true;
  }
}
