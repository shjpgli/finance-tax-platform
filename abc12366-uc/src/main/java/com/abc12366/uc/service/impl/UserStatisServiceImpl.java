package com.abc12366.uc.service.impl;

import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.DateUtils;
import com.abc12366.uc.mapper.db2.UserStatisRoMapper;
import com.abc12366.uc.model.User;
import com.abc12366.uc.model.bo.*;
import com.abc12366.uc.service.UserStatisService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
        if (!type.trim().equals("city") && !type.trim().equals("province")) {
            return null;
        }

        String[] timeStr;
        Calendar c1 = Calendar.getInstance();
        Calendar c3 = Calendar.getInstance();
        if(!StringUtils.isEmpty(timeInterval)){
            timeStr = timeInterval.split("～");
            if(timeStr.length!=2||timeStr[0].trim().length()!=(timeStr[1].trim().length())){
                throw new ServiceException(4806);
            }

            if(!StringUtils.isEmpty(timeStr[0])){
                c1.setTime(DateUtils.strToDate(timeStr[0], "yyyy-MM-dd HH:mm:ss"));
            }
            if(!StringUtils.isEmpty(timeStr[1])){
                c3.setTime(DateUtils.strToDate(timeStr[1], "yyyy-MM-dd HH:mm:ss"));
            }
        }

        if(type.trim().equals("province")){
            return userStatisRoMapper.regionProvinceUinfo(StringUtils.isEmpty(timeInterval) ? null : c1.getTime(), StringUtils.isEmpty(timeInterval) ? null : c3.getTime(), province);
        } else if(type.trim().equals("city")){
            return userStatisRoMapper.regionCityUinfo(StringUtils.isEmpty(timeInterval) ? null : c1.getTime(), StringUtils.isEmpty(timeInterval) ? null : c3.getTime(), province);
        }
        return null;
    }

    @Override
    public Object bindCount(String type, String start, String end) {
        if(StringUtils.isEmpty(type)){
            return null;
        }
        Calendar c1 = Calendar.getInstance();
        Calendar c3 = Calendar.getInstance();
        if(!StringUtils.isEmpty(start)){
            c1.setTime(DateUtils.strToDate(start, "yyyy-MM"));
        }
        if(!StringUtils.isEmpty(end)){
            c3.setTime(DateUtils.strToDate(end, "yyyy-MM"));
            c3.add(Calendar.MONTH, 1);
        }
            if(type.trim().equals("all")){
                List<BindStatisAllBO> allBOList = new ArrayList<>();
                allBOList.add(new BindStatisAllBO("dzsb", userStatisRoMapper.bindDzsb(c1.getTime(), c3.getTime())));
                allBOList.add(new BindStatisAllBO("hngs", userStatisRoMapper.bindHngs(c1.getTime(), c3.getTime())));
                allBOList.add(new BindStatisAllBO("hnds", userStatisRoMapper.bindHnds(c1.getTime(), c3.getTime())));
                return allBOList;
            } else if(type.trim().equals("dzsb")||type.trim().equals("hngs")||type.trim().equals("hnds")){
                Calendar c2 = Calendar.getInstance();
                c2.setTime(DateUtils.strToDate(start, "yyyy-MM"));
                int minusYear = c3.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
                int minus = c3.get(Calendar.MONTH) - c1.get(Calendar.MONTH) + 12 * minusYear;
                c2.add(Calendar.MONTH, 1);
                List<BindStatisSingleBO> bindStatisSingleBOList = new ArrayList<>();
                for (int i = 0; i < minus; i++) {
                    BindStatisSingleBO bindStatisSingleBO = new BindStatisSingleBO();
                    bindStatisSingleBO.setTimeInterval(DateUtils.dateToString(c1.getTime(), "yyyy-MM"));
                    bindStatisSingleBO.setType(type);
                    if(type.trim().equals("dzsb")){
                        bindStatisSingleBO.setBindCount(userStatisRoMapper.bindDzsb(c1.getTime(), c2.getTime()));
                    } else if(type.trim().equals("hngs")){
                        bindStatisSingleBO.setBindCount(userStatisRoMapper.bindHngs(c1.getTime(), c2.getTime()));
                    } else if(type.trim().equals("hnds")){
                        bindStatisSingleBO.setBindCount(userStatisRoMapper.bindHnds(c1.getTime(), c2.getTime()));
                    }
                    bindStatisSingleBOList.add(bindStatisSingleBO);
                    c1.add(Calendar.MONTH, 1);
                    c2.add(Calendar.MONTH, 1);
                }
                return bindStatisSingleBOList;
            }
        return null;
    }

    @Override
    public List<BindCountInfo> bindCountInfo(String type, String startStr,String endStr, int page, int size) {
        Date start = DateUtils.strToDate(startStr, "yyyy-MM");
        Calendar c2 = Calendar.getInstance();
        c2.setTime(DateUtils.strToDate(startStr, "yyyy-MM"));
        c2.add(Calendar.MONTH, 1);
        Calendar c3 = Calendar.getInstance();
        if(!StringUtils.isEmpty(endStr)){
            c3.setTime(DateUtils.strToDate(endStr, "yyyy-MM"));
            c3.add(Calendar.MONTH, 1);
        }

        switch (type){
            case "dzsb":
                PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
                return userStatisRoMapper.bindDzsbInfo(start, StringUtils.isEmpty(endStr) ? c2.getTime() : c3.getTime());
            case "hngs":
                PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
                return userStatisRoMapper.bindHngsInfo(start, StringUtils.isEmpty(endStr) ? c2.getTime() : c3.getTime());
            case "hnds":
                PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
                return userStatisRoMapper.bindHndsInfo(start, StringUtils.isEmpty(endStr) ? c2.getTime() : c3.getTime());
        }
        return null;
    }
}
