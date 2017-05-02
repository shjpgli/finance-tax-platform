package com.abc12366.message.model;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-02-23 4:46 PM
 * @since 1.0.0
 */
public class HelloEvent {

    private String id;
    private String msg;

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

    @Override
    public String toString() {
        return "HelloEvent{" +
                "id='" + id + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
