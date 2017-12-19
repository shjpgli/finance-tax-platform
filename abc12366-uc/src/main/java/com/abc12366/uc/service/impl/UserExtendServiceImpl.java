package com.abc12366.uc.service.impl;

import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.RedisConstant;
import com.abc12366.gateway.util.TaskConstant;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.mapper.db1.UserExtendMapper;
import com.abc12366.uc.mapper.db2.UserExtendRoMapper;
import com.abc12366.uc.model.UserExtend;
import com.abc12366.uc.model.bo.UserExtendBO;
import com.abc12366.uc.model.bo.UserExtendUpdateBO;
import com.abc12366.uc.service.TodoTaskService;
import com.abc12366.uc.service.UserBindService;
import com.abc12366.uc.service.UserExtendService;
import com.abc12366.uc.service.UserFeedbackMsgService;
import com.alibaba.fastjson.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author liuguiyao
 * @create 2017-05-05 10:08 AM
 * @since 1.0.0
 */
@Service
public class UserExtendServiceImpl implements UserExtendService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(UserExtendService.class);

	@Autowired
	private UserExtendMapper userExtendMapper;

	@Autowired
	private UserExtendRoMapper userExtendRoMapper;

	@Autowired
	private UserBindService userBindService;

	@Autowired
	private TodoTaskService todoTaskService;

	@Autowired
	private UserFeedbackMsgService userFeedbackMsgService;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Override
	public UserExtendBO selectOne(String userId) {
		LOGGER.info("{}", userId);
		UserExtend userExtend = null;
		if (redisTemplate.hasKey(userId + "_UserExtend")) {
			userExtend = JSONObject.parseObject(redisTemplate.opsForValue()
					.get(userId + "_UserExtend"), UserExtend.class);
		} else {
			userExtend = userExtendRoMapper.selectOne(userId);

		}
		if (userExtend == null) {
			return null;
		}
		redisTemplate.opsForValue().set(userId + "_UserExtend",
				JSONObject.toJSONString(userExtend),
				RedisConstant.USER_INFO_TIME_ODFAY, TimeUnit.DAYS);
		
		if (userExtend.getValidStatus().equals(
				TaskConstant.USER_REALNAME_VALIDATED)) {
			// 首次实名认证任务埋点
			todoTaskService.doTask(userId,
					TaskConstant.SYS_TASK_FIRST_REALNAME_VALIDATE_CODE);
		}
		UserExtendBO userExtendBO = new UserExtendBO();
		BeanUtils.copyProperties(userExtend, userExtendBO);
		LOGGER.info("{}", userExtendBO);
		return userExtendBO;
	}

	@Transactional("db1TxManager")
	@Override
	public UserExtendBO insert(UserExtendBO userExtendBO,
			HttpServletRequest request) {
		if (userExtendBO == null) {
			LOGGER.warn("新增失败，参数：{}" + null);
			throw new ServiceException(4101);
		}
		LOGGER.info("{}", userExtendBO);
		if (userExtendBO.getUserId() != null
				&& !userExtendBO.getUserId().equals("")) {
			UserExtend userExtend = userExtendRoMapper.selectOne(userExtendBO
					.getUserId());
			if (userExtend != null) {
				UserExtendBO userExtendOld = new UserExtendBO();
				BeanUtils.copyProperties(userExtend, userExtendOld);
				return userExtendOld;
			}
			userExtend = new UserExtend();
			BeanUtils.copyProperties(userExtendBO, userExtend);
			userExtend.setCreateTime(new Date());
			userExtend.setLastUpdate(new Date());
			if (userExtendBO.getValidStatus() == null
					|| userExtendBO.getValidStatus().trim().equals("")) {
				userExtend.setValidStatus("0");
			}
			// 调用电子税局实名认证查询接口查询用户实名认证情况，如果已实名，则财税专家直接将该用户置为已实名
			if (userBindService.isRealNameValidatedDzsj(
					userExtendBO.getIdcard(), userExtendBO.getRealName(),
					request)) {
				userExtend.setValidStatus("2");
				userExtend.setValidTime(new Date());
				userExtend.setValidType("0");
				userFeedbackMsgService.realNameValidate(
						userExtendBO.getUserId(), "2");

				// 实名认证 跟新生日和性别
				String idCard = userExtendBO.getIdcard();
				if (idCard.length() == 15) {
					String id17 = idCard.substring(14, 15);
					if (Integer.parseInt(id17) % 2 != 0) {
						userExtend.setSex("1");
					} else {
						userExtend.setSex("0");
					}
					String birthday = idCard.substring(6, 12);
					Date birthdate = null;
					try {
						birthdate = new SimpleDateFormat("yyMMdd")
								.parse(birthday);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					userExtend.setBirthday(birthdate);
				} else if (idCard.length() == 18) {
					String id17 = idCard.substring(16, 17);
					if (Integer.parseInt(id17) % 2 != 0) {
						userExtend.setSex("1");
					} else {
						userExtend.setSex("0");
					}
					String birthday = idCard.substring(6, 14);
					Date birthdate = null;
					try {
						birthdate = new SimpleDateFormat("yyyyMMdd")
								.parse(birthday);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					userExtend.setBirthday(birthdate);
				}

			}
			int result = userExtendMapper.insert(userExtend);
			if (result != 1) {
				LOGGER.warn("新增失败，参数：{}" + userExtend.toString());
				throw new ServiceException(4112);
			}
			
			redisTemplate.opsForValue().set(userExtend.getUserId() + "_UserExtend",
					JSONObject.toJSONString(userExtend),
					RedisConstant.USER_INFO_TIME_ODFAY, TimeUnit.DAYS);

			UserExtendBO userExtendBO1 = new UserExtendBO();
			BeanUtils.copyProperties(userExtend, userExtendBO1);
			LOGGER.info("{}", userExtendBO1);
			return userExtendBO1;
		}
		return null;
	}

	@Override
	public UserExtendBO delete(String userId) {
		LOGGER.info("{}", userId);
		UserExtend userExtend = userExtendRoMapper.selectOne(userId);
		if (userExtend == null) {
			LOGGER.warn("删除失败，参数：{}" + userId);
			throw new ServiceException(4103);
		}
		int result = userExtendMapper.delete(userId);
		if (result != 1) {
			LOGGER.warn("删除失败，参数：{}" + userId);
			throw new ServiceException(4103);
		}
		
		// 删除redis用户信息
		redisTemplate.delete(userId + "_UserExtend");
		
		UserExtendBO userExtendBO = new UserExtendBO();
		BeanUtils.copyProperties(userExtend, userExtendBO);
		LOGGER.info("{}", userExtendBO);
		return userExtendBO;
	}

	@Override
	public UserExtendBO update(UserExtendUpdateBO userExtendUpdateBO,
			HttpServletRequest request) throws IOException {
		LOGGER.info("{}", userExtendUpdateBO);
		if (userExtendUpdateBO == null) {
			LOGGER.warn("修改失败，参数：{}" + null);
			throw new ServiceException(4102);
		}
		// 不允许修改非当前登录用户数据
		if (!userExtendUpdateBO.getUserId().trim()
				.equals(Utils.getUserId(request).trim())) {
			throw new ServiceException(4190);
		}
		if (!userExtendUpdateBO.getUserId().equals("")) {
			UserExtend userExtend = userExtendRoMapper
					.selectOne(userExtendUpdateBO.getUserId());
			if (userExtend == null) {
				UserExtendBO userExtendBO = new UserExtendBO();
				BeanUtils.copyProperties(userExtendUpdateBO, userExtendBO);
				return insert(userExtendBO, request);
			}
			UserExtend userExtendSecond = new UserExtend();
			BeanUtils.copyProperties(userExtendUpdateBO, userExtendSecond);
			userExtendSecond.setLastUpdate(new Date());
			// 调用电子税局实名认证查询接口查询用户实名认证情况，如果已实名，则财税专家直接将该用户置为已实名
			if (userBindService.isRealNameValidatedDzsj(
					userExtendUpdateBO.getIdcard(),
					userExtendUpdateBO.getRealName(), request)) {
				userExtendSecond.setValidStatus("2");
				userExtendSecond.setValidTime(new Date());
				userExtendSecond.setValidType("0");
				userFeedbackMsgService.realNameValidate(
						userExtendUpdateBO.getUserId(), "2");

				// 实名认证 跟新生日和性别
				String idCard = userExtendUpdateBO.getIdcard();
				if (idCard.length() == 15) {
					String id17 = idCard.substring(14, 15);
					if (Integer.parseInt(id17) % 2 != 0) {
						userExtendSecond.setSex("1");
					} else {
						userExtendSecond.setSex("0");
					}
					String birthday = idCard.substring(6, 12);
					Date birthdate = null;
					try {
						birthdate = new SimpleDateFormat("yyMMdd")
								.parse(birthday);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					userExtendSecond.setBirthday(birthdate);
				} else if (idCard.length() == 18) {
					String id17 = idCard.substring(16, 17);
					if (Integer.parseInt(id17) % 2 != 0) {
						userExtendSecond.setSex("1");
					} else {
						userExtendSecond.setSex("0");
					}
					String birthday = idCard.substring(6, 14);
					Date birthdate = null;
					try {
						birthdate = new SimpleDateFormat("yyyyMMdd")
								.parse(birthday);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					userExtendSecond.setBirthday(birthdate);
				}

			}
			int result = userExtendMapper.update(userExtendSecond);
			UserExtendBO userExtendBO = new UserExtendBO();

			if (result < 1) {
				LOGGER.warn("修改失败，参数：{}" + userExtendUpdateBO);
				throw new ServiceException(4102);
			}
			// 删除redis用户信息
			redisTemplate.delete(userExtendUpdateBO.getUserId() + "_UserExtend");
			
			UserExtend userExtend2 = userExtendRoMapper
					.selectOne(userExtendSecond.getUserId());
			BeanUtils.copyProperties(userExtend2, userExtendBO);
			LOGGER.info("{}", userExtendBO);
			return userExtendBO;
		}
		return null;
	}

}
