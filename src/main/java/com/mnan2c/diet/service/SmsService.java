package com.mnan2c.diet.service;

import java.time.ZonedDateTime;
import java.util.Random;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

@Service
public class SmsService {
  private static final String ACCESS_KEY_ID = "LTAIZQaEAP0eiTPf";
  private static final String ACCESS_KEY_SECRET = "cbCCWzVIfZTtT1PuVd8X4JndUa36GZ";
  private static final String SIGN_NAME = "小常的注册验证";
  private static final String TEMPLATE_CODE = "SMS_133055072";
  // 产品名称:云通信短信API产品,开发者无需替换
  static final String product = "Dysmsapi";
  // 产品域名,开发者无需替换
  static final String domain = "dysmsapi.aliyuncs.com";

  @Inject
  private HttpSession session;

  public SendSmsResponse sendSms(String cellphoneNumber) throws ClientException {

    // 可自助调整超时时间
    System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
    System.setProperty("sun.net.client.defaultReadTimeout", "10000");

    // 初始化acsClient,暂不支持region化
    IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", ACCESS_KEY_ID, ACCESS_KEY_SECRET);
    DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
    IAcsClient acsClient = new DefaultAcsClient(profile);

    // 组装请求对象-具体描述见控制台-文档部分内容
    SendSmsRequest request = new SendSmsRequest();
    // 必填:待发送手机号
    request.setPhoneNumbers(cellphoneNumber);
    // 必填:短信签名-可在短信控制台中找到
    request.setSignName(SIGN_NAME);
    // 必填:短信模板-可在短信控制台中找到
    request.setTemplateCode(TEMPLATE_CODE);
    // 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
    String verifyCode = genVerifyCode(4);
    request.setTemplateParam("{\"code\":\"" + verifyCode + "\"}");
    session.setAttribute("cellphoneNumber", cellphoneNumber);
    session.setAttribute("verifyCode", verifyCode);
    session.setAttribute("createdTime", ZonedDateTime.now());
    // 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
    // request.setOutId("yourOutId");

    // hint 此处可能会抛出异常，注意catch
    SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

    return sendSmsResponse;
  }

  public static String genVerifyCode(int digits) {
    StringBuilder sBuilder = new StringBuilder();
    for (int i = 0; i < digits; i++) {
      sBuilder.append(new Random().nextInt(10));
    }
    return sBuilder.toString();
  }
}
