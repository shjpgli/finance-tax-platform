package com.abc12366.common.exception;

import com.abc12366.common.model.BodyStatus;
import com.abc12366.common.util.Utils;

/**
 * Service层公用的Exception.
 * <p/>
 * 继承自RuntimeException, 从由Spring管理事务的函数中抛出时会触发事务回滚.
 *
 * @author lizhongwei
 */
public class ServiceException extends RuntimeException {

    private BodyStatus bodyStatus;

    private static final long serialVersionUID = 1401593546385403720L;

    public ServiceException() {
        super();
    }

    public ServiceException(int code) {
        this.bodyStatus=  Utils.bodyStatus(code);
    }

    public BodyStatus getBodyStatus() {
        return bodyStatus;
    }

    public void setBodyStatus(BodyStatus bodyStatus) {
        this.bodyStatus = bodyStatus;
    }
}