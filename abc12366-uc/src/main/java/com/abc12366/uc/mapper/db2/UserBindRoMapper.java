package com.abc12366.uc.mapper.db2;

import com.abc12366.uc.model.UserDzsb;
import com.abc12366.uc.model.UserHnds;
import com.abc12366.uc.model.UserHngs;
import com.abc12366.uc.model.abc4000.NSRXX;
import com.abc12366.uc.model.abc4000.NSRXXBO;
import com.abc12366.uc.model.bo.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Admin: liuguiyao<435720953@qq.com>
 * Date: 2017-07-25
 * Time: 16:22
 */
public interface UserBindRoMapper {

    UserDzsb userDzsbSelectById(String id);

    UserHngs userHngsSelectById(String id);

    UserHnds userHndsSelectById(String id);

    List<UserDzsbListBO> getUserDzsbBind(Map<String, String> map);

    List<UserHngsListBO> getUserhngsBind(Map<String, String> map);

    List<UserHndsBO> getUserhndsBind(Map<String, String> map);

    List<NSRXX> selectListByUserId(String userId);

    List<UserHngs> userHngsListExist(UserHngsInsertBO userHngsInsertBO);

    /**
     * 根据'用户ID，纳税人识别号或社会信用代码'查询是否存在绑定信息
     *
     * @param queryParam 纳税人识别号、社会信用代码必传
     * @return 纳税人绑定信息
     */
    List<NSRXXBO> selectListByUserIdAndNsrsbhOrShxydm(UserDzsb queryParam);

    /**
     * 根据'用户ID，纳税人识别号'查询是否存在绑定信息
     *
     * @param queryParam 纳税人识别号必传
     * @return 纳税人绑定信息
     */
    List<NSRXXBO> selectListByUserIdAndNsrsbh(UserDzsb queryParam);

    List<UserDzsb> dzsbCount(String userId);

    List<UserHngs> hngsCount(String userId);

    List<UserHnds> hndsCount(String userId);

    List<ShxydmBO> bindCount(String userId);

    List<String> selectListByDate(Date date);
}
