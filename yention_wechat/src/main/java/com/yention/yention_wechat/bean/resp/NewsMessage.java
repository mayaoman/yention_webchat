package com.yention.yention_wechat.bean.resp;
import java.util.List;

/** 
 * @Package com.yention.yention_wechat.bean.resp
 * @ClassName: NewsMessage
 * @Description: 多图文消息
 * @author 孙刚
 * @date 2019年4月11日 下午10:38:42
 */
public class NewsMessage extends BaseMessage {
    // 图文消息个数，限制为10条以内
    private int ArticleCount;
    // 多条图文消息信息，默认第一个item为大图
    private List<Article> Articles;

    public int getArticleCount() {
        return ArticleCount;
    }

    public void setArticleCount(int articleCount) {
        ArticleCount = articleCount;
    }

    public List<Article> getArticles() {
        return Articles;
    }

    public void setArticles(List<Article> articles) {
        Articles = articles;
    }
}