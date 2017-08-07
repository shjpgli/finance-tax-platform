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
	private static String gatewayUrl="https://openapi.alipaydev.com/gateway.do";// 支付宝网关 
	
	private static String app_id="2016082000294562"; // 应用ID收款账号既是您的APPID对应支付宝账号 
	
	private static String merchant_private_key="MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCiwWYDAuaElzrsLeP9CVkniyPBEhgZqybIf0AcmtsmdDlmXEjymA7mNpoRXjq5J6T3pBVV02+5tsecoK0ltaW8IkFhOmLO4tS40+Tc2pf+ua3zdafpbVB0C7PaRYnAOkhvDVAH7h4CixCrybnekNp6snhJqbGTELpsqNzl6kHUwceoAdtW9H5h3GvJJ4+qzZ/LNmbF6enOXeAO3ymYimvcoTfmDFX+qj6SwHvy8m4dyTp77Ke9wWRivzvmYNuywL+vcVKtcqsq/hkYT2iOqMd+E0yBDCqtaPae3PPB7AeQdJEtr5WH9hS9Pg2u9jWZkWQIO60uu2Z6JAlvcjQQ0EHDAgMBAAECggEBAJqIauWdEWb/eJDbmced4qrrL82X8o4ctbxVJniCiGZgoEeShCjjO/JWEb8/6x32iGohv6g4E7MP20PaQ8y0RmL1W4KHv12Ufknn1zvjycGwZ3vsULwQcP1eRyyOLhb4v+HBkCWXb6MWi4OxC+xJOUiawB+hIdvzXC7jvqoG3O62c5E5utRzZmfStJ/G860oJ1vadDnWfWhS9DTSRxr3JW60l9OiKoZENQzXOIK4ugulfmLXx0nQp6qBEsvskqP530DWIhuRAMiVJlhAzBk4nODcV5ikc6GviwQeW0ejQa8m5PAbheNxL0VPhmTpKiw/wRWreYbQSoQX6VjHtRcswLECgYEA02xpEQsC8nfuUlnlNninLDJys4/1Z+THKEAlj0xbKndRBJV3U9mHsrKY/vEwyYwLdQXXw+HWnbLLsppcQaTUlH7qZZRIeaQehJk9RLFxXuTRawAJTMzbosbCcyHe3zh6mMoaio2AWupiQo3KyOl5wAm0R4z7HW3qwGOkhXuNg4UCgYEAxRIhV9O0rhJbtj5DQ5SPAwMSaNHO7LvyNkQDoYh+zYU62K5/T3ASvjoipQdH1NEytWP0WPZvuB5tNStF2RqVW4SxzHvD9U6q7MDM27hmr8pEVtULVyqmNNFG3MmxA6L03Dp5DQ5b8cOodbIZx3RCE1SmWrQqkfJm/py0JH70fqcCgYEAkk5rRsf/+hrUaVicLd3Ake03QttUT9kZmNkLJOcKD5ESF8H3GadnKFo/Ve1CfbbUFt88OOmHwwl/vNDlFugh08EgI7tBjCvukoQYyfihI7Hk2c7koKkDNNmmplXCDWbgL+tsAoV5VXDtHnFQ60fmYOZWQ01kWTE/SnOp3lVp0KECgYAvCevRhePbpOAlOOlfSSd1pZcQU/wbP6VJ1KQ1O1pg/gsUy1nyASNIKqI4V+uKXcDH0SryzSfPf9u7VC/g6Vp7NMonr+cPN47O4hpD29kO3q2/OCzzymdPtiA+9Rl+b7gYlRBt0F6b74LXTQKgPBzFdnAauGRs37o7zfWlvHPpgwKBgQCxN0mHxiILc+f/d21qIyk59+hUeym7ftXoqfGAgS8zP/+g9s4ioTNrOhAGf0cqso3Z8m4MTyFGWXLbkDDBh8YeDKFgLricLmazRPI4WyXwZjaYp5Tb3Ui3PfyLRZyz8LIWa9LsT7dw51WkwJ2SAqRQ5chKq+wDZ6Vh6OD9ZxqITw==";
	private static String charset="UTF-8"; // 字符编码格式
	
	private static String alipay_public_key="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAyRfzAXOZUwDvAoFJgfXo1e7ApWfZvzIVCspxpp4Dz+ya9wmjOAKj6VlVnRLvElr82aaWF2cRziVcUs/MDkklzvsmlB0YH9fVdMN1ey+lLCbgkW6BLgniBGMPrUXWzlR7Yg6VWM0d1gjup6ZYmXjPaTQ1nVvl5Fm7+NyrePrcftxNnB1dRVflMM8HBLfMdRlNiGKStKjyh5sOyophPyTKP56Z7LvA/kdMhXSEEodDuYjSM02IgkCtcfF5NGYw1NHQOwqTIll5pEtiyhVvK8u2epA3R4wgJgWBZ6E1R+mWb7LMzaWMUajCWoVEDQFWTW18qgHMQ57Ii/qQ0uKlS9szpwIDAQAB";
	
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
