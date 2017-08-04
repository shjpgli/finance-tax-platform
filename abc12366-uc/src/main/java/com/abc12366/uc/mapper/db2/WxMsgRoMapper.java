package com.abc12366.uc.mapper.db2;

import java.util.List;

import com.abc12366.cszj.model.weixin.bo.message.News;
import com.abc12366.cszj.model.weixin.bo.message.ReturnMsg;

public interface WxMsgRoMapper {

	List<News> getNews(News news);

	ReturnMsg getReMsgOneBysetting(String setting);

}
