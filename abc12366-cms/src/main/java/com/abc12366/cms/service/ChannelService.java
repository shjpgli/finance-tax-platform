package com.abc12366.cms.service;


import com.abc12366.cms.model.Channel;
import com.abc12366.cms.vo.ChannelVO;
import com.abc12366.cms.vo.SiteVO;

import java.util.List;

public interface ChannelService {

    List<ChannelVO> selectList();

    int update(ChannelVO channelVO);

    ChannelVO selectOneById(String channel);

    List<Channel> selectListByParam(Channel channel);
}
