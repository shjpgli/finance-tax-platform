package com.abc12366.cms.model;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-06-08 3:39 PM
 * @since 1.0.0
 */
public class SubjectItem {

    private String id;

    private String subjectId;

    @NotEmpty
    @Length(min = 1, max = 10)
    private String type;

    @NotEmpty
    @Length(min = 1, max = 200)
    private String item;

    @Length(min = 1, max = 999)
    private Integer sort;

    // 参与人数（number of participants）
    private Integer nop;

    public SubjectItem() {
    }

    public SubjectItem(String id, String subjectId, String type, String item, Integer sort) {
        this.id = id;
        this.subjectId = subjectId;
        this.type = type;
        this.item = item;
        this.sort = sort;
    }

    private SubjectItem(Builder builder) {
        setId(builder.id);
        setSubjectId(builder.subjectId);
        setType(builder.type);
        setItem(builder.item);
        setSort(builder.sort);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getNop() {
        return nop;
    }

    public void setNop(Integer nop) {
        this.nop = nop;
    }

    @Override
    public String toString() {
        return "SubjectItem{" +
                "id='" + id + '\'' +
                ", subjectId='" + subjectId + '\'' +
                ", type='" + type + '\'' +
                ", item='" + item + '\'' +
                ", sort=" + sort +
                ", nop=" + nop +
                '}';
    }

    public static final class Builder {
        private String id;
        private String subjectId;
        private String type;
        private String item;
        private Integer sort;

        public Builder() {
        }

        public Builder id(String val) {
            id = val;
            return this;
        }

        public Builder subjectId(String val) {
            subjectId = val;
            return this;
        }

        public Builder type(String val) {
            type = val;
            return this;
        }

        public Builder item(String val) {
            item = val;
            return this;
        }

        public Builder sort(Integer val) {
            sort = val;
            return this;
        }

        public SubjectItem build() {
            return new SubjectItem(this);
        }
    }
}
