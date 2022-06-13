package com.dedalow.cad.monolithic.commons.services.impl;

import com.dedalow.cad.monolithic.commons.exception.ExceptionResponse;
import com.dedalow.cad.monolithic.commons.services.ConfigService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConfigServiceImpl implements ConfigService {

  @Autowired MessageSource messageSource;

  @Override
  public ExceptionResponse selectedException(Exception exc, String type) {
    String errorCode = "";
    String description = "";
    String source = "";
    Locale locale = LocaleContextHolder.getLocale();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String date = formatter.format(new Date());

    if (Objects.isNull(exc.getMessage())) {
      if (exc instanceof IllegalArgumentException) {
        errorCode = "ERR-" + type + "-002";
      } else {
        errorCode = "ERR-" + type + "-001";
      }
    } else {
      if (Objects.isNull(exc.getCause())) {
        if (exc instanceof IllegalArgumentException) {
          errorCode = "ERR-GEN-001";
        } else if (exc instanceof ClassCastException) {
          errorCode = "ERR-GEN-002";
        } else if (exc instanceof NullPointerException) {
          errorCode = "ERR-GEN-003";
        } else if (exc instanceof ParseException) {
          errorCode = "ERR-GEN-004";
        } else if (exc.getMessage().startsWith("ERR")) {
          errorCode = exc.getMessage();
        } else {
          log.debug("No have error code");
        }
      } else {
        if (exc instanceof IllegalArgumentException) {
          errorCode = "ERR-" + type + "-002";
        }
        description = exc.getMessage();
        source = exc.getCause().getMessage();
      }
    }
    if (errorCode.startsWith("ERR")) {
      source = messageSource.getMessage("exception." + errorCode + ".source", null, locale);
      description =
          messageSource.getMessage("exception." + errorCode + ".description", null, locale);
      errorCode = messageSource.getMessage("exception." + errorCode + ".code", null, locale);
    } else {
      description = exc.getMessage();
    }
    return new ExceptionResponse(errorCode, description, date, source);
  }
}
