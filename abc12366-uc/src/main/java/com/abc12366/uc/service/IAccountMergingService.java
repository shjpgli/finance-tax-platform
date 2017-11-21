package com.abc12366.uc.service;

import org.springframework.http.ResponseEntity;

import com.abc12366.uc.model.bo.PointsRuleBO;
import com.abc12366.uc.model.bo.UserBO;

/**
 * 账号合并service
 * @author zhushuai 2017-11-21
 *
 */
public interface IAccountMergingService {

	/**
     * 账户合并 
     * @param mergeUserBO 合并账户信息
     * @param beMergeUserBO 被合并账号信息
     * @param bo //积分规则信息
     * @return
     */
	ResponseEntity merging(UserBO mergeUserBO, UserBO beMergeUserBO,
			PointsRuleBO bo);
}
