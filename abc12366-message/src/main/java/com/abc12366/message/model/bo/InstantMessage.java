package com.abc12366.message.model.bo;

import java.util.Calendar;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-03-22 2:02 PM
 * @since 1.0.0
 */
public class InstantMessage {

    private String to;

    private String from;

    private String message;

    private Calendar created = Calendar.getInstance();

    public String getTo() {
        return this.to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return this.from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Calendar getCreated() {
        return this.created;
    }

    public void setCreated(Calendar created) {
        this.created = created;
    }
}
