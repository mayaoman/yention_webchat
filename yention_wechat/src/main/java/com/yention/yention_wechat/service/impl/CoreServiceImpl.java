package com.yention.yention_wechat.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.yention.yention_wechat.bean.ReplyText;
import com.yention.yention_wechat.bean.resp.Article;
import com.yention.yention_wechat.bean.resp.NewsMessage;
import com.yention.yention_wechat.bean.resp.TextMessage;
import com.yention.yention_wechat.service.CoreService;
import com.yention.yention_wechat.service.UserService;
import com.yention.yention_wechat.thread.AccessTokenThread;
import com.yention.yention_wechat.util.MessageUtil;
import com.yention.yention_wechat.util.RestUtil;
import com.yention.yention_wechat.util.WeixinUtil;

import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
 * @Package com.yention.yention_wechat.service.impl
 * @ClassName: CoreServiceImpl
 * @Description: 公众号核心服务类
 * @author 孙刚
 * @date 2019年4月11日 下午10:52:21
 */
@Service
public class CoreServiceImpl implements CoreService {

    private static Logger log = LoggerFactory.getLogger(CoreServiceImpl.class);
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private UserService userService;

    /**
     * 处理微信发来的请求（包括事件的推送）
     *
     * @param request
     * @return
     */
    public  String processRequest(HttpServletRequest request) {
    	
        String respMessage = null;
        try {
            // 默认返回的文本消息内容
            String respContent = "请求处理异常，请稍候尝试！";
            // xml请求解析
            Map<String, String> requestMap = MessageUtil.parseXml(request);
            // 发送方帐号（open_id）
            String fromUserName =requestMap.get("FromUserName");
            // 公众帐号
            String toUserName = requestMap.get("ToUserName");
            // 消息类型
            String msgType = requestMap.get("MsgType");
            // 回复文本消息
            TextMessage textMessage = new TextMessage();
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setCreateTime(new Date().getTime());
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
            textMessage.setFuncFlag(0);

            // 创建图文消息
            NewsMessage newsMessage = new NewsMessage();
            newsMessage.setToUserName(fromUserName);
            newsMessage.setFromUserName(toUserName);
            newsMessage.setCreateTime(new Date().getTime());
            newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
            newsMessage.setFuncFlag(0);

            List<Article> articleList = new ArrayList<Article>();

            //点击菜单id
            String EventKey =requestMap.get("EventKey");
            // 接收文本消息内容
            String content =requestMap.get("Content");
            log.info("文本消息内容："+content);
            // 自动回复文本消息
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
                  //如果用户发送表情，则回复同样表情。
                  if (isQqFace(content)) {
                      respContent = content;
                      textMessage.setContent(respContent);
                      // 将文本消息对象转换成xml字符串
                      respMessage = MessageUtil.textMessageToXml(textMessage);
                  }else {
                /*
                 *自动回复消息的话先判断此公众号中数据库里是否存在此key，否则回复固定的消息
                 */
                  //回复数据库里面有的自动回复消息
                  String replytest = null;
                  //List<ReplyText> replyTexts = replyTextService.findByAccountId(AccessTokenThread.accountid);
                  List<ReplyText> replyTexts = new ArrayList<ReplyText>();            
                  if (!StringUtils.isEmpty(replyTexts)) {
                      for (ReplyText replyText : replyTexts) {
                          if (content.equals(replyText.getKeyword())) {
                              replytest = replyText.getContent();
                          }
                      }
                      if (!StringUtils.isEmpty(replytest)) {
                          respContent = replytest;
                          textMessage.setContent(respContent);
                          // 将文本消息对象转换成xml字符串
                          respMessage = MessageUtil.textMessageToXml(textMessage);
                      } else {
                          //回复固定消息
                          switch (content) {
                              case "1": {
                                  StringBuffer buffer = new StringBuffer();
                                  buffer.append("您好，我是小8，请回复数字选择服务：").append("\n\n");
                                  buffer.append("11 可查看测试单图文").append("\n");
                                  buffer.append("12  可测试多图文发送").append("\n");
                                  buffer.append("13  可测试网址").append("\n");
                                  buffer.append("14  获取临时二维码").append("\n\n");
                                  buffer.append("或者您可以尝试发送表情").append("\n\n");
                                  buffer.append("回复“1”显示此帮助菜单").append("\n");
                                  buffer.append("回复“客服”可呼叫在线客服");
                                  respContent = String.valueOf(buffer);
                                  textMessage.setContent(respContent);
                                  respMessage = MessageUtil.textMessageToXml(textMessage);
                                  break;
                              }
                              case "11": {
                                  //测试单图文回复
                                  Article article = new Article();
                                  article.setTitle("微信公众帐号开发教程Java版");
                                  // 图文消息中可以使用QQ表情、符号表情
                                  article.setDescription("这是测试有没有换行\n\n如果有空行就代表换行成功\n\n点击图文可以跳转到百度首页");
                                  // 将图片置为空
                                  article.setPicUrl("http://www.sinaimg.cn/dy/slidenews/31_img/2016_38/28380_733695_698372.jpg");
                                  article.setUrl("http://www.baidu.com");
                                  articleList.add(article);
                                  newsMessage.setArticleCount(articleList.size());
                                  newsMessage.setArticles(articleList);
                                  respMessage = MessageUtil.newsMessageToXml(newsMessage);
                                  break;
                              }
                              case "12": {
                                  //多图文发送
                                  Article article1 = new Article();
                                  article1.setTitle("紧急通知，不要捡这种钱！湛江都已经传疯了！\n");
                                  article1.setDescription("");
                                  article1.setPicUrl("http://www.sinaimg.cn/dy/slidenews/31_img/2016_38/28380_733695_698372.jpg");
                                  article1.setUrl("http://mp.weixin.qq.com/s?__biz=MjM5Njc2OTI4NQ==&mid=2650924309&idx=1&sn=8bb6ae54d6396c1faa9182a96f30b225&chksm=bd117e7f8a66f769dc886d38ca2d4e4e675c55e6a5e01e768b383f5859e09384e485da7bed98&scene=4#wechat_redirect");

                                  Article article2 = new Article();
                                  article2.setTitle("湛江谁有这种女儿，请给我来一打！");
                                  article2.setDescription("");
                                  article2.setPicUrl("http://www.sinaimg.cn/dy/slidenews/31_img/2016_38/28380_733695_698372.jpg");
                                  article2.setUrl("http://mp.weixin.qq.com/s?__biz=MjM5Njc2OTI4NQ==&mid=2650924309&idx=2&sn=d7ffc840c7e6d91b0a1c886b16797ee9&chksm=bd117e7f8a66f7698d094c2771a1114853b97dab9c172897c3f9f982eacb6619fba5e6675ea3&scene=4#wechat_redirect");

                                  Article article3 = new Article();
                                  article3.setTitle("以上图片我就随意放了");
                                  article3.setDescription("");
                                  article3.setPicUrl("http://www.sinaimg.cn/dy/slidenews/31_img/2016_38/28380_733695_698372.jpg");
                                  article3.setUrl("http://mp.weixin.qq.com/s?__biz=MjM5Njc2OTI4NQ==&mid=2650924309&idx=3&sn=63e13fe558ff0d564c0da313b7bdfce0&chksm=bd117e7f8a66f7693a26853dc65c3e9ef9495235ef6ed6c7796f1b63abf1df599aaf9b33aafa&scene=4#wechat_redirect");

                                  articleList.add(article1);
                                  articleList.add(article2);
                                  articleList.add(article3);
                                  newsMessage.setArticleCount(articleList.size());
                                  newsMessage.setArticles(articleList);
                                  respMessage = MessageUtil.newsMessageToXml(newsMessage);
                                  break;
                              }

                              case "13": {
                                  //测试网址回复
                                  respContent = "<a href=\"http://www.baidu.com\">百度主页</a>";
                                  textMessage.setContent(respContent);
                                  // 将文本消息对象转换成xml字符串
                                  respMessage = MessageUtil.textMessageToXml(textMessage);
                                  break;
                              }
                              case "14": {
                                  //测试临时二维码

                                  // 调用接口获取access_token
                                  String at = AccessTokenThread.accessToken.getToken();
                                  //String ticket = qrodeService.add(QrodeController.getTemporaryQrode(), at).getString("ticket");
                                  String ticket = "";
                                  String url = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + ticket;
                                  respContent = "<a href=\"" + url + "\">临时二维码</a>";
                                  textMessage.setContent(respContent);
                                  // 将文本消息对象转换成xml字符串
                                  respMessage = MessageUtil.textMessageToXml(textMessage);
                                  break;
                              }
                              case "客服": {
                                  respContent = "请稍等，正在为您转接客服";

                                  textMessage.setMsgType(MessageUtil.EVENT_TYPE_TRANSFER_CUSTOMER_SERVICE);
                                  textMessage.setContent(respContent);
                                  // 将文本消息对象转换成xml字符串
                                  respMessage = MessageUtil.textMessageToXml(textMessage);
                                  break;

                              }

                              default: {
                                  respContent = "（这是里面的）很抱歉，现在小8暂时无法提供此功能给您使用。\n\n回复“1”显示帮助信息";
                                  textMessage.setContent(respContent);
                                  // 将文本消息对象转换成xml字符串
                                  respMessage = MessageUtil.textMessageToXml(textMessage);
                              }
                          }
                      }

                  } else {
                      //回复固定消息
                      switch (content) {

                          case "1": {
                              StringBuffer buffer = new StringBuffer();
                              buffer.append("您好，我是小8，请回复数字选择服务：").append("\n\n");
                              buffer.append("11 可查看测试单图文").append("\n");
                              buffer.append("12  可测试多图文发送").append("\n");
                              buffer.append("13  可测试网址").append("\n");
                              buffer.append("14  获取临时二维码").append("\n\n");
                              buffer.append("或者您可以尝试发送表情").append("\n\n");
                              buffer.append("回复“1”显示此帮助菜单").append("\n");
                              buffer.append("回复“客服”可呼叫在线客服");
                              respContent = String.valueOf(buffer);
                              textMessage.setContent(respContent);
                              respMessage = MessageUtil.textMessageToXml(textMessage);
                              break;
                          }
                          case "11": {
                              //测试单图文回复
                              Article article = new Article();
                              article.setTitle("微信公众帐号开发教程Java版");
                              // 图文消息中可以使用QQ表情、符号表情
                              article.setDescription("这是测试有没有换行\n\n如果有空行就代表换行成功\n\n点击图文可以跳转到百度首页");
                              // 将图片置为空
                              article.setPicUrl("http://www.sinaimg.cn/dy/slidenews/31_img/2016_38/28380_733695_698372.jpg");
                              article.setUrl("http://www.baidu.com");
                              articleList.add(article);
                              newsMessage.setArticleCount(articleList.size());
                              newsMessage.setArticles(articleList);
                              respMessage = MessageUtil.newsMessageToXml(newsMessage);
                              break;
                          }
                          case "12": {
                              //多图文发送
                              Article article1 = new Article();
                              article1.setTitle("紧急通知，不要捡这种钱！湛江都已经传疯了！\n");
                              article1.setDescription("");
                              article1.setPicUrl("http://www.sinaimg.cn/dy/slidenews/31_img/2016_38/28380_733695_698372.jpg");
                              article1.setUrl("http://mp.weixin.qq.com/s?__biz=MjM5Njc2OTI4NQ==&mid=2650924309&idx=1&sn=8bb6ae54d6396c1faa9182a96f30b225&chksm=bd117e7f8a66f769dc886d38ca2d4e4e675c55e6a5e01e768b383f5859e09384e485da7bed98&scene=4#wechat_redirect");

                              Article article2 = new Article();
                              article2.setTitle("湛江谁有这种女儿，请给我来一打！");
                              article2.setDescription("");
                              article2.setPicUrl("http://www.sinaimg.cn/dy/slidenews/31_img/2016_38/28380_733695_698372.jpg");
                              article2.setUrl("http://mp.weixin.qq.com/s?__biz=MjM5Njc2OTI4NQ==&mid=2650924309&idx=2&sn=d7ffc840c7e6d91b0a1c886b16797ee9&chksm=bd117e7f8a66f7698d094c2771a1114853b97dab9c172897c3f9f982eacb6619fba5e6675ea3&scene=4#wechat_redirect");

                              Article article3 = new Article();
                              article3.setTitle("以上图片我就随意放了");
                              article3.setDescription("");
                              article3.setPicUrl("http://www.sinaimg.cn/dy/slidenews/31_img/2016_38/28380_733695_698372.jpg");
                              article3.setUrl("http://mp.weixin.qq.com/s?__biz=MjM5Njc2OTI4NQ==&mid=2650924309&idx=3&sn=63e13fe558ff0d564c0da313b7bdfce0&chksm=bd117e7f8a66f7693a26853dc65c3e9ef9495235ef6ed6c7796f1b63abf1df599aaf9b33aafa&scene=4#wechat_redirect");

                              articleList.add(article1);
                              articleList.add(article2);
                              articleList.add(article3);
                              newsMessage.setArticleCount(articleList.size());
                              newsMessage.setArticles(articleList);
                              respMessage = MessageUtil.newsMessageToXml(newsMessage);
                              break;
                          }

                          case "13": {
                              //测试网址回复
                              respContent = "<a href=\"http://www.baidu.com\">百度主页</a>";
                              textMessage.setContent(respContent);
                              // 将文本消息对象转换成xml字符串
                              respMessage = MessageUtil.textMessageToXml(textMessage);
                              break;
                          }
                          case "14": {
                              //测试临时二维码

                              // 调用接口获取access_token
                              String at = AccessTokenThread.accessToken.getToken();
                              //String ticket = qrodeService.add(QrodeController.getTemporaryQrode(), at).getString("ticket");
                              String ticket = "";
                              String url = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + ticket;
                              respContent = "<a href=\"" + url + "\">临时二维码</a>";
                              textMessage.setContent(respContent);
                              // 将文本消息对象转换成xml字符串
                              respMessage = MessageUtil.textMessageToXml(textMessage);
                              break;
                          }
                          case "客服": {
                              respContent = "请稍等，正在为您转接客服";

                              textMessage.setMsgType(MessageUtil.EVENT_TYPE_TRANSFER_CUSTOMER_SERVICE);
                              textMessage.setContent(respContent);
                              // 将文本消息对象转换成xml字符串
                              respMessage = MessageUtil.textMessageToXml(textMessage);
                              break;

                          }

                          default: {
                              respContent = "（这是外面的）很抱歉，现在小8暂时无法提供此功能给您使用。\n\n回复“1”显示帮助信息";
                              textMessage.setContent(respContent);
                              // 将文本消息对象转换成xml字符串
                              respMessage = MessageUtil.textMessageToXml(textMessage);
                          }
                      }
                  }
              }
            }else if(msgType.equals(MessageUtil.EVENT_TYPE_TRANSFER_CUSTOMER_SERVICE)){
            	
            }
            // 图片消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
                respContent = "您发送的是图片消息！";
                textMessage.setContent(respContent);
                // 将文本消息对象转换成xml字符串
                respMessage = MessageUtil.textMessageToXml(textMessage);
            }
            // 地理位置消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
                respContent = "您发送的是地理位置消息！";
                textMessage.setContent(respContent);
                // 将文本消息对象转换成xml字符串
                respMessage = MessageUtil.textMessageToXml(textMessage);
            }
            // 链接消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
                respContent = "您发送的是链接消息！";textMessage.setContent(respContent);
                // 将文本消息对象转换成xml字符串
                respMessage = MessageUtil.textMessageToXml(textMessage);

            }
            // 音频消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
                respContent = "您发送的是音频消息！";
                textMessage.setContent(respContent);
                // 将文本消息对象转换成xml字符串
                respMessage = MessageUtil.textMessageToXml(textMessage);
            }
            // 事件推送
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
                // 事件类型
                String eventType =requestMap.get("Event");
                // 订阅
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
                    //测试单图文回复
//                    Article article = new Article();
//                    article.setTitle("谢谢您的关注！祝您开心每一天！");
//                    articleList.add(article);
//                    newsMessage.setArticleCount(articleList.size());
//                    newsMessage.setArticles(articleList);
//                    respMessage = MessageUtil.newsMessageToXml(newsMessage);
                    
                    respContent = "谢谢您的关注！祝您开心每一天！";
                    textMessage.setContent(respContent);
                    // 将文本消息对象转换成xml字符串
                    respMessage = MessageUtil.textMessageToXml(textMessage);
                    
                    String access_token = AccessTokenThread.accessToken.getToken();
                    JSONObject userInfo = userService.getUserInfo(fromUserName, access_token);
                    String subscribe = userInfo.getString("subscribe");
                    String nickname = userInfo.getString("nickname");
                    String headimgurl = userInfo.getString("headimgurl");
                    int sex = userInfo.getInt("sex");
                    System.out.println("subscribe:"+subscribe);
                    System.out.println(userInfo);
                    
                    String url = "http://localhost:9090/tcm-api/user/addUser";
                    Map<String,Object> map = new HashMap<String,Object>();
            		map.put("wxOpenId", fromUserName);
            		map.put("wxNickName", nickname);
            		map.put("headImgUrl", headimgurl);
            		map.put("sex", sex);
                    String json= JSONObject.fromObject(map).toString();
                    
                    HttpHeaders headers = new HttpHeaders();
            		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            		headers.add("Accept", MediaType.APPLICATION_JSON.toString());
            		HttpEntity<String> formEntity = new HttpEntity<String>(json, headers);
            		String jsonResult = restTemplate.postForObject(url , formEntity, String.class);
            		
                    //JSONObject jsonObject = WeixinUtil.httpRequest(url, "POST", json);
                    System.out.println(String.valueOf(jsonResult));
                }
                else if(eventType.equals(MessageUtil.EVENT_TYPE_SCAN)){
                    //测试单图文回复
                    Article article = new Article();
                    article.setTitle("这是已关注用户扫描二维码弹到的界面");
                    // 图文消息中可以使用QQ表情、符号表情
                    article.setDescription("点击图文可以跳转到百度首页");
                    // 将图片置为空
                    article.setPicUrl("http://www.sinaimg.cn/dy/slidenews/31_img/2016_38/28380_733695_698372.jpg");
                    article.setUrl("http://www.baidu.com");
                    articleList.add(article);
                    newsMessage.setArticleCount(articleList.size());
                    newsMessage.setArticles(articleList);
                    respMessage = MessageUtil.newsMessageToXml(newsMessage);
                }
                // 取消订阅
                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
                    // TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
                }
                // 自定义菜单点击事件
                else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
                    switch (EventKey){
                        case "11":{
                            respContent = "您好。您的微信账号尚未建立档案。如果已经有健康系统账号，请先进行账号绑定：<a href=\"http://www.ikdys.com/YilifeHealth/bind.html?wx_id=oJyjMvicT1VBD20kSOvPAvbwd8Lc\">请点击这里</a>。如果新用户请到<a href=\"http://www.yilife.com/\">官网</a>注册";
                            break;
                        }
                        case "12":{
                            respContent = "您好。您的微信账号尚未建立档案。如果已经有健康系统账号，请先进行账号绑定：<a href=\"http://www.ikdys.com/YilifeHealth/bind.html?wx_id=oJyjMvicT1VBD20kSOvPAvbwd8Lc\">请点击这里</a>。如果新用户请到<a href=\"http://www.yilife.com/\">官网</a>注册";
                            break;
                        }
                        case "13":{
                            respContent = "您好,请进行账号绑定：<a href=\"http://www.ikdys.com/YilifeHealth/bind.html?wx_id=oJyjMvicT1VBD20kSOvPAvbwd8Lc\">请点击这里</a>。";
                            break;
                        }
                        case "21":{
                            respContent = "您好，咨询请拨打健康易生活热线：020-81893162";
                            break;
                        }
                        default:{
                            log.error("开发者反馈：EventKey值没找到，它是:"+EventKey);
                            respContent= "很抱歉，此按键功能正在升级无法使用";
                        }
                    }
                    textMessage.setContent(respContent);
                    // 将文本消息对象转换成xml字符串
                    respMessage = MessageUtil.textMessageToXml(textMessage);
                }
                else if(eventType.equals(MessageUtil.EVENT_TYPE_VIEW)){
                    // 对于点击菜单转网页暂时不做推送
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return respMessage;
    }

    /**
     * 判断是否是QQ表情
     *
     * @param content
     * @return
     */
    public static boolean isQqFace(String content) {
        boolean result = false;

        // 判断QQ表情的正则表达式
        String qqfaceRegex = "/::\\)|/::~|/::B|/::\\||/:8-\\)|/::<|/::$|/::X|/::Z|/::'\\(|/::-\\||/::@|/::P|/::D|/::O|/::\\(|/::\\+|/:--b|/::Q|/::T|/:,@P|/:,@-D|/::d|/:,@o|/::g|/:\\|-\\)|/::!|/::L|/::>|/::,@|/:,@f|/::-S|/:\\?|/:,@x|/:,@@|/::8|/:,@!|/:!!!|/:xx|/:bye|/:wipe|/:dig|/:handclap|/:&-\\(|/:B-\\)|/:<@|/:@>|/::-O|/:>-\\||/:P-\\(|/::'\\||/:X-\\)|/::\\*|/:@x|/:8\\*|/:pd|/:<W>|/:beer|/:basketb|/:oo|/:coffee|/:eat|/:pig|/:rose|/:fade|/:showlove|/:heart|/:break|/:cake|/:li|/:bome|/:kn|/:footb|/:ladybug|/:shit|/:moon|/:sun|/:gift|/:hug|/:strong|/:weak|/:share|/:v|/:@\\)|/:jj|/:@@|/:bad|/:lvu|/:no|/:ok|/:love|/:<L>|/:jump|/:shake|/:<O>|/:circle|/:kotow|/:turn|/:skip|/:oY|/:#-0|/:hiphot|/:kiss|/:<&|/:&>";
        Pattern p = Pattern.compile(qqfaceRegex);
        Matcher m = p.matcher(content);
        if (m.matches()) {
            result = true;
        }
        return result;
    }
}

