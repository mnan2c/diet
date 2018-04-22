package com.mnan2c.diet.domain.constants;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DietConstants {

  public static final String ISOStringFormatter = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

  public static final DateTimeFormatter ISOFormatter =
      DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

  // session key
  public static final String SESSION_USER = "user";
}
