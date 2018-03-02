package com.abc12366.message.service;

import java.util.List;
import java.util.Map;

import com.abc12366.message.model.Subscriptions;

public interface ISubscriptionService {

	/**
	 * 订阅设置列表
	 * @param param
	 * @param page
	 * @param size
	 * @return
	 */
	List<Subscriptions> selectList(Map<String, Object> param, int page, int size);

	/**
	 * 查询单个订阅设置
	 * @param id
	 * @return
	 */
	Subscriptions selectOneSetting(String id);

	/**
	 * 消息订阅设置删除
	 * @param id
	 * @return
	 */
	int delOneSetting(String id);

	/**
	 * 更新消息订阅设置
	 * @param subscriptions
	 * @return
	 */
	Subscriptions updateOneSetting(Subscriptions subscriptions);

	/**
	 * 新增消息订阅设置
	 * @param subscriptions
	 * @return
	 */
	Subscriptions addOneSetting(Subscriptions subscriptions);

}
