package com.dedalow.cad.monolithic.commons.util;

import com.dedalow.cad.monolithic.commons.exception.CadException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Objects;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperUtil {

  private static ObjectMapper objectMapper;

  public static ObjectMapper getMapper() {
    if (Objects.nonNull(objectMapper)) {
      return objectMapper;
    } else {
      ObjectMapperUtil obj = new ObjectMapperUtil();
      objectMapper = obj.getObjectMapper();
      return objectMapper;
    }
  }

  @Bean(name = "objectMapper")
  ObjectMapper getObjectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper;
  }

  public static <T> T convertValue(Object fromObject, TypeReference<T> toValue) {
    return getMapper().convertValue(fromObject, toValue);
  }

  /** Deserialize the given JSON content provided as a String into the given class. */
  public static <T> T readValue(final String fromJson, final Class<T> toValue) throws CadException {
    try {
      return getMapper().readValue(fromJson, toValue);
    } catch (final JsonProcessingException e) {
      throw new CadException("Error deserializing JSON");
    }
  }
}
