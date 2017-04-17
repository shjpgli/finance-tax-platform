package com.abc12366.core.model.bo;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-02-23 4:53 PM
 * @since 1.0.0
 */
public enum EventType {

    CREATE("create"),
    READ("read");

    EventType(String type) {
        this.type = type;
    }

    private final String type;

    public String getType() {
        return type;
    }
}
