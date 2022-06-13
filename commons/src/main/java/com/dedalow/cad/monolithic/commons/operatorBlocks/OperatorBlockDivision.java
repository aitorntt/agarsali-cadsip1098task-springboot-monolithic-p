package com.dedalow.cad.monolithic.commons.operatorBlocks;

import com.dedalow.cad.monolithic.commons.exception.CadException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class OperatorBlockDivision {

  public static Integer executeDivisionInteger(Object... integers) throws CadException {
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
        result /= Integer.valueOf(integers[i].toString());
      }
    } catch (Exception e) {
      throw new CadException("One of the elements is not correct: " + e.getMessage());
    }
    return result;
  }

  public static Long executeDivisionLong(Object... longs) throws CadException {
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
        result /= Long.valueOf(longs[i].toString());
      }
    } catch (Exception e) {
      throw new CadException("One of the elements is not correct: " + e.getMessage());
    }
    return result;
  }

  public static BigDecimal executeDivisionDecimal(Object... decimals) throws CadException {
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
        result = result.divide(new BigDecimal(decimals[i].toString()), 2, RoundingMode.CEILING);
      }
    } catch (Exception e) {
      throw new CadException("One of the elements is not correct: " + e.toString());
    }
    return result.setScale(2, RoundingMode.CEILING);
  }

  public static Boolean executeDivisionBoolean(Object... booleans) throws CadException {
    Boolean result = true;
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
        result = result && Boolean.valueOf(booleans[i].toString());
      }
    } catch (Exception e) {
      throw new CadException("One of the elements is not correct: " + e.getMessage());
    }
    return !result;
  }
}
