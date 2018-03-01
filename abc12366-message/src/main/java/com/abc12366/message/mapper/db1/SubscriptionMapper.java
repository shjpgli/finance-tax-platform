package com.abc12366.message.mapper.db1;

import com.abc12366.message.model.Subscriptions;

public interface SubscriptionMapper {

	int delOneSetting(String id);

	void updateOneSetting(Subscriptions subscriptions);

	void addOneSetting(Subscriptions subscriptions);

	void delUserSetting(String id);

}
