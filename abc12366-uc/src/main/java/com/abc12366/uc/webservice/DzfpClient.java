package com.abc12366.uc.webservice;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.namespace.QName;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.commons.httpclient.protocol.Protocol;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abc12366.uc.model.dzfp.DzfpGetReq;
import com.abc12366.uc.model.dzfp.Einvocie;
import com.alibaba.fastjson.JSON;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 电子发票客户端
 * @author zhushuai 2017-8-8
 *
 */
public class DzfpClient {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DzfpClient.class);
	/**
	 * 	 进入test.cer所在目录，执行如下操作（注意配置环境变量），生成testclient.truststore文件:
	 *   keytool -import -file test.cer -storepass 123456 -keystore testclient.truststore
	 *   然后输入“y”，回车
	 *   命令说明：
	 *   keytool  JDK提供的证书生成工具，所有参数的用法参见keytool –help
	 *   -import  执行导入
	 *   -file test.cer  要导入的证书，即从ie上导出的证书
	 *   -storepass 123456  证书的存取密码
	 *   -keystore testclient.truststore 保存路径及文件名
	 */
	private static String ssl_store = "C:/cer/testclient.truststore";//证书地址
	
	private static String ssl_pwd = "123456";//证书密码
	
	//private static String endpoint="https://www.fapiao.com:53087/fpt-dsqz/services/DZFPService";
	private static String endpoint="https://dev.fapiao.com:19443/fpt-dsqz-spbm/services/DZFPService";
	
	
	private static String namepace="http://dsqzjk.dzfpqz";
	
	private static String appid="6d29f136114544bcc73edcce960c430231183cc192c433e2b9ebcad56e8ceb08";
	
	
	@SuppressWarnings("rawtypes")
	public static Object doSender(String interfaceCode,String content,Class _class) throws Exception{
		System.setProperty("javax.net.ssl.trustStore", ssl_store);
		System.setProperty("javax.net.ssl.keyStorePassword", ssl_pwd);
		System.setProperty("javax.net.ssl.trustStorePassword", ssl_pwd);
		
		Protocol protocol = null;
		SSLIgnoreErrorProtocolSocketFactory socketfactory = null;

		socketfactory = new SSLIgnoreErrorProtocolSocketFactory();

		protocol = new Protocol("https", socketfactory, 443);
		RPCServiceClient serviceClient = new RPCServiceClient();
    	Options options = serviceClient.getOptions();
    	EndpointReference targetEPR = new EndpointReference(endpoint);
    	
    	options.setProperty(HTTPConstants.CUSTOM_PROTOCOL_HANDLER, protocol);
    	
    	options.setTo(targetEPR);
    	
    	String xml = getCommonXml(interfaceCode, new BASE64Encoder().encodeBuffer(content.getBytes("UTF-8")));
    	
    	LOGGER.info("调用电子发票WebService,请求信息:",xml);
		
    	Object[] opArgs = new Object[] {xml };
    	Class<?>[] opReturnType = new Class[] { String.class };
    	Object[] ret = serviceClient.invokeBlocking(new QName(namepace, "doService"), opArgs,  opReturnType);
    	return xmlToObject(ret[0].toString(),_class);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object xmlToObject(String result,Class _class) throws Exception{
		
		LOGGER.info("调用电子发票WebService,接收信息:",result);
		
		Object object=_class.newInstance();
		Document doc = DocumentHelper.parseText(result);
		Element rootElt = doc.getRootElement(); // 获取根节点
		
		Element returnCode=(Element) doc.selectNodes("/interface/returnStateInfo/returnCode").get(0);
		Method m = _class.getDeclaredMethod("setReturnCode", String.class);
		m.invoke(object,returnCode.getTextTrim()); 
		
		Element returnMessage=(Element) doc.selectNodes("/interface/returnStateInfo/returnMessage").get(0);
		Method m2 = _class.getDeclaredMethod("setReturnMessage", String.class);
		m2.invoke(object,returnMessage.getTextTrim()); 
		
		
		Element data=rootElt.element("Data").element("content");
		String content=new String(new BASE64Decoder().decodeBuffer(data.getTextTrim()),"UTF-8");
		Document doc2 = DocumentHelper.parseText(content);		
		
		
		List<Element> elements=doc2.getRootElement().elements();
		if(elements!=null && elements.size()>0){
			for(Element element:elements){
				try{
				 Method m3 = _class.getDeclaredMethod("set"+element.getName(), String.class);
				 m3.invoke(object,element.getTextTrim()); 
				}catch(Exception e){
					continue;
				}
			}
		}
			
		return object;
	}
	
	
	/**
	 * 组装发送报文格式
	 * @param interfaceCode
	 * @param content
	 * @return
	 */
	private static String getCommonXml(String interfaceCode,String content){
		StringBuffer sb = new StringBuffer("");
		sb.append("<?xml version='1.0' encoding='UTF-8' ?>");
		sb.append("<interface xmlns=\"\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:schemaLocation=\"http://www.chinatax.gov.cn/tirip/dataspec/interfaces.xsd\" version=\"DZFPQZ0.2\"> ");
		sb.append("<globalInfo>");
		sb.append("<appId>").append(appid).append("</appId>");
		sb.append("<interfaceId></interfaceId>");
		sb.append("<interfaceCode>").append(interfaceCode).append("</interfaceCode>");
		sb.append("<requestCode>DZFPQZ</requestCode>");
		sb.append("<requestTime>").append(formatToTime()).append("</requestTime>");
		sb.append("<responseCode>Ds</responseCode>");
		sb.append("<dataExchangeId>DZFPQZ").append(interfaceCode).append(formatToDay()).append(randNineData()).append("</dataExchangeId>");
		sb.append("</globalInfo>");
		sb.append("<returnStateInfo>");
		sb.append("<returnCode></returnCode>");
		sb.append("<returnMessage></returnMessage>");
		sb.append("</returnStateInfo>");
		sb.append("<Data>");
		sb.append("<dataDescription>");
		sb.append("<zipCode>0</zipCode>");
		sb.append("</dataDescription>");
		sb.append("<content>");
		sb.append(content);
		sb.append("</content>");
		sb.append("</Data>");
		sb.append("</interface>");
		return sb.toString();
	}
	
	/*
	 * 格式化时间-时间
	 */
	public static String formatToTime(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		return sdf.format((new Date()));
	}
	
	/*
	 * 格式化时间-日
	 */
	public static String formatToDay(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format((new Date()));
	}
	
	/*
	 * 9位随机整数
	 */
	public static String randNineData(){
		return String.valueOf((int)(Math.random()*900000000+100000000));
	}
	
	
	public static void main(String[] args) throws Exception {
		//doSender("DFXJ1001","<REQUEST_COMMON_FPKJ class='REQUEST_COMMON_FPKJ'> <FPQQLSH>21151334123422451</FPQQLSH><KPLX>0</KPLX><XSF_NSRSBH>110109500321655</XSF_NSRSBH><XSF_MC>百旺电子测试2</XSF_MC><XSF_DZDH>山东省青岛市</XSF_DZDH><XSF_YHZH>92523123213412341234</XSF_YHZH><GMF_NSRSBH>440300568519737</GMF_NSRSBH><GMF_MC>张三</GMF_MC><GMF_DZDH>浙江省杭州市余杭区文一西路xxx号18234561212</GMF_DZDH><GMF_YHZH>123412341234</GMF_YHZH><GMF_SJH>18234561212</GMF_SJH><GMF_DZYX>mytest@xxx.com</GMF_DZYX><FPT_ZH></FPT_ZH><KPR>小张</KPR><SKR></SKR><FHR>小林</FHR><YFP_DM>111100000000</YFP_DM><YFP_HM>00004349</YFP_HM><JSHJ>1170.00</JSHJ><HJJE>1000.00</HJJE><HJSE>170.00</HJSE><BZ>电子发票测试</BZ><HYLX>1</HYLX><BY1></BY1><BY2></BY2><BY3></BY3><BY4></BY4><BY5></BY5><BY6></BY6><BY7></BY7><BY8></BY8><BY9></BY9><BY10></BY10><COMMON_FPKJ_XMXXS class='COMMON_FPKJ_XMXX' size='1'><COMMON_FPKJ_XMXX><FPHXZ>0</FPHXZ><SPBM>3040201990000000000</SPBM><XMMC>软件服务</XMMC><GGXH>X100</GGXH><DW>台</DW><XMSL>10</XMSL><XMDJ>100.00</XMDJ><XMJE>1000.00</XMJE><SL>0.17</SL><SE>170.00</SE><BY1></BY1><BY2></BY2><BY3></BY3><BY4></BY4><BY5></BY5><BY6></BY6></COMMON_FPKJ_XMXX></COMMON_FPKJ_XMXXS></REQUEST_COMMON_FPKJ>");		
		 
		DzfpGetReq dzfpGetReq=new DzfpGetReq();
		dzfpGetReq.setZsfs("0");
		dzfpGetReq.setKplx("0");
		dzfpGetReq.setXsf_nsrsbh("110109500321655");
		dzfpGetReq.setXsf_mc("百旺电子测试2");
		dzfpGetReq.setXsf_dzdh("南山区蛇口、83484949");
		dzfpGetReq.setGmf_mc("王毅");
		dzfpGetReq.setJshj("3504.27");
		dzfpGetReq.setHjje("3504.27");
		dzfpGetReq.setHjse("0");
		dzfpGetReq.setXmmc("棉花");
		dzfpGetReq.setSpbm("1010105000000000000");
		dzfpGetReq.setSl("0");
		dzfpGetReq.setSe("0");
		dzfpGetReq.setXmje("3504.27");
		dzfpGetReq.setHylx("0");
		dzfpGetReq.setFphxz("0");
		dzfpGetReq.setYhzcbs("0");
		dzfpGetReq.setKpr("shuaiia");
		
		Einvocie einvocie=(Einvocie) doSender("DFXJ1001",dzfpGetReq.tosendXml(),Einvocie.class);
		System.out.println(JSON.toJSONString(einvocie));
	}
}
