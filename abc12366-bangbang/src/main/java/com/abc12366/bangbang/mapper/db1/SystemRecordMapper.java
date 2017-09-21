package com.abc12366.bangbang.mapper.db1;

import com.abc12366.bangbang.model.SystemRecord;
import com.abc12366.bangbang.model.bo.SystemRecordBO;

public interface SystemRecordMapper {
    int insert(SystemRecord systemRecord);
    int updateStay(SystemRecordBO systemRecordBO);
}
