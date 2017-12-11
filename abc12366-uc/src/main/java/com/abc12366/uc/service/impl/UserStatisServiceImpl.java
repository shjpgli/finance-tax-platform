package com.abc12366.uc.service.impl;

import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.DateUtils;
import com.abc12366.uc.mapper.db2.UserStatisRoMapper;
import com.abc12366.uc.model.User;
import com.abc12366.uc.model.bo.RigionStatisBO;
import com.abc12366.uc.model.bo.TagUserStaticBO;
import com.abc12366.uc.service.UserStatisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-12-07
 * Time: 10:56
 */
@Service
public class UserStatisServiceImpl implements UserStatisService {
    @Autowired
    private UserStatisRoMapper userStatisRoMapper;

    @Override
    public List<TagUserStaticBO> tag(String type, String startStr, String endStr, String tagName) {
        if (StringUtils.isEmpty(type) || StringUtils.isEmpty(startStr) || StringUtils.isEmpty(endStr)) {
            return null;
        }
        if (!type.equals("year") && !type.trim().equals("month") && !type.trim().equals("day")) {
            return null;
        }
        Calendar c1 = Calendar.getInstance();
        Calendar c3 = Calendar.getInstance();

        if (type.equals("year")) {
            c1.setTime(DateUtils.strToDate(startStr, "yyyy"));
            c3.setTime(DateUtils.strToDate(endStr, "yyyy"));
        } else if (type.trim().equals("month")) {
            c1.setTime(DateUtils.strToDate(startStr, "yyyy-MM"));
            c3.setTime(DateUtils.strToDate(endStr, "yyyy-MM"));
        } else if (type.trim().equals("day")) {
            c1.setTime(DateUtils.strToDate(startStr, "yyyy-MM-dd"));
            c3.setTime(DateUtils.strToDate(endStr, "yyyy-MM-dd"));
        }
        String[] tags = tagName.split(",");
        List<TagUserStaticBO> tagUserStaticBOList = new ArrayList<>();
        for (int i = 0; i < tags.length; i++) {
            List<User> tagUsers = userStatisRoMapper.tagCountUsers(c1.getTime(), c3.getTime(), tags[i]);
            TagUserStaticBO tagUserStaticBO = new TagUserStaticBO();
            tagUserStaticBO.setTagName(tags[i]);
            tagUserStaticBO.setCount(tagUsers == null ? 0 : tagUsers.size());
            tagUserStaticBOList.add(tagUserStaticBO);
        }
        return tagUserStaticBOList;
    }

    @Override
    public List<User> userTagUinfo(String type, String startStr, String endStr, String tagName) {
        if (StringUtils.isEmpty(type) || StringUtils.isEmpty(startStr) || StringUtils.isEmpty(endStr)) {
            return null;
        }
        if (!type.trim().equals("year") && !type.trim().equals("month") && !type.trim().equals("day")) {
            return null;
        }
        Calendar c1 = Calendar.getInstance();
        Calendar c3 = Calendar.getInstance();

        if (type.equals("year")) {
            c1.setTime(DateUtils.strToDate(startStr, "yyyy"));
            c3.setTime(DateUtils.strToDate(endStr, "yyyy"));
        } else if (type.trim().equals("month")) {
            c1.setTime(DateUtils.strToDate(startStr, "yyyy-MM"));
            c3.setTime(DateUtils.strToDate(endStr, "yyyy-MM"));
        } else if (type.trim().equals("day")) {
            c1.setTime(DateUtils.strToDate(startStr, "yyyy-MM-dd"));
            c3.setTime(DateUtils.strToDate(endStr, "yyyy-MM-dd"));
        }
        return userStatisRoMapper.tagCountUsers(c1.getTime(), c3.getTime(), tagName);
    }

    @Override
    public List<RigionStatisBO> region(String type, String start, String end, String province) {
        if (StringUtils.isEmpty(type)) {
            return null;
        }
        if (!type.trim().equals("country") && !type.trim().equals("province")) {
            return null;
        }
        Calendar c1 = Calendar.getInstance();
        Calendar c3 = Calendar.getInstance();
        if(!StringUtils.isEmpty(start)){
            c1.setTime(DateUtils.strToDate(start, "yyyy-MM"));
        }
        if(!StringUtils.isEmpty(end)){
            c3.setTime(DateUtils.strToDate(end, "yyyy-MM"));
        }
        if(type.trim().equals("country")){
            return userStatisRoMapper.regionCountry(StringUtils.isEmpty(start)?null:c1.getTime(), StringUtils.isEmpty(end)?null:c3.getTime());
        } else if(type.trim().equals("province")){
            return userStatisRoMapper.regionProvince(StringUtils.isEmpty(start)?null:c1.getTime(), StringUtils.isEmpty(end)?null:c3.getTime(), province);
        }
        return null;
    }

    @Override
    public List<User> regionUinfo(String type, String timeInterval, String province) {
        if (StringUtils.isEmpty(type)) {
            return null;
        }
        if (!type.trim().equals("country") && !type.trim().equals("province")) {
            return null;
        }
        String[] timeStr = timeInterval.split("～");
        if(timeStr.length!=2||timeStr[0].trim().length()!=(timeStr[1].trim().length())){
            throw new ServiceException(4806);
        }
        Calendar c1 = Calendar.getInstance();
        Calendar c3 = Calendar.getInstance();
        if(!StringUtils.isEmpty(timeStr[0])){
            c1.setTime(DateUtils.strToDate(timeStr[0], "yyyy-MM-dd HH:mm:ss"));
        }
        if(!StringUtils.isEmpty(timeStr[1])){
            c3.setTime(DateUtils.strToDate(timeStr[1], "yyyy-MM-dd HH:mm:ss"));
        }
        if(type.trim().equals("country")){
            return userStatisRoMapper.regionCountryUinfo(StringUtils.isEmpty(timeStr[0])?null:c1.getTime(), StringUtils.isEmpty(timeStr[1])?null:c3.getTime());
        } else if(type.trim().equals("province")){
            return userStatisRoMapper.regionProvinceUinfo(StringUtils.isEmpty(timeStr[0])?null:c1.getTime(), StringUtils.isEmpty(timeStr[1])?null:c3.getTime(), province);
        }
        return null;
    }

    @Override
    public void bindCount(String type, String start, String end) {

    }
}
