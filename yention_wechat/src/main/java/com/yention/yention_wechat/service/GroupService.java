package com.yention.yention_wechat.service;

import com.yention.yention_wechat.bean.Group;

import net.sf.json.JSONObject;

/** 
 * @Package com.yention.yention_wechat.service
 * @ClassName: GroupService
 * @Description: 分组业务接口
 * @author 孙刚
 * @date 2019年4月12日 上午1:09:15
 */
public interface GroupService {
    public String createGroup(String group, String access_token);
    public String getAllGroups(String access_token);
    public String getone(Group group, String access_token);
    public String updateGroup(String group, String access_token);
    public String moveUser(Group group, String access_token);
    public JSONObject deleteGroup(String group, String access_token);
}
