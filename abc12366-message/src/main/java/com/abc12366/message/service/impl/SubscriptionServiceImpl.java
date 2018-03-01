package com.abc12366.message.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.Utils;
import com.abc12366.message.mapper.db1.SubscriptionMapper;
import com.abc12366.message.mapper.db2.SubscriptionRoMapper;
import com.abc12366.message.model.Subscriptions;
import com.abc12366.message.service.ISubscriptionService;
import com.github.pagehelper.PageHelper;

@Service
public class SubscriptionServiceImpl implements ISubscriptionService{

	@Autowired
	private SubscriptionMapper subscriptionMapper;
	
	@Autowired
	private SubscriptionRoMapper subscriptionRoMapper;

	@Override
	public List<Subscriptions> selectList(Map<String, Object> param, int page, int size) {
		PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
		return subscriptionRoMapper.selectList(param);
	}

	@Override
	public Subscriptions selectOneSetting(String id) {
		return subscriptionRoMapper.selectOneSetting(id);
	}

	@Transactional("db1TxManager")
	public int delOneSetting(String id) {
		//先删除所有订阅了该消息的人
		subscriptionMapper.delUserSetting(id);
		return subscriptionMapper.delOneSetting(id);
	}

	@Transactional("db1TxManager")
	public Subscriptions updateOneSetting(Subscriptions subscriptions) {
		subscriptions.setLastUpdate(new Date());
		subscriptionMapper.updateOneSetting(subscriptions);
		return subscriptions;
	}

	@Transactional("db1TxManager")
	public Subscriptions addOneSetting(Subscriptions subscriptions) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("type", subscriptions.getType());
		param.put("busiType", subscriptions.getBusiType());
		List<Subscriptions> list = subscriptionRoMapper.selectList(param);
		if(list !=null && list.size() > 0){
			throw new ServiceException(9999,"已经存在此消息类型和业务类型相同的消息订阅!");
		}
		Date date = new Date();
		subscriptions.setCreateTime(date);
		subscriptions.setLastUpdate(date);
		subscriptions.setId(Utils.uuid());
		subscriptionMapper.addOneSetting(subscriptions);
		return subscriptions;
	}
}
