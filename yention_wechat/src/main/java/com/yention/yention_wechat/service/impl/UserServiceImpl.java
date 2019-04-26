package com.yention.yention_wechat.service.impl;

import org.springframework.stereotype.Service;

import com.yention.yention_wechat.service.UserService;
import com.yention.yention_wechat.util.WeixinUtil;

import net.sf.json.JSONObject;

/** 
 * @Package com.yention.yention_wechat.service.impl
 * @ClassName: UserServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 孙刚
 * @date 2019年4月12日 上午1:42:23
 */
@Service
public class UserServiceImpl implements UserService {
	//获取用户列表
    private String user_get_url ="https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
    //获取用户基本信息（包括UnionID机制）
    private String user_info_url ="https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	
    /** (non Javadoc)
	 * Title: getUserList
	 * Description: 
	 * @return
	 * @see com.yention.yention_wechat.service.UserService#getUserList()
	 */
	@Override
	public String getUserList(String access_token) {
		String url=user_get_url.replace("ACCESS_TOKEN",access_token);
		String url2=url.replace("NEXT_OPENID","");
        JSONObject jsonObject= WeixinUtil.httpRequest(url2,"GET",null);
        return String.valueOf(jsonObject);
	}

	/** (non Javadoc)
	 * Title: getUserInfo
	 * Description: 
	 * @param openId
	 * @return
	 * @see com.yention.yention_wechat.service.UserService#getUserInfo(java.lang.String)
	 */
	@Override
	public JSONObject getUserInfo(String openId,String access_token) {
		String url=user_info_url.replace("ACCESS_TOKEN",access_token);
		String url2=url.replace("OPENID",openId);
        JSONObject jsonObject= WeixinUtil.httpRequest(url2,"GET",null);
        //return String.valueOf(jsonObject);
        return jsonObject;
	}

}
