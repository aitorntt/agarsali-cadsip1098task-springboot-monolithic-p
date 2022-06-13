package com.dedalow.cad.monolithic.commons.operatorBlocks;

import com.dedalow.cad.monolithic.commons.exception.CadException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class OperatorBlockMod {

  public static Integer executeModInteger(Object... integers) throws CadException {
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
        result %= Integer.valueOf(integers[i].toString());
      }
    } catch (Exception e) {
      throw new CadException("One of the elements is not correct: " + e.getMessage());
    }
    return result;
  }

  public static Long executeModLong(Object... longs) throws CadException {
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
        result %= Long.valueOf(longs[i].toString());
      }
    } catch (Exception e) {
      throw new CadException("One of the elements is not correct: " + e.getMessage());
    }
    return result;
  }

  public static BigDecimal executeModDecimal(Object... decimals) throws CadException {
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
        result = result.remainder(new BigDecimal(decimals[i].toString()));
      }
    } catch (Exception e) {
      throw new CadException("One of the elements is not correct: " + e.toString());
    }
    return result.setScale(2, RoundingMode.CEILING);
  }
}
