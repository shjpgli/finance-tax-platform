package com.abc12366.uc.model.bo;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-02-23 4:46 PM
 * @since 1.0.0
 */
public class HelloRequestEvent {

    private String id;
    private String msg;
    private int attempts;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    @Override
    public String toString() {
        return "HelloRequestEvent{" +
                "id='" + id + '\'' +
                ", msg='" + msg + '\'' +
                ", attempts=" + attempts +
                '}';
    }
}
