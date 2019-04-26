package com.yention.yention_wechat.bean;
/** 
 * @Package com.yention.yention_wechat.bean
 * @ClassName: Tag
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 孙刚
 * @date 2019年4月12日 上午2:15:52
 */
public class Tag {
	private String  id ;//标签id，由微信分配
    private String name;//标签名，UTF8编码
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
    
}
