package com.dedalow.cad.monolithic.commons.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class DateDeserializer extends StdDeserializer<Date> {
  private static final long serialVersionUID = 1L;

  private static final String[] DATETIME_PATTERNS =
      new String[] {"yyyy-MM-dd'T'HH:mm:ss'Z'", "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd HH:mm:ss.S"};

  public DateDeserializer() {
    this(null);
  }

  public DateDeserializer(final Class<?> vc) {
    super(vc);
  }

  @Override
  public Date deserialize(final JsonParser jp, final DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    final JsonNode node = jp.getCodec().readTree(jp);
    final String date = node.textValue();

    for (final String pattern : DATETIME_PATTERNS) {
      try {
        return new SimpleDateFormat(pattern).parse(date);
      } catch (final ParseException e) {
      }
    }
    if (date.length() <= 10) {
      try {
        return new SimpleDateFormat("yyyy-MM-dd").parse(date);
      } catch (final ParseException e) {
      }
    }
    try {
      return new Date(Long.parseLong(date));
    } catch (final NumberFormatException e) {
    }

    throw new JsonParseException(
        jp,
        "Unparseable date: \""
            + date
            + "\". Supported formats: Long or "
            + Arrays.toString(DATETIME_PATTERNS));
  }
}
