package com.abc12366.message.mapper.db2;

import java.util.List;
import java.util.Map;

import com.abc12366.message.model.Subscriptions;

public interface SubscriptionRoMapper {

	List<Subscriptions> selectList(Map<String, Object> param);

	Subscriptions selectOneSetting(String id);

}
