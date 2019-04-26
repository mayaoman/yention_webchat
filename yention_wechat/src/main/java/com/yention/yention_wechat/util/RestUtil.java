package com.yention.yention_wechat.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

/** 
 * @Package com.yention.yention_wechat.util
 * @ClassName: RestUtil
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 孙刚
 * @date 2019年4月18日 上午1:22:13
 */
public class RestUtil {
	@Autowired
    private static RestTemplate restTemplate;
	

	/**
	 * @Title: postForObject
	 * @Description: POST请求
	 * @param url
	 * @param jsonStr
	 * @return String   
	 */
	public static String postForObject(String url,String jsonStr){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		headers.add("Accept", MediaType.APPLICATION_JSON.toString());
		HttpEntity<String> formEntity = new HttpEntity<String>(jsonStr, headers);
		
		//第1个参数：请求的url地址
		//第2个参数：其实是HttpEntity，这个类主要有三种构造方法，如下
		//        new HttpEntity(请求体)
		//        new HttpEntity(请求头)
		//        new HttpEntity(请求体,请求头)
		//第3个参数：返回的结果类型，这里String.class表示返回结果是一个字符串。
		//第4个参数：参数值，这里有Map和 可变参数两种形式（通常用不到，数据通常放在Json里就全部传输过去了）
		String jsonResult = restTemplate.postForObject(url , formEntity, String.class);
		
		return jsonResult;
	}
	
	/**
	 * @Title: getForObject
	 * @Description: get请求
	 * @param url
	 * @param map
	 * @return String   
	 */
	public static String getForObject(String url,Map<String,Object> map){
		//第1个参数：请求的url地址
		//第2个参数：返回的结果类型，这里String.class表示返回结果是一个字符串。
		//第3个参数：参数值，这里有Map和 可变参数两种形式
//		Map map = new HashMap();
//		map.put("1", "hello");
//		map.put("2", "world");
		//http://localhost:8081/getIds?param1={1}&param2={2}
		String result = restTemplate.getForObject(url, String.class,map);
		//String result2 = restTemplate.getForObject("http://localhost:8081/getIds?param1={1}&param2={2}", String.class, "hello", "world");
		return result;
	}
}
