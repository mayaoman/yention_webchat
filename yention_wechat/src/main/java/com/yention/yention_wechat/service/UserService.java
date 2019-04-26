package com.yention.yention_wechat.service;

import net.sf.json.JSONObject;

/** 
 * @Package com.yention.yention_wechat.service
 * @ClassName: UserService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 孙刚
 * @date 2019年4月12日 上午1:39:22
 */
public interface UserService {
	public String getUserList(String access_token);
	public JSONObject getUserInfo(String openId,String access_token);
}
