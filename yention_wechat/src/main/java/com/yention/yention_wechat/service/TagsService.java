package com.yention.yention_wechat.service;

import com.yention.yention_wechat.bean.Tag;

/** 
 * @Package com.yention.yention_wechat.service
 * @ClassName: TagsService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 孙刚
 * @date 2019年4月12日 上午2:07:26
 */
public interface TagsService {
	/**
	 * @Title: createTags
	 * @Description: 创建标签
	 * @param tag
	 * @param access_token
	 * @return String   
	 */
	public String createTags(Tag tag,String access_token);
	/**
	 * @Title: getTags
	 * @Description: 获取公众号已创建的标签
	 * @param access_token
	 * @return String   
	 */
	public String getTags(String access_token);
	/**
	 * @Title: updateTags
	 * @Description: 编辑标签
	 * @param tag
	 * @param access_token
	 * @return String   
	 */
	public String updateTags(Tag tag,String access_token);
	/**
	 * @Title: deleteTags
	 * @Description: 删除标签
	 * @param tag
	 * @param access_token
	 * @return String   
	 */
	public String deleteTags(Tag tag,String access_token);
	/**
	 * @Title: getUserByTags
	 * @Description: 获取标签下粉丝列表
	 * @param tag
	 * @param access_token
	 * @return String   
	 */
	public String getUserByTags(String tagid,String access_token);
	/**
	 * @Title: batchtagging
	 * @Description: 批量为用户打标签
	 * @param tag
	 * @param access_token
	 * @return String   
	 */
	public String batchtagging(String[] openid_list,String tagid,String access_token);
	/**
	 * @Title: batchuntagging
	 * @Description: 批量为用户取消标签
	 * @param tag
	 * @param access_token
	 * @return String   
	 */
	public String batchuntagging(String[] openid_list,String tagid,String access_token);
	/**
	 * @Title: getTagsByUser
	 * @Description: 获取用户身上的标签列表
	 * @param openId
	 * @param access_token
	 * @return String   
	 */
	public String getTagsByUser(String openId,String access_token);
}
