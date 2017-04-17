package com.abc12366.core.model.bo;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-02-23 4:45 PM
 * @since 1.0.0
 */
public class HelloResponseEvent {

    private String id;
    private String requestId;
    private String responseMsg;
    private HelloRequestEvent requestEvent;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    public HelloRequestEvent getRequestEvent() {
        return requestEvent;
    }

    public void setRequestEvent(HelloRequestEvent requestEvent) {
        this.requestEvent = requestEvent;
    }

    @Override
    public String toString() {
        return "HelloResponseEvent{" +
                "id='" + id + '\'' +
                ", requestId='" + requestId + '\'' +
                ", responseMsg='" + responseMsg + '\'' +
                ", requestEvent=" + requestEvent +
                '}';
    }
}
