package com.abc12366.gateway.model.bo;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-05 1:03 PM
 * @since 1.0.0
 */
public class AppRespBO {

    private String id;

    private String name;

    private String mark;

    public AppRespBO() {
    }

    private AppRespBO(Builder builder) {
        setId(builder.id);
        setName(builder.name);
        setMark(builder.mark);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        return "AppRespBO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", mark='" + mark + '\'' +
                '}';
    }

    public static final class Builder {
        private String id;
        private String name;
        private String mark;

        public Builder() {
        }

        public Builder id(String val) {
            id = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder mark(String val) {
            mark = val;
            return this;
        }

        public AppRespBO build() {
            return new AppRespBO(this);
        }
    }
}
