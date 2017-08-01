package com.abc12366.cszj.mapper.db1;


import com.abc12366.cszj.model.weixin.bo.message.Article;
import com.abc12366.cszj.model.weixin.bo.message.News;

public interface WxMsgMapper {

	void insertNews(News news);

	void insertArticle(Article article);

	int updateNews(News news);

	void deleteArticle(String id);
   
}
