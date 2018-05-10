package com.mnan2c.diet.constants;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DietConstants {

  public static final String ISOStringFormatter = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

  public static final DateTimeFormatter ISOFormatter =
      DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

  public static final DateTimeFormatter WEB_DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  // session key
  public static final String SESSION_USER = "user";

  // third party infomation
  public static final String ALIYUN_OSS_URL = "https://morgan-zmn.oss-cn-beijing.aliyuncs.com/";
}
