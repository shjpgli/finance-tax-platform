package com.abc12366.core.model.bo;

/**
 * 验证器返回的异常结果body内容
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-03-21 5:21 PM
 * @since 1.0.0
 */
public class ResultBodyBO {

    private Class clazz;

//    private Throwable cause;

    private String message;

    private String localizedMessage;

    private ResultBodyBO(Builder builder) {
        setClazz(builder.clazz);
        setMessage(builder.message);
        setLocalizedMessage(builder.localizedMessage);
    }

//    private StackTraceElement[] stackTraces;


    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLocalizedMessage() {
        return localizedMessage;
    }

    public void setLocalizedMessage(String localizedMessage) {
        this.localizedMessage = localizedMessage;
    }

    @Override
    public String toString() {
        return "ResultBodyBO{" +
                "clazz=" + clazz +
                ", message='" + message + '\'' +
                ", localizedMessage='" + localizedMessage + '\'' +
                '}';
    }


    public static final class Builder {
        private Class clazz;
        private String message;
        private String localizedMessage;

        public Builder() {
        }

        public Builder clazz(Class val) {
            clazz = val;
            return this;
        }

        public Builder message(String val) {
            message = val;
            return this;
        }

        public Builder localizedMessage(String val) {
            localizedMessage = val;
            return this;
        }

        public ResultBodyBO build() {
            return new ResultBodyBO(this);
        }
    }
}
