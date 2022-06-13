package com.dedalow.cad.monolithic.commons.services;

import com.dedalow.cad.monolithic.commons.exception.ExceptionResponse;

public interface ConfigService {

  ExceptionResponse selectedException(Exception exc, String type);
}
