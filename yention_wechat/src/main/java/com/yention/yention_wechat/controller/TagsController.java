package com.yention.yention_wechat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yention.yention_wechat.bean.Tag;
import com.yention.yention_wechat.service.TagsService;
import com.yention.yention_wechat.thread.AccessTokenThread;

/** 
 * @Package com.yention.yention_wechat.controller
 * @ClassName: TagsController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 孙刚
 * @date 2019年4月12日 上午2:31:14
 */
@RestController
@RequestMapping("/tags")
public class TagsController {
	@Autowired
    private TagsService tagsService;
	
	/**
     * @Title: get
     * @Description: 获取用户列表
     * @return String   
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String get() {
        // 调用接口获取access_token
        String access_token = AccessTokenThread.accessToken.getToken();
        String tagList = tagsService.getTags(access_token);
        return tagList;
    }
    
    /**
	 * @Title: createTags
	 * @Description: 创建标签
	 * @param tag
	 * @param access_token
	 * @return String   
	 */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
	public String createTags(@RequestBody Tag tag){
    	String access_token = AccessTokenThread.accessToken.getToken();
    	String result = tagsService.createTags(tag, access_token);
        return result;
	}
	/**
	 * @Title: updateTags
	 * @Description: 编辑标签
	 * @param tag
	 * @param access_token
	 * @return String   
	 */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateTags(Tag tag){
    	String access_token = AccessTokenThread.accessToken.getToken();
    	String result = tagsService.updateTags(tag, access_token);
        return result;
	}
	/**
	 * @Title: deleteTags
	 * @Description: 删除标签
	 * @param tag
	 * @param access_token
	 * @return String   
	 */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String deleteTags(Tag tag){
		String access_token = AccessTokenThread.accessToken.getToken();
    	String result = tagsService.deleteTags(tag, access_token);
        return result;
	}
	/**
	 * @Title: getUserByTags
	 * @Description: 获取标签下粉丝列表
	 * @param tag
	 * @param access_token
	 * @return String   
	 */
    @RequestMapping(value = "/getUserByTags", method = RequestMethod.POST)
	public String getUserByTags(String tagid){
    	String access_token = AccessTokenThread.accessToken.getToken();
    	String result = tagsService.getUserByTags(tagid, access_token);
        return result;
	}
	/**
	 * @Title: batchtagging
	 * @Description: 批量为用户打标签
	 * @param tag
	 * @param access_token
	 * @return String   
	 */
    @RequestMapping(value = "/batchtagging", method = RequestMethod.POST)
	public String batchtagging(String[] openid_list,String tagid){
    	String access_token = AccessTokenThread.accessToken.getToken();
    	String result = tagsService.batchtagging(openid_list,tagid, access_token);
        return result;
	}
	/**
	 * @Title: batchuntagging
	 * @Description: 批量为用户取消标签
	 * @param tag
	 * @param access_token
	 * @return String   
	 */
    @RequestMapping(value = "/batchuntagging", method = RequestMethod.POST)
	public String batchuntagging(String[] openid_list,String tagid){
    	String access_token = AccessTokenThread.accessToken.getToken();
    	String result = tagsService.batchuntagging(openid_list,tagid, access_token);
        return result;
	}
	/**
	 * @Title: getTagsByUser
	 * @Description: 获取用户身上的标签列表
	 * @param openId
	 * @param access_token
	 * @return String   
	 */
    @RequestMapping(value = "/getTagsByUser", method = RequestMethod.POST)
	public String getTagsByUser(String openId){
    	String access_token = AccessTokenThread.accessToken.getToken();
    	String result = tagsService.getTagsByUser(openId, access_token);
        return result;
	}
}
