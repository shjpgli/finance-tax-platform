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

    // 代码
    private int code;
    // 代码解释
    private String message;

    private static final long serialVersionUID = 1401593546385403720L;

    public ServiceException() {
        super();
    }

    public ServiceException(int code) {
        BodyStatus bodyStatus =  Utils.bodyStatus(code);
        this.code = code;
        this.message = bodyStatus.getMessage();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}