package com.abc12366.cszj.mapper.db2;

import java.util.List;

import com.abc12366.cszj.model.weixin.bo.message.News;

public interface WxMsgRoMapper {

	List<News> getNews(News news);

}
