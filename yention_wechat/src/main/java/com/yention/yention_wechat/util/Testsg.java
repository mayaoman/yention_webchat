package com.yention.yention_wechat.util;

import java.util.HashMap;
import java.util.Map;

import javax.xml.ws.Response;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;

import net.sf.json.JSONObject;

public class Testsg {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String url = "http://localhost:8082/order/addUser";
//        Map<String,String> map = new HashMap<String,String>();
//		map.put("openid", "sgwx003");
//        String json= JSONObject.fromObject(map).toString();
//        System.out.println(json);
//        JSONObject jsonObject = WeixinUtil.httpRequest(url, "POST", json);
//        System.out.println(String.valueOf(jsonObject));
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setReadTimeout(5000);//单位为ms
		factory.setConnectTimeout(5000);//单位为ms
		RestTemplate restTemplate = new RestTemplate(factory);
		//ExpressionDomain expressionDomain=new ExpressionDomain("hello","hasaki","win");
		Map<String,String> map = new HashMap<String,String>();
		map.put("openid", "345345343");
        String json= JSONObject.fromObject(map).toString();
		//JSONObject jsonObj = (JSONObject) JSONObject.toJSON(expressionDomain);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		headers.add("Accept", MediaType.APPLICATION_JSON.toString());
		HttpEntity<String> formEntity = new HttpEntity<String>(json, headers);
		String jsonResult = restTemplate.postForObject(url , formEntity, String.class);
		System.out.println(jsonResult);
		System.out.println("ces");
		//Response resp = JSON.parseObject(jsonResult, new TypeReference<Response>() {});
	}

}
