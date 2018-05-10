package com.mnan2c.diet.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.activation.MimetypesFileTypeMap;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.GenericRequest;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.mnan2c.diet.constants.ConstantProperties;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AliyunOSSUtil {

  private static final String endpoint = ConstantProperties.OSS_END_POINT;
  private static final String accessKeyId = ConstantProperties.OSS_ACCESS_KEY_ID;
  private static final String accessKeySecret = ConstantProperties.OSS_ACCESS_KEY_SECRET;
  private static final String bucketName = ConstantProperties.OSS_BUCKET_NAME;
  private static final String fileHost = ConstantProperties.OSS_FILE_HOST;

  @SuppressWarnings("unused")
  public static String upload(File file) {
    log.info("=========>OSS文件上传开始：" + file.getName());

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    String dateStr = format.format(new Date());

    if (null == file) {
      return null;
    }

    OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
    try {
      // 容器不存在，就创建
      if (!ossClient.doesBucketExist(bucketName)) {
        ossClient.createBucket(bucketName);
        CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
        createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
        ossClient.createBucket(createBucketRequest);
      }
      // 创建文件路径
      String filePath = UUID.randomUUID().toString().replace("-", "") + "-" + file.getName();
      String fileUrl = fileHost + "/" + filePath;
      // 上传文件
      PutObjectResult result = ossClient.putObject(new PutObjectRequest(bucketName, fileUrl, file));
      // 设置权限 这里是公开读
      ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
      if (null != result) {
        log.info("==========>OSS文件上传成功,OSS地址：" + fileUrl);
        return fileUrl;
      }
    } catch (OSSException oe) {
      log.error(oe.getMessage());
    } catch (ClientException ce) {
      log.error(ce.getMessage());
    } finally {
      ossClient.shutdown();
    }
    return null;
  }

  public static String getFileMimeType(File file) {
    return new MimetypesFileTypeMap().getContentType(file);
  }

  public static boolean deleteFile(String fileUrl) {
    String fileName = getFileName(fileUrl); // 根据url获取fileName
    if (bucketName == null || fileName == null)
      return false;
    OSSClient ossClient = null;
    try {
      ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
      GenericRequest request = new DeleteObjectsRequest(bucketName).withKey(fileName);
      ossClient.deleteObject(request);
    } catch (Exception oe) {
      oe.printStackTrace();
      return false;
    } finally {
      ossClient.shutdown();
    }
    return true;
  }

  private static String getFileName(String fileUrl) {
    String str = "aliyuncs.com/";
    int beginIndex = fileUrl.indexOf(str);
    if (beginIndex == -1)
      return null;
    return fileUrl.substring(beginIndex + str.length());
  }
}
