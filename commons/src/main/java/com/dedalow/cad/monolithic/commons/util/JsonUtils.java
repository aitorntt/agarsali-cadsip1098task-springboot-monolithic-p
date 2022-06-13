package com.dedalow.cad.monolithic.commons.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.util.StringUtils;

public class JsonUtils {

  public static JSONObject toJSON(Object data, String[] fields) {
    JSONObject result = new JSONObject();
    if (data instanceof Object[]) {
      for (int i = 0; i < ((Object[]) fields).length; i++) {
        result.put(fields[i], ((Object[]) data)[i]);
      }
    } else if (data instanceof LinkedHashMap) {
      for (int i = 0; i < ((Object[]) fields).length; i++) {
        result.put(fields[i], ((LinkedHashMap) data).get(fields[i]));
      }
    } else if (data instanceof JSONObject) {
      result = (JSONObject) data;
    } else {
      try {
        ObjectMapper mapper = new ObjectMapper();
        if (data instanceof CADDate) {
          result = new JSONObject(mapper.writeValueAsString(data.toString()));
        } else {
          String stringData = mapper.writeValueAsString(data);
          if ("{\"empty\":false}".equals(stringData)) {
            throw new JSONException("Wrong response");
          }
          result = new JSONObject(stringData);
        }
      } catch (JSONException | JsonProcessingException e) {
        if (fields.length > 0) {
          result.put(fields[0], data);
        }
      }
    }

    return result;
  }

  public static JSONArray toJSON(List data, String[] fields) {
    JSONArray result = new JSONArray();
    for (Object aux : data) {
      JSONObject param = new JSONObject();
      if (fields.length == 0) {
        result.put(aux);
      } else {
        for (int i = 0; i < ((Object[]) fields).length; i++) {
          if (aux instanceof Object[]) {
            param.put(fields[i], ((Object[]) aux)[i]);
          } else if (aux instanceof LinkedHashMap) {
            param.put(fields[i], ((LinkedHashMap) aux).get(fields[i]));
          } else {
            param.put(fields[i], aux);
          }
        }
        result.put(param);
      }
    }

    return result;
  }

  public static JSONObject modelToJson(Object data) throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    return new JSONObject(mapper.writeValueAsString(data));
  }

  public static Map<String, Object> castToGson(JSONObject encapsulatedJSONResponse) {
    return new Gson()
        .fromJson(
            encapsulatedJSONResponse.toString(),
            new TypeToken<HashMap<String, Object>>() {}.getType());
  }

  public static List toList(Object jsonObject) throws Exception {
    ArrayList listdata = new ArrayList();
    JSONArray jArray = (JSONArray) jsonObject;
    if (jArray != null) {
      for (int i = 0; i < jArray.length(); i++) {
        listdata.add(jArray.get(i));
      }
    }
    return listdata;
  }

  public static JSONArray modelToJson(List<?> data) throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    return new JSONArray(mapper.writeValueAsString(data));
  }

  public static Map<String, Object> buildResponse(
      List<Object> outputObject, String[] fields, String encapsulateName) {

    JSONObject encapsulatedJSONResponse = new JSONObject();
    JSONArray outputObjectJSON = toJSON(outputObject, fields);

    if ("{}".equals(outputObjectJSON.toString())) {
      encapsulatedJSONResponse.put(encapsulateName, outputObject);
    } else if (!StringUtils.isEmpty(encapsulateName)) {
      encapsulatedJSONResponse.put(encapsulateName, outputObjectJSON);
    }

    return castToGson(encapsulatedJSONResponse);
  }

  public static Map<String, Object> buildResponse(
      Object outputObject, String[] fields, String encapsulateName) {

    JSONObject encapsulatedJSONResponse = new JSONObject();
    JSONObject outputObjectJSON = toJSON(outputObject, fields);

    if (StringUtils.isEmpty(encapsulateName) && !"{}".equals(outputObjectJSON.toString())) {
      encapsulatedJSONResponse = outputObjectJSON;
    } else if ("{}".equals(outputObjectJSON.toString())) {
      encapsulatedJSONResponse.put(encapsulateName, outputObject);
    } else {
      encapsulatedJSONResponse.put(encapsulateName, outputObjectJSON);
    }

    return castToGson(encapsulatedJSONResponse);
  }

  public static List<Object> toListJSON(List data, String[] fields) {
    List<Object> result = new LinkedList<>();
    for (Object aux : data) {
      JSONObject param = new JSONObject();
      if (fields.length != 0) {
        for (int i = 0; i < ((Object[]) fields).length; i++) {
          if (aux instanceof Object[]) {
            param.put(fields[i], ((Object[]) aux)[i]);
          } else {
            param.put(fields[i], aux);
          }
        }
        result.add(param);
      } else {
        result.add(aux);
      }
    }
    return result;
  }
}
