package com.dedalow.cad.monolithic.commons.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang3.math.NumberUtils;

public class CADDate {

  private Date caddate;
  private boolean isDateTime;

  public CADDate(Object date) {
    this(false, date);
  }

  public CADDate(boolean isDateTime, Object date) {
    if (date instanceof java.sql.Date) {
      setDateTime(isDateTime);
      caddate = new Date(((java.sql.Date) date).getTime());
    } else if (date instanceof java.util.Date) {
      setDateTime(isDateTime);
      caddate = new Date(((java.util.Date) date).getTime());
    } else if (date instanceof java.sql.Timestamp) {
      setDateTime(isDateTime);
      caddate = new Date(((java.sql.Timestamp) date).getTime());
    } else if (date instanceof Long) {
      setDateTime(isDateTime);
      caddate = new Date((Long) date);
    } else if (date instanceof CADDate) {
      setDateTime(((CADDate) date).isDateTime());
      caddate = ((CADDate) date).getUtilDate();
    } else if (date instanceof String) {
      try {
        setDateTime(isDateTime);
        if (NumberUtils.isNumber(date.toString())) {
          caddate = new Date(Long.parseLong(date.toString()));
        } else {
          parseString(isDateTime, date.toString());
        }
      } catch (ParseException e) {
        setDateTime(false);
        caddate = new Date();
      }
    } else {
      setDateTime(false);
      caddate = new Date();
    }
  }

  public CADDate(boolean isDateTime) {
    setDateTime(isDateTime);
    caddate = new Date();
  }

  private void parseString(boolean isDateTime, String stringDate) throws ParseException {
    if (!isDateTime) {
      caddate = new SimpleDateFormat("yyyy-MM-dd").parse(stringDate);
    } else {
      try {
        caddate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(stringDate);
      } catch (Exception e) {
        try {
          caddate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(stringDate);
        } catch (Exception ea) {
          try {
            caddate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(stringDate);
          } catch (Exception ex) {
            try {
              caddate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(stringDate);
            } catch (Exception ee) {
              caddate = new Date(Long.parseLong(stringDate));
            }
          }
        }
      }
    }
  }

  public CADDate(boolean isDateTime, Timestamp timestamp) throws ParseException {
    setDateTime(isDateTime);
    caddate = new Date(timestamp.getTime());
  }

  public CADDate(boolean isDateTime, long timestampLong) throws ParseException {
    setDateTime(isDateTime);
    Timestamp timestamp = new Timestamp(timestampLong);
    caddate = new Date(timestamp.getTime());
  }

  public java.sql.Date getSqlDate() {
    return new java.sql.Date(caddate.getTime());
  }

  public Timestamp getTimestamp() {
    return new Timestamp(caddate.getTime());
  }

  public Date getUtilDate() {
    return new Date(caddate.getTime());
  }

  public long getTime() {
    return caddate.getTime();
  }

  public String getDateString() {
    return new SimpleDateFormat("yyyy-MM-dd").format(caddate);
  }

  public String getDateTimeString() {
    return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(caddate);
  }

  public String getHumanDateTimeString() {
    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(caddate);
  }

  public boolean isDateTime() {
    return isDateTime;
  }

  public void setDateTime(boolean isDateTime) {
    this.isDateTime = isDateTime;
  }

  @Override
  public String toString() {
    return isDateTime ? getDateTimeString() : getDateString();
  }
}
