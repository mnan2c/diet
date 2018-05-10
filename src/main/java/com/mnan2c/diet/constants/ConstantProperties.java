package com.mnan2c.diet.constants;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantProperties implements InitializingBean {

  @Value("${aliyun.oss.endpoint}")
  private String aliyunOSSEndpoint;

  @Value("${aliyun.oss.keyid}")
  private String aliyunOSSKeyId;

  @Value("${aliyun.oss.keysecret}")
  private String aliyunOSSKeySecret;

  @Value("${aliyun.oss.filehost}")
  private String aliyunOSSFileHost;

  @Value("${aliyun.oss.bucketname}")
  private String aliyunOSSBucketName;

  public static String OSS_END_POINT;
  public static String OSS_ACCESS_KEY_ID;
  public static String OSS_ACCESS_KEY_SECRET;
  public static String OSS_BUCKET_NAME;
  public static String OSS_FILE_HOST;

  @Override
  public void afterPropertiesSet() throws Exception {
    OSS_END_POINT = aliyunOSSEndpoint;
    OSS_ACCESS_KEY_ID = aliyunOSSKeyId;
    OSS_ACCESS_KEY_SECRET = aliyunOSSKeySecret;
    OSS_FILE_HOST = aliyunOSSFileHost;
    OSS_BUCKET_NAME = aliyunOSSBucketName;
  }
}
