package com.yention.yention_wechat.controller;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yention.yention_wechat.service.MenuService;
import com.yention.yention_wechat.thread.AccessTokenThread;

/** 
 * @Package com.yention.yention_wechat.controller
 * @ClassName: MenuController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 孙刚
 * @date 2019年4月12日 上午2:38:09
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    private static Logger log = LoggerFactory.getLogger(MenuController.class);

    /**
     * @Title: getMenu
     * @Description: 查询全部菜单
     * @return String   
     */
    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public String getMenu() {
        // 调用接口获取access_token
        String at = AccessTokenThread.accessToken.getToken();
        JSONObject jsonObject =null;
        if (at != null) {
            // 调用接口查询菜单
            jsonObject = menuService.getMenu(at);
            // 判断菜单创建结果
            return String.valueOf(jsonObject);
        }
        log.info("token为"+at);
        return "无数据";
    }

    /**
     * @Title: createMenu
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @return int   
     */
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public int createMenu() {
        // 调用接口获取access_token
        String at = AccessTokenThread.accessToken.getToken();
        int result=0;
        if (at != null) {

            // 调用接口创建菜单
            result = menuService.createMenu(at);
            // 判断菜单创建结果
            if (0 == result) {
                log.info("菜单创建成功！");
            } else {
                log.info("菜单创建失败，错误码：" + result);
            }
        }
        return result ;
    }

    /**
     * @Title: deleteMenu
     * @Description: 删除菜单
     * @return int   
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public int deleteMenu() {
        // 调用接口获取access_token
        String at = AccessTokenThread.accessToken.getToken();
        int result=0;
        if (at != null) {
            // 调用接口创建菜单
            result = menuService.deleteMenu(at);
            // 判断菜单创建结果
            if (0 == result) {
                log.info("菜单删除成功！");
            } else {
                log.info("菜单删除失败，错误码：" + result);
            }
        }
        return  result;
    }


//        /**
//         * 组装菜单数据
//         *
//         * @return
//         */
//        private static Menu getMenu1() {
//            CommonButton btn11 = new CommonButton();
//            btn11.setName("个人中心");
//            btn11.setType("click");
//            btn11.setKey("location");
//
//            CommonButton btn12 = new CommonButton();
//            btn12.setName("地图导航");
//            btn12.setType("view");
//            btn12.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxe423086720d7a588&response_type=code&scope=snsapi_base&redirect_uri=http%3A%2F%2Fhhys.yilife.com%2FmedicalO2O%2Fwechat%2Fforword%2FwechatUserCenter&state=_1#wechat_redirect");
//
//            CommonButton btn21 = new CommonButton();
//            btn21.setName("我的预约");
//            btn21.setType("view");
//            btn21.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxe423086720d7a588&response_type=code&scope=snsapi_base&redirect_uri=http%3A%2F%2Fhhys.yilife.com%2FmedicalO2O%2Fwechat%2Fforword%2FwechatBook&state=findMyAppo_1#wechat_redirect");
//
//            CommonButton btn22 = new CommonButton();
//            btn22.setName("预约挂号");
//            btn22.setType("view");
//            btn22.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxe423086720d7a588&response_type=code&scope=snsapi_base&redirect_uri=http%3A%2F%2Fhhys.yilife.com%2FmedicalO2O%2Fwechat%2Fforword%2FwechatBook&state=_1#wechat_redirect");
//
//            CommonButton btn23 = new CommonButton();
//            btn23.setName("添加家人");
//            btn23.setType("view");
//            btn23.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxe423086720d7a588&response_type=code&scope=snsapi_base&redirect_uri=http%3A%2F%2Fhhys.yilife.com%2FmedicalO2O%2Fwechat%2Fforword%2FwechatBook&state=findAddPatient_1#wechat_redirect");
//
//            CommonButton btn24 = new CommonButton();
//            btn24.setName("病情反馈");
//            btn24.setType("view");
//            btn24.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxe423086720d7a588&response_type=code&scope=snsapi_base&redirect_uri=http%3A%2F%2Fhhys.yilife.com%2FmedicalO2O%2Fwechat%2Fforword%2FwechatQuestion&state=choosePatient_1#wechat_redirect");
//
//
//            CommonButton btn31 = new CommonButton();
//            btn31.setName("患者咨询");
//            btn31.setType("click");
//            btn31.setKey("sick");
//
//            CommonButton btn32 = new CommonButton();
//            btn32.setName("app下载");
//            btn32.setType("view");
//            btn32.setUrl("http://hhys.yilife.com/medicalO2O/wechatInfo/download");
//
//            CommonButton btn33 = new CommonButton();
//            btn33.setName("最新班表");
//            btn33.setType("view");
//            btn33.setUrl("http://www.29239207.com/banbiao_img/banbiao.jpeg");
//
//            CommonButton btn34 = new CommonButton();
//            btn34.setName("关于我们");
//            btn34.setType("view");
//            btn34.setUrl("http://hhys.yilife.com/medicalO2O/wechatInfo/home");
//
//            CommonButton btn35= new CommonButton();
//            btn35.setName("健康咨询");
//            btn35.setType("view");
//            btn35.setUrl("http://mp.weixin.qq.com/mp/getmasssendmsg?__biz=MzAxMjA5OTMwMA==#wechat_webview_type=1&wechat_redirect");
//
//
//
//            ComplexButton mainBtn1 = new ComplexButton();
//            mainBtn1.setName("家庭医生");
//            mainBtn1.setSub_button(new CommonButton[] { btn11,btn12});
//
//            ComplexButton mainBtn2 = new ComplexButton();
//            mainBtn2.setName("就诊");
//            mainBtn2.setSub_button(new CommonButton[] {btn21,btn22,btn23,btn24});
//
//            ComplexButton mainBtn3 = new ComplexButton();
//            mainBtn3.setName("咨询服务");
//            mainBtn3.setSub_button(new CommonButton[] { btn31,btn32,btn33,btn34,btn35});
//
//            /**
//             * 这是公众号xiaoqrobot目前的菜单结构，每个一级菜单都有二级菜单项<br>
//             *
//             * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？<br>
//             * 比如，第三个一级菜单项不是“更多体验”，而直接是“幽默笑话”，那么menu应该这样定义：<br>
//             * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 });
//             */
//            Menu menu = new Menu();
//            menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });
//
//            return menu;
//        }
    }

