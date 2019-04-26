package com.yention.yention_wechat.bean;

/** 
 * @Package com.yention.yention_wechat.bean
 * @ClassName: User
 * @Description: 分组实体类
 * @author 孙刚
 * @date 2019年4月12日 上午1:04:36
 */
public class Group {
    private String  access_token; //调用接口凭证
    private String  id ;//分组id，由微信分配
    private String name;//分组名字（30个字符以内）
    private Group[] group ;//公众平台分组信息列表
    private String  count ;//分组内用户数量
    private String  openid ;//用户的OpenID
    private String  groupid ;//用户所属的groupid
    private String  to_groupid ;//	分组id

    public String getAccess_token() {
        return access_token;
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCount() {
        return count;
    }

    public String getOpenid() {
        return openid;
    }

    public String getGroupid() {
        return groupid;
    }

    public String getTo_groupid() {
        return to_groupid;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }


    public void setId(String id) {
        this.id = id;
    }

    public Group[] getGroup() {
		return group;
	}


	public void setGroup(Group[] group) {
		this.group = group;
	}


	public void setCount(String count) {
        this.count = count;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public void setTo_groupid(String to_groupid) {
        this.to_groupid = to_groupid;
    }
}
