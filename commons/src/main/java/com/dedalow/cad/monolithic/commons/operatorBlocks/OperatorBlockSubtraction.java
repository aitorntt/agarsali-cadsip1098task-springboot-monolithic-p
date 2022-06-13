package com.dedalow.cad.monolithic.commons.operatorBlocks;

import com.dedalow.cad.monolithic.commons.exception.CadException;
import com.dedalow.cad.monolithic.commons.util.CADDate;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class OperatorBlockSubtraction {

  public static Integer executeSubtractionInteger(Object... integers) throws CadException {
    Integer result;
    if (integers.length < 2) {
      throw new CadException("The input parameters must be at least 2");
    }
    try {
      if (Objects.isNull(integers[0])) {
        throw new CadException("One of the elements is null");
      } else {
        result = Integer.valueOf(integers[0].toString());
      }
      for (int i = 1; i < integers.length; ++i) {
        if (Objects.isNull(integers[i])) {
          throw new CadException("One of the elements is null");
        }
        result -= Integer.valueOf(integers[i].toString());
      }
    } catch (Exception e) {
      throw new CadException("One of the elements is not correct: " + e.getMessage());
    }
    return result;
  }

  public static Long executeSubtractionLong(Object... longs) throws CadException {
    Long result;
    if (longs.length < 2) {
      throw new CadException("The input parameters must be at least 2");
    }
    try {
      if (Objects.isNull(longs[0])) {
        throw new CadException("One of the elements is null");
      } else {
        result = Long.valueOf(longs[0].toString());
      }
      for (int i = 1; i < longs.length; ++i) {
        if (Objects.isNull(longs[i])) {
          throw new CadException("One of the elements is null");
        }
        result -= Long.valueOf(longs[i].toString());
      }
    } catch (Exception e) {
      throw new CadException("One of the elements is not correct: " + e.getMessage());
    }
    return result;
  }

  public static BigDecimal executeSubtractionDecimal(Object... decimals) throws CadException {
    BigDecimal result;
    if (decimals.length < 2) {
      throw new CadException("The input parameters must be at least 2");
    }
    try {
      if (Objects.isNull(decimals[0])) {
        throw new CadException("One of the elements is null");
      } else {
        result = new BigDecimal(decimals[0].toString());
      }
      for (int i = 1; i < decimals.length; ++i) {
        if (Objects.isNull(decimals[i])) {
          throw new CadException("One of the elements is null");
        }
        result = result.subtract(new BigDecimal(decimals[i].toString()));
      }
    } catch (Exception e) {
      throw new CadException("One of the elements is not correct: " + e.toString());
    }
    return result.setScale(2, RoundingMode.CEILING);
  }

  public static String executeSubtractionString(Object... strings) throws CadException {
    String result;
    if (strings.length < 2) {
      throw new CadException("The input parameters must be at least 2");
    }
    try {
      if (Objects.isNull(strings[0])) {
        throw new CadException("One of the elements is null");
      } else {
        result = String.valueOf(strings[0]);
      }
      for (int i = 1; i < strings.length; ++i) {
        if (Objects.isNull(strings[i])) {
          throw new CadException("One of the elements is null");
        }
        result = result.replace(String.valueOf(strings[i]), "");
      }
    } catch (Exception e) {
      throw new CadException("One of the elements is not correct: " + e.getMessage());
    }
    return result;
  }

  public static String executeSubtractionLongText(Object... longtexts) throws CadException {
    return executeSubtractionString(longtexts);
  }

  public static Boolean executeSubtractionBoolean(Object... booleans) throws CadException {
    Boolean result;
    if (booleans.length < 2) {
      throw new CadException("The input parameters must be at least 2");
    }
    try {
      if (Objects.isNull(booleans[0])) {
        throw new CadException("One of the elements is null");
      } else {
        result = Boolean.valueOf(booleans[0].toString());
      }
      for (int i = 1; i < booleans.length; ++i) {
        if (Objects.isNull(booleans[i])) {
          throw new CadException("One of the elements is null");
        }
        result = result || Boolean.valueOf(booleans[i].toString());
      }
    } catch (Exception e) {
      throw new CadException("One of the elements is not correct: " + e.getMessage());
    }
    return !result;
  }

  public static List executeSubtractionList(Object... lists) throws CadException {
    List result;
    if (lists.length < 2) {
      throw new CadException("The input parameters must be at least 2");
    }
    try {
      if (Objects.isNull(lists[0])) {
        throw new CadException("One of the elements is null");
      } else {
        result = (List) lists[0];
      }
      for (int i = 1; i < lists.length; ++i) {
        if (Objects.isNull(lists[i])) {
          throw new CadException("One of the elements is null");
        }
        result.removeAll((List) lists[i]);
      }
    } catch (Exception e) {
      throw new CadException("One of the elements is not correct: " + e.getMessage());
    }
    return result;
  }

  public static CADDate executeSubtractionDate(Object... list) throws CadException {
    return executeSubtractionDateOrDateTime(false, list);
  }

  public static CADDate executeSubtractionDateTime(Object... list) throws CadException {
    return executeSubtractionDateOrDateTime(true, list);
  }

  public static CADDate executeSubtractionDateOrDateTime(boolean isDatetime, Object... list)
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
    cal.add(dateConstant, -quantity);
    CADDate result = new CADDate(isDatetime, cal.getTime());
    return result;
  }
}
