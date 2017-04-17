package com.abc12366.core.model.bo;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-02-23 4:52 PM
 * @since 1.0.0
 */
public class BaseEvent {

    protected String id;

    // 尝试次数
    protected int attempts;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }
}
