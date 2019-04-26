package com.yention.yention_wechat.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yention.yention_wechat.bean.Tag;
import com.yention.yention_wechat.service.TagsService;
import com.yention.yention_wechat.util.WeixinUtil;

import net.sf.json.JSONObject;

/** 
 * @Package com.yention.yention_wechat.service.impl
 * @ClassName: TagsServiceImpl
 * @Description: 用户标签业务类
 * @author 孙刚
 * @date 2019年4月12日 上午2:20:34
 */
@Service
public class TagsServiceImpl implements TagsService {
	//创建标签
    private String tags_create_url ="https://api.weixin.qq.com/cgi-bin/tags/create?access_token=ACCESS_TOKEN";
    //获取公众号已创建的标签
    private String tags_get_url ="https://api.weixin.qq.com/cgi-bin/tags/get?access_token=ACCESS_TOKEN";
    //编辑标签
    private String tags_update_url ="https://api.weixin.qq.com/cgi-bin/tags/update?access_token=ACCESS_TOKEN";
    //删除标签
    private String tags_delete_url ="https://api.weixin.qq.com/cgi-bin/tags/delete?access_token=ACCESS_TOKEN";
    //获取标签下粉丝列表
    private String tags_user_get_url ="https://api.weixin.qq.com/cgi-bin/user/tag/get?access_token=ACCESS_TOKEN";
    //批量为用户打标签
    private String tags_batchtagging_url ="https://api.weixin.qq.com/cgi-bin/tags/members/batchtagging?access_token=ACCESS_TOKEN";
    //批量为用户取消标签
    private String tags_batchuntagging_url ="https://api.weixin.qq.com/cgi-bin/tags/members/batchuntagging?access_token=ACCESS_TOKEN";
    //获取用户身上的标签列表
    private String tags_getidlist_url ="https://api.weixin.qq.com/cgi-bin/tags/getidlist?access_token=ACCESS_TOKEN";
    
    
	/** (non Javadoc)
	 * Title: createTags
	 * Description: 
	 * @param tag
	 * @param access_token
	 * @return
	 * @see com.yention.yention_wechat.service.TagsService#createTags(com.yention.yention_wechat.bean.Tag, java.lang.String)
	 */
	@Override
	public String createTags(Tag tag, String access_token) {
		String url=tags_create_url.replace("ACCESS_TOKEN",access_token);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("tag", tag);
        String json= JSONObject.fromObject(map).toString();
        JSONObject jsonObject= WeixinUtil.httpRequest(url,"POST",json);
        return String.valueOf(jsonObject);
	}

	/** (non Javadoc)
	 * Title: getTags
	 * Description: 
	 * @param access_token
	 * @return
	 * @see com.yention.yention_wechat.service.TagsService#getTags(java.lang.String)
	 */
	@Override
	public String getTags(String access_token) {
		String url=tags_get_url.replace("ACCESS_TOKEN",access_token);
        JSONObject jsonObject= WeixinUtil.httpRequest(url,"GET",null);
        return String.valueOf(jsonObject);
	}

	/** (non Javadoc)
	 * Title: updateTags
	 * Description: 
	 * @param tag
	 * @param access_token
	 * @return
	 * @see com.yention.yention_wechat.service.TagsService#updateTags(com.yention.yention_wechat.bean.Tag, java.lang.String)
	 */
	@Override
	public String updateTags(Tag tag, String access_token) {
		String url=tags_update_url.replace("ACCESS_TOKEN",access_token);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("tag", tag);
        String json= JSONObject.fromObject(map).toString();
        JSONObject jsonObject= WeixinUtil.httpRequest(url,"POST",json);
        return String.valueOf(jsonObject);
	}

	/** (non Javadoc)
	 * Title: deleteTags
	 * Description: 
	 * @param tag
	 * @param access_token
	 * @return
	 * @see com.yention.yention_wechat.service.TagsService#deleteTags(com.yention.yention_wechat.bean.Tag, java.lang.String)
	 */
	@Override
	public String deleteTags(Tag tag, String access_token) {
		String url=tags_delete_url.replace("ACCESS_TOKEN",access_token);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("tag", tag);
        String json= JSONObject.fromObject(map).toString();
        JSONObject jsonObject= WeixinUtil.httpRequest(url,"POST",json);
        return String.valueOf(jsonObject);
	}

	/** (non Javadoc)
	 * Title: getUserByTags
	 * Description: 
	 * @param tag
	 * @param access_token
	 * @return
	 * @see com.yention.yention_wechat.service.TagsService#getUserByTags(com.yention.yention_wechat.bean.Tag, java.lang.String)
	 */
	@Override
	public String getUserByTags(String tagid, String access_token) {
		String url=tags_user_get_url.replace("ACCESS_TOKEN",access_token);
        String json= JSONObject.fromObject(tagid).toString();
        JSONObject jsonObject= WeixinUtil.httpRequest(url,"POST",json);
        return String.valueOf(jsonObject);
	}

	/** (non Javadoc)
	 * Title: batchtagging
	 * Description: 
	 * @param tag
	 * @param access_token
	 * @return
	 * @see com.yention.yention_wechat.service.TagsService#batchtagging(com.yention.yention_wechat.bean.Tag, java.lang.String)
	 */
	@Override
	public String batchtagging(String[] openid_list,String tagid, String access_token) {
		String url=tags_batchtagging_url.replace("ACCESS_TOKEN",access_token);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("openid_list", openid_list);
		map.put("tagid", tagid);
        String json= JSONObject.fromObject(map).toString();
        JSONObject jsonObject= WeixinUtil.httpRequest(url,"POST",json);
        return String.valueOf(jsonObject);
	}

	/** (non Javadoc)
	 * Title: batchuntagging
	 * Description: 
	 * @param tag
	 * @param access_token
	 * @return
	 * @see com.yention.yention_wechat.service.TagsService#batchuntagging(com.yention.yention_wechat.bean.Tag, java.lang.String)
	 */
	@Override
	public String batchuntagging(String[] openid_list,String tagid, String access_token) {
		String url=tags_batchuntagging_url.replace("ACCESS_TOKEN",access_token);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("openid_list", openid_list);
		map.put("tagid", tagid);
        String json= JSONObject.fromObject(map).toString();
        JSONObject jsonObject= WeixinUtil.httpRequest(url,"POST",json);
        return String.valueOf(jsonObject);
	}

	/** (non Javadoc)
	 * Title: getTagsByUser
	 * Description: 
	 * @param openId
	 * @param access_token
	 * @return
	 * @see com.yention.yention_wechat.service.TagsService#getTagsByUser(java.lang.String, java.lang.String)
	 */
	@Override
	public String getTagsByUser(String openId, String access_token) {
		String url=tags_getidlist_url.replace("ACCESS_TOKEN",access_token);
        String json= JSONObject.fromObject(openId).toString();
        JSONObject jsonObject= WeixinUtil.httpRequest(url,"POST",json);
        return String.valueOf(jsonObject);
	}

}
