package com.abc12366.core.model.bo;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-03-22 2:41 PM
 * @since 1.0.0
 */
public class ActiveUser {

    private String id;

    private String username;

    private Long connectionTime;

    public ActiveUser() {
    }

    public ActiveUser(String id, String username, Long connectionTime) {
        super();
        this.id = id;
        this.username = username;
        this.connectionTime = connectionTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getConnectionTime() {
        return this.connectionTime;
    }

    public void setConnectionTime(Long connectionTime) {
        this.connectionTime = connectionTime;
    }

    @Override
    public String toString() {
        return "ActiveUser{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", connectionTime=" + connectionTime +
                '}';
    }
}
