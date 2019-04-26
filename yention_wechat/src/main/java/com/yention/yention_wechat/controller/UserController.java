package com.yention.yention_wechat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yention.yention_wechat.service.UserService;
import com.yention.yention_wechat.thread.AccessTokenThread;

import net.sf.json.JSONObject;

/** 
 * @Package com.yention.yention_wechat.controller
 * @ClassName: UserController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 孙刚
 * @date 2019年4月12日 上午1:51:52
 */
@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
    private UserService userService;
    
    /**
     * @Title: get
     * @Description: 获取用户列表
     * @return String   
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String get() {
        // 调用接口获取access_token
        String access_token = AccessTokenThread.accessToken.getToken();
        String userList = userService.getUserList(access_token);
        return userList;
    }
    
    /**
     * @Title: info
     * @Description: 获取用户基本信息（包括UnionID机制）
     * @return String   
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String info() {
        // 调用接口获取access_token
    	String openId = "o1ZNg1RpdH_N6f0tV_PqF9f4NaJI";
        String access_token = AccessTokenThread.accessToken.getToken();
        JSONObject userList = userService.getUserInfo(openId, access_token);
        return String.valueOf(userList);
    }
}
