package com.abc12366.bangbang.mapper.db1;

import com.abc12366.bangbang.model.question.QuestionFactionRewardSetting;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * QuestionFactionRewardSettingMapper数据库操作接口类
 * 
 **/

public interface QuestionFactionRewardSettingMapper{

	/**
	 * 
	 * 添加
	 * 
	 **/
	int insert(QuestionFactionRewardSetting record);


}