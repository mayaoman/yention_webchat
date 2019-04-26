package com.yention.yention_wechat.service.impl;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yention.yention_wechat.bean.Menu;
import com.yention.yention_wechat.service.MenuService;
import com.yention.yention_wechat.util.WeixinUtil;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 
 * @Package com.yention.yention_wechat.service.impl
 * @ClassName: MenuSeviceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 孙刚
 * @date 2019年4月12日 上午2:35:51
 */
@Service
public class MenuSeviceImpl implements MenuService {
    private static Logger log = LoggerFactory.getLogger(MenuSeviceImpl.class);

    // 菜单创建（POST） 限1000（次/天）
    public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

    // 菜单查询（POST） 限10000（次/天）
    public static String menu_get_url = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";

    // 菜单删除（POST） 限1000（次/天）
    public static String menu_delete_url = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
    
    @Value("${wechat.appid}")
	private String appid;
    
    /**
     * 查询菜单
     * @param accessToken 有效的access_token
     * @return
     */
    public JSONObject getMenu(String accessToken) {
        // 拼装创建菜单的url
        String url = menu_get_url.replace("ACCESS_TOKEN", accessToken);
        // 调用接口查询菜单
        JSONObject jsonObject = WeixinUtil.httpRequest(url, "GET", null);
        return jsonObject;
    }
    /**
     * 创建菜单(替换旧菜单)
     * @param accessToken 有效的access_token
     * @return 0表示成功，其他值表示失败
     */
    public int createMenu(String accessToken) {
        int result = 0;
        Map<String, Object> wechatMenuMap = new HashMap<String, Object>();
        List<Map<String, Object>> wechatMenuMapList = new ArrayList<Map<String, Object>>();
//        Map<String, Object> wechatMenuMapList=new HashedMap();
        //List<Menu> menuList = menuMapper.findFristMenu(accountid);//返回全部父级菜单集合（根据sort排序）
        String menUrl = createAuth2Url("http://localhost:8081/tcm_dist_wx/personalCenter.html",appid);
        List<Menu> menuList = new ArrayList<Menu>();
        Menu menu1 = new Menu();
        menu1.setId("1");
        menu1.setName("找医生");
        menu1.setKey(menUrl);
        menu1.setType("view");
        Menu menu2 = new Menu();
        menu2.setId("2");
        menu2.setName("我的医生");
        menu2.setKey(menUrl);
        menu2.setType("view");
        Menu menu3 = new Menu();
        menu3.setId("3");
        menu3.setName("个人中心");
        menu3.setKey(menUrl);
        menu3.setType("view");
        menuList.add(menu1);
        menuList.add(menu2);
        menuList.add(menu3);
        for(Menu menu : menuList){
            //String id = menu.getId();
            String name = menu.getName();
            String type = menu.getType();
            String key = menu.getKey();

            Map<String, Object> menuMap = new HashMap<String, Object>();

            //List<Menu> parentMenuList = menuMapper.findByParentId(id);//返回全部子级菜单集合（根据sort排序）
            List<Menu> parentMenuList = new ArrayList<Menu>();
//            Menu menu3 = new Menu();
//            menu3.setName("个人中心");
//            menu3.setKey("http://v8qmnm.natappfree.cc/order/init");
//            menu3.setType("view");
//            Menu menu4 = new Menu();
//            menu4.setName("地图导航");
//            menu4.setKey("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxe423086720d7a588&response_type=code&scope=snsapi_base&redirect_uri=http%3A%2F%2Fhhys.yilife.com%2FmedicalO2O%2Fwechat%2Fforword%2FwechatUserCenter&state=_1#wechat_redirect");
//            menu4.setType("view");
//            parentMenuList.add(menu3);
//            parentMenuList.add(menu4);
            if(parentMenuList.size() > 0){ //如果有子菜单

                List<Map<String, Object>> subMenuMapList = new ArrayList<Map<String, Object>>();

                for(Menu subMenu : parentMenuList){ //循环子菜单

                    String subName = subMenu.getName();
                    String subType = subMenu.getType();
                    String subKey = subMenu.getKey();
                    subMenuMapList.add(createSubMap(subName, subType, subKey));
                }

                menuMap.put("name", name);
                menuMap.put("sub_button", subMenuMapList);

            } else {

                menuMap.put("name", name);
                menuMap.put("type", type);
                if(type.equals("view")){
                    menuMap.put("url", key);
                } else {
                    menuMap.put("key", key);
                }
            }
            wechatMenuMapList.add(menuMap);
        }
        wechatMenuMap.put("button", wechatMenuMapList);
        // 拼装创建菜单的url
        String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);
        // 将菜单对象转换成json字符串
        String jsonMenu = JSONObject.fromObject(wechatMenuMap).toString();
        // 调用接口创建菜单
        JSONObject jsonObject = WeixinUtil.httpRequest(url, "POST", jsonMenu);

        if (null != jsonObject) {
            if (0 != jsonObject.getInt("errcode")) {
                result = jsonObject.getInt("errcode");
                log.error("创建菜单失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
                log.error("****"+jsonMenu+"****");
            }
        }
        return result;
    }

    private Map<String,Object> createSubMap(String title, String type, String key){
        Map<String,Object> m = new HashMap<String, Object>();
        m.put("type", type);
        m.put("name",title);
        if("view".equals(type)){
            m.put("url", key);
        }else if("click".equals(type)){
            m.put("key", key);
        }else {
            m.put("key", key);
        }

        return m;
    }
    /**
     * 删除菜单
     *
     * @param accessToken 有效的access_token
     * @return 0表示成功，其他值表示失败
     */
    public int deleteMenu(String accessToken) {
        int result = 0;

        // 拼装创建菜单的url
        String url = menu_delete_url.replace("ACCESS_TOKEN", accessToken);

        // 调用接口创建菜单
        JSONObject jsonObject = WeixinUtil.httpRequest(url, "POST", null);

        if (null != jsonObject) {
            if (0 != jsonObject.getInt("errcode")) {
                result = jsonObject.getInt("errcode");
                log.error("删除菜单失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
            }
        }
        return result;
    }
    
    private String createAuth2Url(String forword, String appId){
		try{
			StringBuffer authUrl=new StringBuffer()
					.append("https://open.weixin.qq.com/connect/oauth2/authorize?")
					.append("appid=").append(appId)
					.append("&response_type=code")
					.append("&scope=snsapi_base")
					.append("&redirect_uri=");

			StringBuffer sb = new StringBuffer()
					.append(authUrl.toString())
					.append(URLEncoder.encode(forword.toString(), "utf-8"))
					.append("&state=1")
					.append("#wechat_redirect");

			return sb.toString();
		}catch(Exception e){
			return "";
		}
	}
}
