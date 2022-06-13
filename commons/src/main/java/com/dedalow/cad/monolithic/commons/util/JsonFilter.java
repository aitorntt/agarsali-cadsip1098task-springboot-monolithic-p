package com.dedalow.cad.monolithic.commons.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.io.IOUtils;

public class JsonFilter {

  public JsonElement filter(String responseJson, String filterPath) {

    JsonParser parser = new JsonParser();
    InputStream inputStream = getClass().getResourceAsStream(filterPath);
    StringWriter sw = new StringWriter();

    JsonElement responseElement = parser.parse(responseJson);

    if (inputStream != null) {
      try {
        IOUtils.copy(inputStream, sw, Charset.defaultCharset().toString());
      } catch (IOException e) {
        e.printStackTrace();
      }

      JsonElement filterElement = parser.parse(sw.toString());

      if (responseElement.isJsonObject()) {

        JsonObject responseObject = responseElement.getAsJsonObject();
        JsonObject filterObject = filterElement.getAsJsonObject();

        if (!filterObject.keySet().isEmpty()) {
          recursiveFilter(responseObject, filterObject, null);
          return filterObject;
        } else {
          return responseObject;
        }

      } else {

        JsonObject responseObject = parser.parse("{}").getAsJsonObject();
        responseObject.add("bodyArray", responseElement);
        JsonObject filterObject = parser.parse("{}").getAsJsonObject();
        filterObject.add("bodyArray", filterElement);
        recursiveFilter(responseObject, filterObject, null);

        return filterObject.get("bodyArray");
      }
    } else {
      if (responseElement.isJsonObject()) {
        return responseElement.getAsJsonObject();
      } else {
        JsonObject responseObject = parser.parse("{}").getAsJsonObject();
        responseObject.add("bodyArray", responseElement);
        return responseObject;
      }
    }
  }

  public JsonElement filter(String responseJson, String filterPath, String arrayKey) {

    JsonParser parser = new JsonParser();
    InputStream inputStream = getClass().getResourceAsStream(filterPath);
    StringWriter sw = new StringWriter();

    JsonElement responseElement = parser.parse(responseJson);

    if (inputStream != null) {
      try {
        IOUtils.copy(inputStream, sw, Charset.defaultCharset().toString());
      } catch (IOException e) {
        e.printStackTrace();
      }

      JsonElement filterElement = parser.parse(sw.toString());

      if (responseElement.isJsonObject()) {

        JsonObject responseObject = responseElement.getAsJsonObject();
        JsonObject filterObject = filterElement.getAsJsonObject();

        if (!filterObject.keySet().isEmpty()) {
          if (filterObject.keySet().size() > 1) {
            recursiveFilter(responseObject, filterObject, null);
            return filterObject;
          } else {
            String keyName = filterObject.keySet().toArray()[0].toString();
            if (responseObject.get(keyName) != null) {
              recursiveFilter(responseObject, filterObject, null);
              return filterObject;
            } else {
              filterObject = parser.parse(filterElement.toString()).getAsJsonObject();

              responseObject = parser.parse("{}").getAsJsonObject();
              responseObject.add(keyName, responseElement);
              recursiveFilter(responseObject, filterObject, null);
              if ("{}".equals(filterObject.get(keyName).toString())) {
                return parser.parse(responseObject.toString());
              }
              return filterObject;
            }
          }

        } else {
          return responseObject;
        }

      } else {

        JsonObject responseObject = parser.parse("{}").getAsJsonObject();
        responseObject.add(arrayKey != null ? arrayKey : "bodyArray", responseElement);

        JsonObject filterObject = parser.parse(filterElement.toString()).getAsJsonObject();
        recursiveFilter(responseObject, filterObject, null);

        return filterObject;
      }
    } else {
      if (responseElement.isJsonObject()) {
        return responseElement.getAsJsonObject();
      } else {
        JsonObject responseObject = parser.parse("{}").getAsJsonObject();
        responseObject.add(arrayKey != null ? arrayKey : "bodyArray", responseElement);
        return responseObject;
      }
    }
  }

  public static void recursiveFilter(
      JsonObject responseJso, JsonObject filterJso, Set<String> keys) {

    JsonParser parser = new JsonParser();
    Set<String> tempKeys = new HashSet<String>();

    if (keys == null) {
      keys = filterJso.keySet();
    }
    tempKeys.addAll(keys);

    if (!keys.isEmpty()) {

      List<String> keysList = new ArrayList<String>(tempKeys);
      String key = keysList.get(0);
      tempKeys.remove(key);

      if (responseJso.has(key)) {

        if (filterJso.get(key).isJsonObject()) {

          JsonElement filterJse = filterJso.get(key);
          JsonElement jse = responseJso.get(key);
          Set<String> auxKeys = filterJse.getAsJsonObject().keySet();
          recursiveFilter(jse.getAsJsonObject(), filterJse.getAsJsonObject(), auxKeys);
          if (!tempKeys.isEmpty()) {
            recursiveFilter(responseJso, filterJso, tempKeys);
          }

        } else if (filterJso.get(key).isJsonArray()) {

          JsonArray filterJsa = filterJso.get(key).getAsJsonArray();

          if (filterJsa.size() < 1) {

            filterJso.add(key, responseJso.get(key));
            recursiveFilter(responseJso, filterJso, tempKeys);

          } else {

            if (!responseJso.get(key).getAsJsonArray().isJsonNull()
                && responseJso.get(key).getAsJsonArray().size() > 0) {
              JsonElement auxNewJsaElement = parser.parse(filterJsa.get(0).toString());
              filterJsa.add(auxNewJsaElement);
              recursiveFilter(
                  responseJso.get(key).getAsJsonArray().get(0).getAsJsonObject(),
                  filterJsa.get(filterJsa.size() - 1).getAsJsonObject(),
                  filterJsa.get(filterJsa.size() - 1).getAsJsonObject().keySet());
              responseJso.get(key).getAsJsonArray().remove(0);
            }

            if (responseJso.get(key).getAsJsonArray().size() > 0) {

              Set<String> arrayKeys = new HashSet<String>();
              arrayKeys.add(key);
              arrayKeys.addAll(tempKeys);
              recursiveFilter(responseJso, filterJso, arrayKeys);

            } else {

              filterJsa.remove(0);
              recursiveFilter(responseJso, filterJso, tempKeys);
            }
          }

        } else {

          filterJso.add(key, responseJso.get(key));
          recursiveFilter(responseJso, filterJso, tempKeys);
        }

      } else {
        filterJso.getAsJsonObject().remove(key);
      }
    }
  }
}
