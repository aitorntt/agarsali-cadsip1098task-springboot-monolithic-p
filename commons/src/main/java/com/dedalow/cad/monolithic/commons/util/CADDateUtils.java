package com.dedalow.cad.monolithic.commons.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class CADDateUtils {

  private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
  private static final SimpleDateFormat SIMPLE_DATETIME_FORMAT =
      new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

  public static Date getDate(boolean isDateTime, String stringDate) {

    try {
      return (new CADDate(isDateTime, stringDate)).getUtilDate();
    } catch (Exception e) {
      return new Date(Long.parseLong(stringDate));
    }
  }

  public static String parseDatetime(final Date date) {
    return parseDate(date, SIMPLE_DATETIME_FORMAT);
  }

  public static String parseDate(final Date date) {
    return parseDate(date, SIMPLE_DATE_FORMAT);
  }

  private static String parseDate(final Date date, final SimpleDateFormat FORMAT) {
    if (Objects.isNull(date)) {
      return null;
    }
    return FORMAT.format(date);
  }

  public static String parseDatetime(final List<Date> listDates) {
    return parseDate(listDates, SIMPLE_DATETIME_FORMAT);
  }

  public static String parseDate(final List<Date> listDates) {
    return parseDate(listDates, SIMPLE_DATE_FORMAT);
  }

  private static String parseDate(final List<Date> listDates, final SimpleDateFormat FORMAT) {
    if (Objects.isNull(listDates)) {
      return null;
    }
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    boolean firstEjecution = true;
    for (Date date : listDates) {
      if (!firstEjecution) {
        sb.append(",");
      }
      firstEjecution = false;
      sb.append(FORMAT.format(date));
    }
    sb.append("]");
    return sb.toString();
  }
}
