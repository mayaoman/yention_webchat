package com.yention.yention_wechat.util;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/** 
 * @Package com.yention.yention_wechat.util
 * @ClassName: MyX509TrustManager
 * @Description: 证书信任管理器（用于https请求）
 * @author 孙刚
 * @date 2019年4月11日 下午10:49:27
 */
public class MyX509TrustManager implements X509TrustManager {

    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    }

    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    }

    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }
}