package com.yention.yention_wechat.controller;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yention.yention_wechat.bean.Group;
import com.yention.yention_wechat.service.GroupService;
import com.yention.yention_wechat.thread.AccessTokenThread;

/** 
 * @Package com.yention.yention_wechat.controller
 * @ClassName: GroupController
 * @Description: 分组控制类
 * @author 孙刚
 * @date 2019年4月12日 上午1:12:41
 */
@RestController
@RequestMapping("/group")
public class GroupController {
    @Autowired
    private GroupService groupService;
    private static Logger log = LoggerFactory.getLogger(GroupController.class);

    /**
     * @Title: create
     * @Description: 创建分组
     * @return String   
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create() {
        // 调用接口获取access_token
        String at = AccessTokenThread.accessToken.getToken();
        String b = groupService.createGroup(getGroup(), at);
        String j = JSONObject.fromObject(getGroup()).toString();
        log.info(j);
        return b;
    }

    /**
     * @Title: get
     * @Description: 查询所有分组
     * @return String   
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String get() {
        // 调用接口获取access_token
        String at = AccessTokenThread.accessToken.getToken();
        String b = groupService.getAllGroups(at);
        return b;
    }

    /**
     * @Title: getone
     * @Description: 查询用户所在分组
     * @return String   
     */
    @RequestMapping(value = "/getone",method = RequestMethod.POST)
    public String getone(){
        // 调用接口获取access_token
        String at = AccessTokenThread.accessToken.getToken();
        String b=groupService.getone(getoneuser(),at);
        return b;
    }

    /**
     * @Title: update
     * @Description: 修改分组名
     * @return String   
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update() {
        // 调用接口获取access_token
        String at = AccessTokenThread.accessToken.getToken();
        String b = groupService.updateGroup(updateGroup(), at);
        String j = JSONObject.fromObject(getGroup()).toString();
        log.info(j);
        return b;
    }

    /**
     * @Title: move
     * @Description: 移动用户分组
     * @return String   
     */
    @RequestMapping(value = "/move",method = RequestMethod.POST)
    public String move(){
        // 调用接口获取access_token
        String at = AccessTokenThread.accessToken.getToken();
        String b = groupService.moveUser(moveuser(), at);
        String j = JSONObject.fromObject(getGroup()).toString();
        log.info(j);
        return b;
    }

    //批量移动用户分组
    //暂时不做。

    /**
     * @Title: delete
     * @Description: 删除分组 void   
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public void delete(){
        // 调用接口获取access_token
        String at = AccessTokenThread.accessToken.getToken();
        JSONObject jsonObject=groupService.deleteGroup(deleteGroup(),at);
        int result=jsonObject.getInt("errcode");
        if(result==0){
            log.info("删除成功");
        }else {
            log.error("删除失败，errcode="+String.valueOf(result));
        }
    }

    //创建名为test的组
    public String  getGroup() {
        Group group = new Group();
        group.setName("kashjdsak");
        String a1=JSONObject.fromObject("{\"group\":{\"name\":\""+group.getName()+"\"}}").toString();
        return a1;
    }
    //查询唯一的用户在哪一组
    public Group getoneuser() {
    	Group group = new Group();
    	group.setOpenid("obcuqxGCSU44QIdKCu-vyPFJ5Ssc");
        return group;
    }
    //修改名为test的组为test2
    public String updateGroup() {
    	Group group = new Group();
    	group.setId("100");
    	group.setName("test2");
        String a1=JSONObject.fromObject("{\"group\":{\"id\":"+group.getId()+",\"name\":\""+group.getName()+"\"}}").toString();
        return a1;
    }

    //移动用户
    public Group moveuser() {
    	Group group = new Group();
    	group.setOpenid("obcuqxGCSU44QIdKCu-vyPFJ5Ssc");
    	group.setTo_groupid("100");
        return group;
    }

    //删除id为100的组
    public String deleteGroup() {
    	Group group = new Group();
        group.setId("100");
        group.setName("test2");
        String a1=JSONObject.fromObject("{\"group\":{\"id\":"+group.getId()+"}}").toString();
        return a1;
    }
}
