package com.yention.yention_wechat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class YentionWechatApplication {

	public static void main(String[] args) {
		SpringApplication.run(YentionWechatApplication.class, args);
	}

}
