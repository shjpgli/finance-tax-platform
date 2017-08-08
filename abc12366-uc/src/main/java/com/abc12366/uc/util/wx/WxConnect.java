package com.abc12366.uc.util.wx;

import com.abc12366.uc.config.SpringCtxHolder;
import com.abc12366.uc.model.weixin.BaseWxRespon;
import com.abc12366.uc.model.weixin.bo.message.FileContent;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * wechat
 *
 * @param <T>
 * @author zhushuai 2017-7-27
 */
public class WxConnect<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(WxConnect.class);

    protected Class<T> _class;
    private String requestUrl;
    private WechatUrl wechatUrl;
    private String requestMethod;
    private String outputStr = null;
    private String jsonStr;
    private Map<String, String> headparamters;
    private Object bodyparamters;
    private FileContent file;

    public WxConnect(WechatUrl url, String requestMethod, Map<String, String> headparamters, Object bodyparamters,
                     Class<T> _class) {
        this.wechatUrl = url;
        this.requestMethod = requestMethod;
        this.headparamters = headparamters;
        this.bodyparamters = bodyparamters;
        this._class = _class;
        this.requestUrl = SpringCtxHolder.getProperty("abc.wx-url") + wechatUrl.uri;
        //mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //mapper.configure(DeserializationConfig.Feature.FAIL_ON_NUMBERS_FOR_ENUMS, true);
        //this.requestUrl = SpringCtxHolder.getProperty("abc.wx-url") + wechatUrl.uri;
    }

    public WxConnect(WechatUrl url, Map<String, String> headparamters, Object bodyparamters, Class<T> _class,
					 FileContent file) {
        this.wechatUrl = url;
        this.file = file;
        this.headparamters = headparamters;
        this.bodyparamters = bodyparamters;
        this._class = _class;
        this.requestUrl = SpringCtxHolder.getProperty("abc.wx-url") + wechatUrl.uri;
    }

    public static byte[] listToByteArray(List<Byte> list) {
        byte[] bytes = new byte[list.size()];
        for (int i = 0; i < list.size(); i++) {
            bytes[i] = list.get(i).byteValue();
        }
        return bytes;
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
                this.outputStr = JSON.toJSONString(bodyparamters);
            }
        } catch (Exception e) {
//            this.setJsonStr("{\"errcode\":\"-999\",\"errmsg\":\"组装微信请求参数异常，请联系管理员\"}");
//            LOGGER.info("组装微信请求[" + wechatUrl.describe + "]参数异常:{}", e);
        }
    }

    public void httpPostFile() {
        HttpsURLConnection conn = null;
        InputStreamReader inputStreamReader = null;
        InputStream inputStream = null;
        BufferedReader buffferedReader = null;
        try {
            URL url = new URL(requestUrl);
            conn = (HttpsURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");

            // 设置边界
            String BOUNDARY = "----------" + System.currentTimeMillis();
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

            StringBuilder sb = new StringBuilder();
            sb.append("--");
            sb.append(BOUNDARY);
            sb.append("\r\n");
            sb.append("Content-Disposition: form-data;name=\"media\";filename=\"" + this.file.getFileName() + "\"\r\n");
            sb.append("Content-Type:application/octet-stream\r\n\r\n");

            byte[] head = sb.toString().getBytes("utf-8");

            // 获得输出流
            OutputStream out = new DataOutputStream(conn.getOutputStream());
            // 输出表头
            out.write(head);

	        /*DataInputStream in = new DataInputStream(new FileInputStream(new File
	        ("D:\\hurocms\\Tomcat\\webapps\\huro_cms\\images\\1.png")));
	        int bytes = 0;
	        byte[] bufferOut = new byte[1024];
	        while ((bytes = in.read(bufferOut)) != -1) {
	           out.write(bufferOut, 0, bytes);
	        }
	        in.close();*/

            byte[] buff = listToByteArray(this.file.getFileContent());
            out.write(buff);
            // 结尾部分
            byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
            out.write(foot);
            out.flush();
            out.close();

            inputStream = conn.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            buffferedReader = new BufferedReader(inputStreamReader);
            String str;
            StringBuffer buffer = new StringBuffer();
            while ((str = buffferedReader.readLine()) != null) {
                buffer.append(str);
            }
            LOGGER.info(wechatUrl.describe + "->微信服务器上传返回信息:{}", buffer.toString());
            this.setJsonStr(buffer.toString());
        } catch (Exception e) {
            this.setJsonStr("{\"errcode\":\"-999\",\"errmsg\":\"微信服务器请求异常异常，请联系管理员\"}");
            LOGGER.error(wechatUrl.describe + "->微信服务器请求异常异常", e);
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
                LOGGER.error(wechatUrl.describe + "->微信服务器关闭连接异常", e);
            }
        }
    }

    public void httpsRequest() {
        HttpsURLConnection conn = null;
        BufferedReader buffferedReader = null;
        InputStreamReader inputStreamReader = null;
        InputStream inputStream = null;
        try {
            //创建SSLContext 对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = {new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            URL url = new URL(requestUrl);
            conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);
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
            LOGGER.info(wechatUrl.describe + "->微信服务器返回信息:{}", buffer.toString());
            this.setJsonStr(buffer.toString());
        } catch (Exception e) {
            this.setJsonStr("{\"errcode\":\"-999\",\"errmsg\":\"微信服务器请求异常异常，请联系管理员\"}");
            LOGGER.error(wechatUrl.describe + "->微信服务器请求异常异常", e);
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
                LOGGER.error(wechatUrl.describe + "->微信服务器关闭连接异常", e);
            }

        }
    }

    @SuppressWarnings("unchecked")
    T parseObject() {
        try {
            T res = JSON.parseObject(this.getJsonStr(), _class);
            return res;
        } catch (Exception e) {
            LOGGER.error("微信服务器返回json格式异常", e);
            e.printStackTrace();
            return (T) new BaseWxRespon("-999", "SOA返回数据格式异常，请联系管理员");
        }

    }

    public String getJsonStr() {
        return jsonStr;
    }

    public void setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
    }
}
