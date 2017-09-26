package com.abc12366.uc.mapper.db2;

import com.abc12366.uc.model.UserDzsb;
import com.abc12366.uc.model.UserHnds;
import com.abc12366.uc.model.UserHngs;
import com.abc12366.uc.model.abc4000.NSRXX;
import com.abc12366.uc.model.abc4000.NSRXXBO;
import com.abc12366.uc.model.bo.*;

import java.util.List;

/**
 * Admin: liuguiyao<435720953@qq.com>
 * Date: 2017-07-25
 * Time: 16:22
 */
public interface UserBindRoMapper {

    UserDzsb userDzsbSelectById(String id);

    UserHngs userHngsSelectById(String id);

    UserHnds userHndsSelectById(String id);

    List<UserDzsbListBO> getUserDzsbBind(String userId);

    List<UserHngsListBO> getUserhngsBind(String userId);

    List<UserHndsBO> getUserhndsBind(String userId);

    List<NSRXX> selectListByUserId(String userId);

    List<UserHngs> userHngsListExist(UserHngsInsertBO userHngsInsertBO);

    List<NSRXXBO> selectListByUserIdAndNsrsbhOrShxydm(UserDzsb queryParam);
    
    List<UserDzsb> dzsbCount(String userId);

    List<UserHngs> hngsCount(String userId);

    List<UserHnds> hndsCount(String userId);

    List<ShxydmBO> bindCount(String userId);
}
