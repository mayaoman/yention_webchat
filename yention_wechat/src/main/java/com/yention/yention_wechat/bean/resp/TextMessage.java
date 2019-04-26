package com.yention.yention_wechat.bean.resp;

/** 
 * @Package com.yention.yention_wechat.bean.resp
 * @ClassName: TextMessage
 * @Description: 文本消息
 * @author 孙刚
 * @date 2019年4月11日 下午10:36:12
 */
public class TextMessage extends BaseMessage {
    // 回复的消息内容
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}