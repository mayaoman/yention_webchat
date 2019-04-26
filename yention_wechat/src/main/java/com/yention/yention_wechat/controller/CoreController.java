package com.yention.yention_wechat.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yention.yention_wechat.service.CoreService;
import com.yention.yention_wechat.thread.AccessTokenThread;
import com.yention.yention_wechat.util.SignUtil;

/** 
 * @Package com.yention.yention_wechat.controller
 * @ClassName: CoreController
 * @Description: 公众号核心控制类
 * @author 孙刚
 * @date 2019年4月11日 下午10:29:38
 */
@RestController
@RequestMapping("")
public class CoreController {
	@Autowired
    private CoreService coreService;
	// 与接口配置信息中的Token要一致
	@Value("${wechat.token}")
	private String token;
	
    /**
     * @Title: checkSignature
     * @Description: 验证是否来自微信服务器的消息
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @return String   
     */
    @RequestMapping(value = "",method = RequestMethod.GET,produces = "text/plain;charset=utf-8")
    public String checkSignature(@RequestParam(name = "signature" ,required = false) String signature  ,
                              @RequestParam(name = "timestamp" ,required = false) String timestamp  ,
                              @RequestParam(name = "nonce",required = false) String  nonce ,
                              @RequestParam(name = "echostr",required = false) String  echostr){
        if (SignUtil.checkSignature(token,signature, timestamp, nonce)) {
            return echostr;
        }
        return "";
    }

    /**
     * @Title: post
     * @Description: 调用核心业务类接收消息、处理消息跟推送消息
     * @param req
     * @return String   
     */
    @RequestMapping(value = "",method = RequestMethod.POST, produces = "application/xml; charset=UTF-8")
    public  String post(HttpServletRequest req){
        // 调用核心业务类接收消息、处理消息跟推送消息
        String respMessage = coreService.processRequest(req);
        return respMessage;
    }

    @RequestMapping(value = "/gettoken",method = RequestMethod.GET)
    public String getToken(){
        return AccessTokenThread.accessToken.getToken();
    }
}
