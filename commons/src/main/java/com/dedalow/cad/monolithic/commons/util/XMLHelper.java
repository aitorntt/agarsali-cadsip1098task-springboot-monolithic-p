package com.dedalow.cad.monolithic.commons.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

public class XMLHelper {

  public static String getResponseIfXML(JsonObject jso, Object response) {

    String xmlResp = "";
    String xmlVal = "";
    String xmlFilter = "";

    if (jso.has("validation")) {
      xmlVal = "<validation>" + jso.get("validation") + "</validation>";
    }

    if (jso.has("filterValidation")) {
      xmlFilter = "<filterValidation>" + jso.get("filterValidation") + "</filterValidation>";
    }

    if (response.getClass().getPackage().equals(Package.getPackage("com.google.gson"))) {

      JsonElement jse = (JsonElement) response;

      if (jse.isJsonArray()) {
        xmlResp =
            "<responseBody>"
                + XML.toString(new JSONArray(jse.toString()), "item")
                + "</responseBody>";
      } else {
        xmlResp = XML.toString(new JSONObject(jse.toString()));
      }

    } else {
      xmlResp = "<responseBody>" + response + "</responseBody>";
    }

    xmlResp = xmlResp.replaceAll(" ", "_");
    xmlResp = xmlResp.replaceAll("'", "");
    xmlResp = xmlResp.replaceAll("+", "");
    xmlResp = xmlResp.replaceAll("(", "");
    xmlResp = xmlResp.replaceAll(")", "");
    xmlResp = xmlResp.replaceAll("\"", "");

    return "<response>" + xmlVal + xmlFilter + xmlResp + "</response>";
  }

  public static String getErrorsIfXML(JsonObject jso) {

    String xmlCode = "";
    String xmlError = "";

    xmlCode = "<responseCode>" + jso.get("responseCode") + "</responseCode>";
    xmlError = "<responseError>" + jso.get("responseError") + "</responseError>";

    return "<response>" + xmlCode + xmlError + "</response>";
  }
}
