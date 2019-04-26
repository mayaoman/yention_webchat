package com.yention.yention_wechat.service.impl;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import com.yention.yention_wechat.bean.Group;
import com.yention.yention_wechat.service.GroupService;
import com.yention.yention_wechat.util.WeixinUtil;

/** 
 * @Package com.yention.yention_wechat.service.impl
 * @ClassName: GroupServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 孙刚
 * @date 2019年4月12日 上午1:52:10
 */
@Service
public class GroupServiceImpl implements GroupService {
    //创建分组
    private String groups_create_url ="https://api.weixin.qq.com/cgi-bin/groups/create?access_token=ACCESS_TOKEN";

    //查询所有分组
    private String groups_get_url ="https://api.weixin.qq.com/cgi-bin/groups/get?access_token=ACCESS_TOKEN";

    //查询用户所在分组
    private String groups_get_id_url ="https://api.weixin.qq.com/cgi-bin/groups/getid?access_token=ACCESS_TOKEN";

    //修改分组名
    private String groups_update_url ="https://api.weixin.qq.com/cgi-bin/groups/update?access_token=ACCESS_TOKEN";

    //移动用户分组
    private String user_move_url  ="https://api.weixin.qq.com/cgi-bin/groups/members/update?access_token=ACCESS_TOKEN";

    //批量移动用户分组
    //private String userlist_move_url ="https://api.weixin.qq.com/cgi-bin/groups/members/batchupdate?access_token=ACCESS_TOKEN";

    //删除分组
    private String groups_delete_url ="https://api.weixin.qq.com/cgi-bin/groups/delete?access_token=ACCESS_TOKEN";


    public String createGroup(String group, String access_token){
        String url=groups_create_url.replace("ACCESS_TOKEN",access_token);
        String json= JSONObject.fromObject(group).toString();
        JSONObject jsonObject= WeixinUtil.httpRequest(url,"POST",json);
        return String.valueOf(jsonObject);
    }
    public String getAllGroups(String access_token){
        String url=groups_get_url.replace("ACCESS_TOKEN",access_token);
        JSONObject jsonObject= WeixinUtil.httpRequest(url,"GET",null);
        return String.valueOf(jsonObject);
    }
    public String getone(Group group, String access_token) {
        String url = groups_get_id_url.replace("ACCESS_TOKEN", access_token);
        String json = JSONObject.fromObject(group).toString();
        JSONObject jsonObject = WeixinUtil.httpRequest(url, "POST", json);
        return String.valueOf(jsonObject);
    }
    public String updateGroup(String group, String access_token){
        String url=groups_update_url.replace("ACCESS_TOKEN",access_token);
        String json= JSONObject.fromObject(group).toString();
        JSONObject jsonObject= WeixinUtil.httpRequest(url,"POST",json);
        return String.valueOf(jsonObject);
    }
    public String moveUser(Group group, String access_token){
        String url=user_move_url.replace("ACCESS_TOKEN",access_token);
        String json= JSONObject.fromObject(group).toString();
        JSONObject jsonObject= WeixinUtil.httpRequest(url,"POST",json);
        return String.valueOf(jsonObject);
    }

    public JSONObject deleteGroup(String group, String access_token){
        String url=groups_delete_url.replace("ACCESS_TOKEN",access_token);
        String json= JSONObject.fromObject(group).toString();
        JSONObject jsonObject= WeixinUtil.httpRequest(url,"POST",json);
        return jsonObject;
    }
}
