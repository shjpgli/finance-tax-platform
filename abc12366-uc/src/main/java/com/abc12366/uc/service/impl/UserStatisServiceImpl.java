package com.abc12366.uc.service.impl;

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
    public RigionStatisBO region(String type, String start, String end) {
        if (StringUtils.isEmpty(type) || StringUtils.isEmpty(start) || StringUtils.isEmpty(end)) {
            return null;
        }
        if (!type.trim().equals("country") && !type.trim().equals("province")) {
            return null;
        }
        Calendar c1 = Calendar.getInstance();
        Calendar c3 = Calendar.getInstance();
        c1.setTime(DateUtils.strToDate(start, "yyyy-MM"));
        c3.setTime(DateUtils.strToDate(end, "yyyy-MM"));
        if(type.trim().equals("country")){
            return userStatisRoMapper.regionCountry(c1.getTime(), c3.getTime());
        } else if(type.trim().equals("province")){
            return userStatisRoMapper.regionProvince(c1.getTime(), c3.getTime());
        }
        return null;
    }
}
