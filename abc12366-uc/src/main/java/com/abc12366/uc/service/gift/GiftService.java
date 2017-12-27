package com.abc12366.uc.service.gift;


import com.abc12366.uc.model.gift.Gift;
import com.abc12366.uc.model.gift.UamountLog;
import com.abc12366.uc.model.gift.UgiftLog;
import com.abc12366.uc.model.gift.bo.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 礼物服务
 *
 * @author lizhongwei
 * @create 2017-12-18 3:09 PM
 * @since 1.0.0
 */
public interface GiftService {

    /**
     * 礼物列表查询
     *
     * @param gift {@linkplain Gift Gift}
     * @return List<Gift>
     */
    List<Gift> selectList(Gift gift);

    /**
     * 查看单个礼物实体
     *
     * @param gift {@linkplain Gift Gift}
     * @return GiftBO {@linkplain GiftBO GiftBO}
     */
    GiftBO selectOne(Gift gift);


    /**
     * 新增礼物
     *
     * @param gift
     * @return
     */
    Gift insert(Gift gift);

    /**
     * 更新礼物
     *
     * @param gift Gift
     * @return GiftBO
     */
    GiftBO update(Gift gift);

    /**
     * 根据ID删除礼物
     *
     * @param id 礼物主键
     * @return int 影响记录行数
     */
    int delete(String id);

    /**
     * 根据ID查询礼物
     *
     * @param id 礼物主键
     * @return Gift
     * @see Gift
     */
    Gift selectById(String id);

    /**
     * 根据礼物名称查询礼物
     *
     * @param map
     * @return List<Gift>
     * @see Gift
     */
    Gift selectGiftByGiftId(Map<String, Object> map);

    /**
     * 用户兑换礼物
     *
     * @param map
     * @return
     */
    void buyGift(Map<String, Object> map);

    /**
     * 礼物审核
     *
     * @param giftCheckBO
     */
    void checkGiftBuy(GiftCheckBO giftCheckBO, HttpServletRequest request);

    /**
     * 礼物发货
     *
     * @param giftSendBO
     */
    void sendGift(GiftSendBO giftSendBO);

    /**
     * 前台-用户的礼物申请列表
     *
     * @param map
     * @return
     */
    List<UgiftApplyBO> selectUgiftApplyList(Map<String, Object> map);

    /**
     * 根据用户ID查找礼包金额记录
     *
     * @param map
     * @return
     */
    List<UamountLog> selectUamountLogList(Map<String, Object> map);

    /**
     * 查询申请单日志
     *
     * @param applyId 申请单ID
     * @return 日志列表
     */
    List<UgiftLog> selectApplyLogList(String applyId);
}
