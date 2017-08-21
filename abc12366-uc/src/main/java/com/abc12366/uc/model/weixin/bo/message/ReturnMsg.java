package com.abc12366.uc.model.weixin.bo.message;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;

public class ReturnMsg {
    private String id;
    private String toUserName;
    private String fromUserName;
    private long createTime;
    @NotEmpty
    private String setting; //0:添加消息,1:回复消息,2:关键字消息
    private String keyString;//关键字
    @NotEmpty
    private String msgType;//text,image,news

    //text
    private String content;

    //image 
    private String mediaId;
    private String imgurl;

    //news
    private String newsId;
    private News news;

    private Date createDate;
    private Date lastUpdate;

    public String toWxXml(String toUserName, String fromUserName, long createTime) {
        this.toUserName = toUserName;
        this.fromUserName = fromUserName;
        this.createTime = createTime;
        StringBuffer buffer = new StringBuffer("<xml>");
        buffer.append("<ToUserName><![CDATA[>").append(this.toUserName).append("]]></ToUserName>");
        buffer.append("<FromUserName><![CDATA[>").append(this.fromUserName).append("]]></FromUserName>");
        buffer.append("<CreateTime><![CDATA[>").append(this.createTime).append("]]></CreateTime>");
        buffer.append("<MsgType><![CDATA[>").append(this.msgType).append("]]></MsgType>");
        if ("text".equals(this.msgType)) {
            buffer.append("<Content><![CDATA[>").append(this.content).append("]]></Content>");
        } else if ("image".equals(this.msgType)) {
            buffer.append("<Image><MediaId><![CDATA[>").append(this.mediaId).append("]]></MediaId></Image>");
        } else {
            buffer.append("<ArticleCount><![CDATA[>").append(this.news.getArticleCount()).append
                    ("]]></ArticleCount><Articles>");
            for (Article article : this.news.getArticles()) {
                buffer.append("<item>");
                buffer.append("<Title><![CDATA[>").append(article.getTitle()).append("]]></Title>");
                buffer.append("<Description><![CDATA[>").append(article.getDescription()).append("]]></Description>");
                buffer.append("<PicUrl><![CDATA[>").append(article.getPicUrl()).append("]]></PicUrl>");
                buffer.append("<Url><![CDATA[>").append(article.getUrl()).append("]]></Url>");
                buffer.append("</item>");
            }
            buffer.append("</Articles>");
        }
        buffer.append("</xml>");
        System.out.println("回复微信服务器信息:"+buffer.toString());
        return buffer.toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getSetting() {
        return setting;
    }

    public void setSetting(String setting) {
        this.setting = setting;
    }

    public String getKeyString() {
        return keyString;
    }

    public void setKeyString(String keyString) {
        this.keyString = keyString;
    }


    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }


}
