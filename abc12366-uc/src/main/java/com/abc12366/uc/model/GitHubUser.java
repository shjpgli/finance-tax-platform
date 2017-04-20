package com.abc12366.uc.model;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-03-24 11:33 AM
 * @since 1.0.0
 */
public class GitHubUser {

    private String name;
    private String blog;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBlog() {
        return blog;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

    @Override
    public String toString() {
        return "GitHubUser{" +
                "name='" + name + '\'' +
                ", blog='" + blog + '\'' +
                '}';
    }
}
