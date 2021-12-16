package com.example.employees.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public final class DateUtil {

  public static LocalDate parseFrom(String input) {
    try {
      return LocalDate.parse(input, DateTimeFormatter.ISO_DATE);
    } catch (DateTimeParseException e) {
      // wrong format, try next
    }
    try {
      return LocalDate.parse(input, DateTimeFormatter.BASIC_ISO_DATE);
    } catch (DateTimeParseException e) {
      // wrong format, try next
    }
    try {
      return LocalDate.parse(input, DateTimeFormatter.ISO_LOCAL_DATE);
    } catch (DateTimeParseException e) {
      // wrong format, try next
    }
    try {
      return LocalDate.parse(input, DateTimeFormatter.ISO_ORDINAL_DATE);
    } catch (DateTimeParseException e) {
      // wrong format, try next
    }
    // If it is not this, then it is really a wrong format
    return LocalDate.parse(input, DateTimeFormatter.ISO_WEEK_DATE);
  }

  public static LocalDate earliest(LocalDate d1, LocalDate d2) {
    if (d1.isBefore(d2)) {
      return d1;
    } else {
      return d2;
    }
  }

  public static LocalDate latest(LocalDate d1, LocalDate d2) {
    if (d1.isAfter(d2)) {
      return d1;
    } else {
      return d2;
    }
  }
}
