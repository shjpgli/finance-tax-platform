package com.abc12366.cms.service;


import com.abc12366.cms.model.Channel;
import com.abc12366.cms.vo.ChannelVO;

import java.util.List;

public interface ModelService {

    List<ChannelVO> selectList();

    int update(ChannelVO channelVO);

    ChannelVO selectOneById(String channel);

    List<Channel> selectListByParam(Channel channel);
}
