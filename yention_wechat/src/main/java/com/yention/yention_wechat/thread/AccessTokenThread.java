package com.yention.yention_wechat.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yention.yention_wechat.bean.AccessToken;
import com.yention.yention_wechat.util.WeixinUtil;

/** 
 * @Package com.yention.yention_wechat.thread
 * @ClassName: AccessTokenThread
 * @Description: 用单线程去获取access_token
 * @author 孙刚
 * @date 2019年4月11日 下午10:50:07
 */
public class AccessTokenThread extends Thread {
    private static Logger log = LoggerFactory.getLogger(AccessTokenThread.class);

    // 第三方用户唯一凭证
    public String appid;
    // 第三方用户唯一凭证密钥
    public String appsecret;
    
    public static AccessToken accessToken;
    
    public AccessTokenThread(String appid,String appsecret){
    	this.appid = appid;
    	this.appsecret = appsecret;
    }
    
    public void run() {
    	log.info("开始获取accessToken");
        while (true) {
            accessToken = WeixinUtil.getAccessToken(appid, appsecret);

            if (null != accessToken) {

                try {
                    log.info("获取成功，accessToken:" + accessToken.getToken());
                    Thread.sleep(7200000);    // 休眠7200秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } else {
                try {
                    log.error("获取token失败");
                    Thread.sleep(60000);    // 休眠60秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

    }
}
