package com.abc12366.gateway.util;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.Map;
import java.util.Set;


/**
 * wechat
 *
 * @param <T>
 * @author lizhongwei 2017-7-31
 */
public class WxConnect<T> {
    //public final static ObjectMapper mapper = new ObjectMapper();
    private static final Logger LOGGER = LoggerFactory.getLogger(WxConnect.class);

    protected Class<T> _class;
    private String requestUrl;
    private String requestMethod;
    private String outputStr = null;
    private String jsonStr;
    private Map<String, String> headparamters;
    private Object bodyparamters;

    public WxConnect(String url, String requestMethod, Map<String, String> headparamters, Object bodyparamters, Class<T> _class) {
        this.requestMethod = requestMethod;
        this.headparamters = headparamters;
        this.bodyparamters = bodyparamters;
        this._class = _class;
        this.requestUrl = "http://118.118.116.202:9500/admins/user/token/37748c5dbddea1e0151b8c52b1144966";
        //mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //mapper.configure(DeserializationConfig.Feature.FAIL_ON_NUMBERS_FOR_ENUMS, true);
    }

    public void initJson() {
        try {
            if (headparamters != null) {
                StringBuffer url = new StringBuffer(requestUrl).append("?");
                Set<String> keys = headparamters.keySet();
                if (keys != null) {
                    for (String key : keys) {
                        String val = (String) headparamters.get(key);
                        url.append("&" + key + "=" + val);
                    }
                }
                requestUrl = url.toString();
            }
            if (bodyparamters != null) {
                //this.outputStr=mapper.writeValueAsString(bodyparamters);
                this.outputStr = JSON.toJSONString(bodyparamters);
            }
        } catch (Exception e) {
            this.setJsonStr("{\"errcode\":\"-999\",\"errmsg\":\"组装微信请求参数异常，请联系管理员\"}");
        }
    }

    public void initXml() {

    }

    public void httpsRequest() {
        HttpsURLConnection conn = null;
        BufferedReader buffferedReader = null;
        InputStreamReader inputStreamReader = null;
        InputStream inputStream = null;
        try {
            URL url = new URL(requestUrl);
            conn = (HttpsURLConnection) url.openConnection();
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(30000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod(requestMethod);

            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            inputStream = conn.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            buffferedReader = new BufferedReader(inputStreamReader);
            String str;
            StringBuffer buffer = new StringBuffer();
            while ((str = buffferedReader.readLine()) != null) {
                buffer.append(str);
            }
            this.setJsonStr(buffer.toString());
        } catch (Exception e) {
            this.setJsonStr("{\"errcode\":\"-999\",\"errmsg\":\"微信服务器请求异常异常，请联系管理员\"}");
        } finally {
            try {
                if (buffferedReader != null) {
                    buffferedReader.close();
                }
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                if (conn != null) {
                    conn.disconnect();
                }
            } catch (Exception e) {
                this.setJsonStr("{\"errcode\":\"-999\",\"errmsg\":\"微信服务器关闭连接异常，请联系管理员\"}");
            }

        }
    }

    @SuppressWarnings("unchecked")
    T parseObject() {
        T res = null;
        try {
            res = JSON.parseObject(this.getJsonStr(), _class);
            //T res = mapper.readValue(this.getJsonStr(), _class);
            return res;
        } catch (Exception e) {
            LOGGER.error("微信服务器返回json格式异常", e);
            e.printStackTrace();
        }
        return res;
    }


    public String getJsonStr() {
        return jsonStr;
    }

    public void setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
    }
}
