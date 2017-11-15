package com.abc12366.bangbang.service.impl;

import com.abc12366.bangbang.mapper.db1.QuestionFactionMapper;
import com.abc12366.bangbang.mapper.db1.QuestionFactionRewardSettingMapper;
import com.abc12366.bangbang.mapper.db2.QuestionFactionMemberRoMapper;
import com.abc12366.bangbang.mapper.db2.QuestionFactionRewardSettingRoMapper;
import com.abc12366.bangbang.model.Message;
import com.abc12366.bangbang.model.question.QuestionFactionMember;
import com.abc12366.bangbang.model.question.QuestionFactionRewardSetting;
import com.abc12366.bangbang.model.question.bo.QuestionFactionRewardSettingBo;
import com.abc12366.bangbang.model.question.bo.QuestionFactionRewardSettingParamBo;
import com.abc12366.bangbang.service.MessageSendUtil;
import com.abc12366.bangbang.service.QuestionFactionRewardSettingService;
import com.abc12366.gateway.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author liuQi
 * @Date 2017/10/28 15:33
 */
@Service
public class QuestionFactionRewardSettingServiceImpl implements QuestionFactionRewardSettingService {

    @Autowired
    private QuestionFactionMapper questionFactionMapper;

    @Autowired
    private QuestionFactionMemberRoMapper questionFactionMemberRoMapper;

    @Autowired
    private QuestionFactionRewardSettingMapper questionFactionRewardSettingMapper;

    @Autowired
    private QuestionFactionRewardSettingRoMapper questionFactionRewardSettingRoMapper;

    @Autowired
    private MessageSendUtil messageSendUtil;

    @Override
    public List<QuestionFactionRewardSettingBo> selectFactionRewardSettingList(QuestionFactionRewardSettingParamBo param) {
        return questionFactionRewardSettingRoMapper.selectFactionRewardSettingList(param);
    }

    @Transactional("db1TxManager")
    @Override
    public void setting(QuestionFactionRewardSetting record, HttpServletRequest request) {
        record.setId(Utils.uuid());
        record.setUpdateAdmin(Utils.getAdminId());
        questionFactionRewardSettingMapper.insert(record);
        questionFactionMapper.updateAwardPoint(record.getFactionId(), record.getRewardsPoints());

        QuestionFactionMember leader = questionFactionMemberRoMapper.selectFactionLeader(record.getFactionId());
        Message message = new Message();
        message.setUserId(leader.getUserId());
        message.setContent("恭喜您！系统给您的帮派分配了"+record.getRewardsPoints()+"积分！");
        message.setType("1");
        message.setBusinessId(record.getId());
        messageSendUtil.sendMessage(message, request);
    }
}
