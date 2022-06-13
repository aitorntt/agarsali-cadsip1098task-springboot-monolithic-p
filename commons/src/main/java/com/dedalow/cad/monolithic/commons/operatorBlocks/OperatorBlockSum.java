package com.dedalow.cad.monolithic.commons.operatorBlocks;

import com.dedalow.cad.monolithic.commons.exception.CadException;
import com.dedalow.cad.monolithic.commons.util.CADDate;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class OperatorBlockSum {

  public static Integer executeSumInteger(Object... integers) throws CadException {
    Integer result = 0;
    if (integers.length < 2) {
      throw new CadException("The input parameters must be at least 2");
    }
    try {
      for (int i = 0; i < integers.length; ++i) {
        if (Objects.isNull(integers[i])) {
          throw new CadException("One of the elements is null");
        }
        result += Integer.valueOf(integers[i].toString());
      }
    } catch (Exception e) {
      throw new CadException("One of the elements is not correct: " + e.getMessage());
    }
    return result;
  }

  public static Long executeSumLong(Object... longs) throws CadException {
    Long result = 0L;
    if (longs.length < 2) {
      throw new CadException("The input parameters must be at least 2");
    }
    try {
      for (int i = 0; i < longs.length; ++i) {
        if (Objects.isNull(longs[i])) {
          throw new CadException("One of the elements is null");
        }
        result += Long.valueOf(longs[i].toString());
      }
    } catch (Exception e) {
      throw new CadException("One of the elements is not correct: " + e.getMessage());
    }
    return result;
  }

  public static BigDecimal executeSumDecimal(Object... decimals) throws CadException {
    BigDecimal result = new BigDecimal(0);
    if (decimals.length < 2) {
      throw new CadException("The input parameters must be at least 2");
    }
    try {
      for (int i = 0; i < decimals.length; ++i) {
        if (Objects.isNull(decimals[i])) {
          throw new CadException("One of the elements is null");
        }
        result = result.add(new BigDecimal(decimals[i].toString()));
      }
    } catch (Exception e) {
      throw new CadException("One of the elements is not correct: " + e.toString());
    }
    return result.setScale(2, RoundingMode.CEILING);
  }

  public static String executeSumString(Object... strings) throws CadException {
    StringBuilder result = new StringBuilder();
    if (strings.length < 2) {
      throw new CadException("The input parameters must be at least 2");
    }
    try {
      for (int i = 0; i < strings.length; ++i) {
        if (Objects.isNull(strings[i])) {
          throw new CadException("One of the elements is null");
        }
        result.append(String.valueOf(strings[i]));
      }
    } catch (Exception e) {
      throw new CadException("One of the elements is not correct: " + e.getMessage());
    }
    return result.toString();
  }

  public static String executeSumLongText(Object... longtexts) throws CadException {
    return executeSumString(longtexts);
  }

  public static Boolean executeSumBoolean(Object... booleans) throws CadException {
    Boolean result = false;
    if (booleans.length < 2) {
      throw new CadException("The input parameters must be at least 2");
    }
    try {
      for (int i = 0; i < booleans.length; ++i) {
        if (Objects.isNull(booleans[i])) {
          throw new CadException("One of the elements is null");
        }
        result = result || Boolean.valueOf(booleans[i].toString());
      }
    } catch (Exception e) {
      throw new CadException("One of the elements is not correct: " + e.getMessage());
    }
    return result;
  }

  public static List executeSumList(Object... lists) throws CadException {
    List result = new ArrayList<>();
    if (lists.length < 2) {
      throw new CadException("The input parameters must be at least 2");
    }
    try {
      for (int i = 0; i < lists.length; ++i) {
        if (Objects.isNull(lists[i])) {
          throw new CadException("One of the elements is null");
        }
        result.addAll((List) lists[i]);
      }
    } catch (Exception e) {
      throw new CadException("One of the elements is not correct: " + e.getMessage());
    }
    return result;
  }

  public static CADDate executeSumDate(Object... list) throws CadException {
    return executeSumDateOrDateTime(false, list);
  }

  public static CADDate executeSumDateTime(Object... list) throws CadException {
    return executeSumDateOrDateTime(true, list);
  }

  public static CADDate executeSumDateOrDateTime(boolean isDatetime, Object... list)
      throws CadException {
    CADDate date = null;
    Integer quantity = null;
    String datePart = null;
    if (list.length != 3) {
      throw new CadException("The input parameters must be exactly 3");
    }

    for (int i = 0; i < list.length; ++i) {
      if (Objects.isNull(list[i])) {
        throw new CadException("One of the elements is null");
      }
      if (list[i] instanceof CADDate) {
        date = (CADDate) list[i];
      } else if (list[i] instanceof Date || list[i] instanceof java.sql.Date) {
        date = new CADDate(false, list[i]);
      } else if (list[i] instanceof java.sql.Timestamp) {
        date = new CADDate(true, list[i]);
      } else {
        if (list[i] instanceof Integer) {
          quantity = (Integer) list[i];
        } else {
          if (list[i] instanceof String) {
            datePart = (String) list[i];
          } else {
            throw new CadException("One of the elements is not correct");
          }
        }
      }
    }

    Calendar cal = Calendar.getInstance();
    cal.setTime(date.getUtilDate());
    int dateConstant = 0;

    switch (datePart) {
      case "Year":
        dateConstant = Calendar.YEAR;
        break;
      case "Month":
        dateConstant = Calendar.MONTH;
        break;
      case "Day":
        dateConstant = Calendar.DATE;
        break;
      case "Hour":
        dateConstant = Calendar.HOUR;
        break;
      case "Minute":
        dateConstant = Calendar.MINUTE;
        break;
      case "Second":
        dateConstant = Calendar.SECOND;
        break;
      default:
        throw new CadException("One of the elements is not correct: " + datePart);
    }
    cal.add(dateConstant, quantity);
    CADDate result = new CADDate(isDatetime, cal.getTime());
    return result;
  }
}
