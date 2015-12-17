package com.ufo.util;

import java.io.ByteArrayOutputStream;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class SendSms {
	private static final String serviceUrl = "http://yxt.bbn.com.cn/eums/rpc/";
	//private static final  String account ="test001";
	//private static final  String passwd = "123qwe";	//密码
	private static final String account ="jiatangjinying";		
	private static final String passwd = "20140925";	//密码
	/**
	 * 直接组装json串发送短信
	 */
	public static void main(String[] args) {
		String message ="你好，今天打球了吗？";
//		String mobiles = "13874951056,18874937398,13875917905,15274941721,13762317307";
		String mobiles="13874951056,18874937398";
		SendSms.sendMessage(message,mobiles);	
	}

	public static void sendMessage( String message,String mobiles) {	
		try {
			int reqId = 1;
			//获取登录令牌
			JSONObject reqJson = new JSONObject();
			reqJson.put("id", reqId++);
			reqJson.put("jsonrpc", "2.0");
			reqJson.put("method", "genLoginToken");
			JSONArray params = new JSONArray();
			params.add(account);
			params.add(passwd);
			reqJson.put("params", params);
			
			String resStr = postJson(serviceUrl + "power/authService", reqJson.toString());
			//System.out.println(resStr);
			JSONObject resJson = JSONObject.fromObject(resStr);
			if(resJson.has("result")){
				JSONObject resultJson = resJson.getJSONObject("result");
				if(resultJson.getBoolean("result")){
					//令牌
					String loginToken = resultJson.getString("others");
					//发送短信
					reqJson = new JSONObject();
					reqJson.put("id", reqId++);
					reqJson.put("jsonrpc", "2.0");
					reqJson.put("method", "save");					
					JSONObject smsJson = new JSONObject();
					smsJson.put("businessType", "短信发送");		//业务类型，用于统计
					smsJson.put("sendText", message);		//短信内容
					//接收人姓名，可以多个，用半角逗号分隔
					smsJson.put("sendTo", mobiles);		
					//接收号码，可以多个，用半角逗号分隔				
					smsJson.put("toDetail",mobiles);
					smsJson.put("sendFrom", "api");
					params = new JSONArray();
					params.add(smsJson);
					reqJson.put("params", params);
					JSONObject authJson = new JSONObject();
					authJson.put("loginToken", loginToken);
					reqJson.put("auth", authJson);
					resStr = postJson(serviceUrl + "smsSave", reqJson.toString());
					//System.out.println(resStr);
					resJson = JSONObject.fromObject(resStr);
					resultJson = resJson.getJSONObject("result");
					if(resultJson.getBoolean("result")){
						System.out.println("Message Send Success!");
					}
					else {
						System.out.println("send fail: " + resultJson.getString("msg"));
					}
				}				
			}
			
		} catch (Exception e) {
			System.out.println("send fail");
		}
	}
	
	public static String postJson(String url, String jsonStr) throws Exception{
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		StringEntity entity = new StringEntity(jsonStr, "utf-8");
		post.setEntity(entity);
		HttpResponse response = client.execute(post);
		int statusCode = response.getStatusLine().getStatusCode();
		HttpEntity  httpEntity = response.getEntity();
		 if (httpEntity != null) {
			 //post.abort();
	     }
		if(statusCode == 200){
			ByteArrayOutputStream resHolder = new ByteArrayOutputStream();
			httpEntity.writeTo(resHolder);
			resHolder.flush();
	    	return resHolder.toString("utf-8");
		}
		else {
			throw new Exception("服务异常");
		}
	}
}
