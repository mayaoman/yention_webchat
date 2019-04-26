package com.yention.yention_wechat.bean;

/** 
 * @Package com.yention.yention_wechat.bean
 * @ClassName: AccessToken
 * @Description: 微信通用接口凭证
 * @author 孙刚
 * @date 2019年4月11日 下午10:46:25
 */
public class AccessToken {
    // 获取到的凭证
    private String token;
    // 凭证有效时间，单位：秒
    private int expiresIn;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }
}