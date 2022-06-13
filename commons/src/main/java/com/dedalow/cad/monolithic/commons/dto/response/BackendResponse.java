package com.dedalow.cad.monolithic.commons.dto.response;

import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BackendResponse<T> implements Serializable {

  private Integer statusCode;
  private T body;

  private boolean isOk;
  private String message;
}
