package com.yention.yention_wechat.thread;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/** 
 * @Package com.yiyao.weixin.thread
 * @ClassName: MyCommandLineRunner
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 孙刚
 * @date 2019年3月28日 下午7:07:26
 */
@Component
public class MyCommandLineRunner implements CommandLineRunner {
	@Value("${wechat.appid}")
	private String appid;
	@Value("${wechat.appsecret}")
	private String appsecret;
	
	/** (non Javadoc)
	 * Title: run
	 * Description: 
	 * @param args
	 * @throws Exception
	 * @see org.springframework.boot.CommandLineRunner#run(java.lang.String[])
	 */
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("==========程序开始==========");
		Thread thread=new AccessTokenThread(appid,appsecret);
        thread.start();
	}

}
