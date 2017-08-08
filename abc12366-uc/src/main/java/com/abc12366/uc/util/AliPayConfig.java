package com.abc12366.uc.util;


import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;

public class AliPayConfig {
	
	private static AlipayClient alipayClient=null;
	
	//https://openapi.alipay.com/gateway.do
	private static String gatewayUrl="https://openapi.alipay.com/gateway.do";// 支付宝网关
	
	//private static String app_id="2016082000294562"; // 应用ID收款账号既是您的APPID对应支付宝账号 
	
	private static String app_id="2017080708072190";
	
	private static String merchant_private_key="MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCsbz1H8e672TZq4vOOtvclC/ZpIgiXxaMPUG7+nI/EsUrb7a0gEKqVKZ1gt+FUrwhQIYriI3hdTw6tIMv50Sn459xHk95XwA8DQtzt4vn+d5pTw9gzlbw3c7ymPZnzBnWY7oDnf1GJQOHCienqJJOifIB08bvPL59pjfIGp7pOHBP2z0uEWn89NEUAViczwfve0iSzSJod0Isll5AidjlpD2Xje+znYjOUILKoyY3rnBYtxiTJmvgMWkPi4Fxp+nVu0pNEamOF9RnJ4TMh/l3O9ocMvlg2ckakrIuqMsvR4Fi/MM8VsY/qTk3x82LFi5Nahe3zesqsqudXFcvwkSVPAgMBAAECggEBAItQcI1w/PXOH7Yp2KekUXcf/Byiv5iHuB9GV/bj3RAIQbgAhhBTBs7uyVT8G1SE+c1r3D6qc7PHjM75oXMbt7J++azm3gu9gyOaYM7dxCcA0LH+l+3Nezy+CFuzh28e9+HwSDtA1Up0HjJDpLZkiNcNiedx6tAQ5NWuNi2EU/SISLxyQvqXGgTenYiZl2GZ63bgM+qCpqmqwpkZR9jIWXCHzFHTmcv3/JFNVRFB2TCLQqPqgOjh+MUw1ZprtkkgdS+deg2mHEu5VTEr1yu4T8twRaGeC/9XfhGpvRn4iDY0sfxAySYJ+qwOxZsbRyEgc3flKw66LPdx+E8PCvgGR6kCgYEA9+if4h939oLRm4Rhz7/hS/pVRSDEwh/AYvUgnmeS7P2iC7bb+LzZHR4W2p0zms0swi4huPGS6NXaBbFwWElqGR1DSZIc/nm90goXQFqZ65kO/uZ8VEz6YdJi5gjQer9Kj5q1F8c70/Pb5bIjVJkI0GehsvBHUDuGJvYyPigHrkMCgYEAsg//jyjk0C1v4iXCIIXpAe0f2hw/SifvhUF/lF02PnUK4bsGmWnW0JxZp9u+0j6yEnxTyWB4pSyjyOw9YxgL0RgapiDdW3JPa8kf8atCzEjsoxg0XRHE6mtfyq0mJDEpPN6YxEPDva8HgXQxpnvhrlsENBveCJuC9uGpxcQBagUCgYEA3qiyoTe+WUvZ0yItb5hZVBI879+kmO5HrxmW+G7ySaTsHDVakKedIUfbIvK6XLkXEDi61Q8z8afRL/fUfZdCkdf0jWmMXQse8D1b8NLvF+nazBpbZKPW5nE9nnogNX8GCsDbEcZB9/Y4dK9wiBUDE5tgYgTpKCiuLuRE8AqoC10CgYB4rV8GJzDr5d9t5Skr4ZWJOKvdpkc+etJZ/Yn5gzLAIZK+aYKPdquftlcM7GDnHJaS8K/YSwl46EgYZAM2pZVcoHlvHdbnoxEwe56qqucajFHUNxNGAET0mtDRC4hN/wNzvWjLmyGN7JGpqpX7vvm0sPqoVjqkmsCnkUj+P1i2AQKBgGqCGmezU8yaI7X9OW3eUFcWKfMZDz8fkv/KOCUZENwBxmcu2Oc5oyNVrg5qPJhHw0N7xHVr1V9JazIoLe87TzyUacmf5IHuOSVO5xwmRlG3ZAK2wgj9xVb6NKUkYAfIvElUz4g9/pigKwL76Yi7fZk3erUBZQ+0vC27ohGCiQOJ";
	private static String charset="UTF-8"; // 字符编码格式
	
	//private static String alipay_public_key="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAyRfzAXOZUwDvAoFJgfXo1e7ApWfZvzIVCspxpp4Dz+ya9wmjOAKj6VlVnRLvElr82aaWF2cRziVcUs/MDkklzvsmlB0YH9fVdMN1ey+lLCbgkW6BLgniBGMPrUXWzlR7Yg6VWM0d1gjup6ZYmXjPaTQ1nVvl5Fm7+NyrePrcftxNnB1dRVflMM8HBLfMdRlNiGKStKjyh5sOyophPyTKP56Z7LvA/kdMhXSEEodDuYjSM02IgkCtcfF5NGYw1NHQOwqTIll5pEtiyhVvK8u2epA3R4wgJgWBZ6E1R+mWb7LMzaWMUajCWoVEDQFWTW18qgHMQ57Ii/qQ0uKlS9szpwIDAQAB";
	
	  private static String alipay_public_key="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArG89R/Huu9k2auLzjrb3JQv2aSIIl8WjD1Bu/pyPxLFK2+2tIBCqlSmdYLfhVK8IUCGK4iN4XU8OrSDL+dEp+OfcR5PeV8APA0Lc7eL5/neaU8PYM5W8N3O8pj2Z8wZ1mO6A539RiUDhwonp6iSTonyAdPG7zy+faY3yBqe6ThwT9s9LhFp/PTRFAFYnM8H73tIks0iaHdCLJZeQInY5aQ9l43vs52IzlCCyqMmN65wWLcYkyZr4DFpD4uBcafp1btKTRGpjhfUZyeEzIf5dzvaHDL5YNnJGpKyLqjLL0eBYvzDPFbGP6k5N8fNixYuTWoXt83rKrKrnVxXL8JElTwIDAQAB";
	
	private static String sign_type="RSA2"; //签名方式
	
     
	public static AlipayClient getInstance(){    
        if (alipayClient == null){
           synchronized(AliPayConfig.class){
                if (alipayClient == null)
                	alipayClient =new DefaultAlipayClient(gatewayUrl,app_id, merchant_private_key, "json",charset, alipay_public_key, sign_type); 
           }
        }
        return alipayClient;
	}
	
	public static String toCharsetJsonStr(Object object) throws Exception{
		return toCharsetStr(JSON.toJSONString(object));
	}

	public static String toCharsetStr(String val) throws Exception{
		return new String(val.getBytes("ISO-8859-1"),charset);
	}

	public static boolean rsaCheckV1(Map<String, String> params) throws AlipayApiException {
		return AlipaySignature.rsaCheckV1(params, AliPayConfig.alipay_public_key, AliPayConfig.charset, AliPayConfig.sign_type);
	}
	
}
