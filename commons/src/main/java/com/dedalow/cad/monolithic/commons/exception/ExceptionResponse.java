package com.dedalow.cad.monolithic.commons.exception;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExceptionResponse {

  @JsonProperty("code")
  private String code;

  @JsonProperty("message")
  private String message;

  @JsonProperty("date")
  private String date;

  @JsonProperty("source")
  private String source;

  public ExceptionResponse(String code, String message, String date, String source) {
    super();
    this.message = message;
    this.code = code;
    this.date = date;
    this.source = source;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }
}
