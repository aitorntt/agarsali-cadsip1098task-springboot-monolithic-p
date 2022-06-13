package com.dedalow.cad.monolithic.commons.operatorBlocks;

import com.dedalow.cad.monolithic.commons.exception.CadException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class OperatorBlockMultiplication {

  public static Integer executeMultiplicationInteger(Object... integers) throws CadException {
    Integer result = 1;
    if (integers.length < 2) {
      throw new CadException("The input parameters must be at least 2");
    }
    try {
      for (int i = 0; i < integers.length; ++i) {
        if (Objects.isNull(integers[i])) {
          throw new CadException("One of the elements is null");
        }
        result *= Integer.valueOf(integers[i].toString());
      }
    } catch (Exception e) {
      throw new CadException("One of the elements is not correct: " + e.getMessage());
    }
    return result;
  }

  public static Long executeMultiplicationLong(Object... longs) throws CadException {
    Long result = 1L;
    if (longs.length < 2) {
      throw new CadException("The input parameters must be at least 2");
    }
    try {
      for (int i = 0; i < longs.length; ++i) {
        if (Objects.isNull(longs[i])) {
          throw new CadException("One of the elements is null");
        }
        result *= Long.valueOf(longs[i].toString());
      }
    } catch (Exception e) {
      throw new CadException("One of the elements is not correct: " + e.getMessage());
    }
    return result;
  }

  public static BigDecimal executeMultiplicationDecimal(Object... decimals) throws CadException {
    BigDecimal result = new BigDecimal(1);
    if (decimals.length < 2) {
      throw new CadException("The input parameters must be at least 2");
    }
    try {
      for (int i = 0; i < decimals.length; ++i) {
        if (Objects.isNull(decimals[i])) {
          throw new CadException("One of the elements is null");
        }
        result = result.multiply(new BigDecimal(decimals[i].toString()));
      }
    } catch (Exception e) {
      throw new CadException("One of the elements is not correct: " + e.toString());
    }
    return result.setScale(2, RoundingMode.CEILING);
  }

  public static Boolean executeMultiplicationBoolean(Object... booleans) throws CadException {
    Boolean result = true;
    if (booleans.length < 2) {
      throw new CadException("The input parameters must be at least 2");
    }
    try {
      for (int i = 0; i < booleans.length; ++i) {
        if (Objects.isNull(booleans[i])) {
          throw new CadException("One of the elements is null");
        }
        result = result && Boolean.valueOf(booleans[i].toString());
      }
    } catch (Exception e) {
      throw new CadException("One of the elements is not correct: " + e.getMessage());
    }
    return result;
  }
}
