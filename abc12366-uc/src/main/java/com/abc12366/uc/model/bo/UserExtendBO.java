package com.abc12366.uc.model.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;

/**
 * Created by lgy on 2017-05-05.
 */
public class UserExtendBO {
    private String id;
    private String userId;
    private String signature;
    private String nickname;
    private String sex;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date birthday;
    private String bloodType;
    private String weight;
    private String height;
    private String marital;
    private String education;
    private String graduate;
    private String occupation;
    private String income;
    private String postAddress;
    private String realName;
    private String experience;
    private String weixin;
    private String qq;
    private String safeQuestion;
    private String safeAnswer;
    private String province;
    private String city;
    private String area;
    private Date createTime;
    private Date lastUpdate;

    public UserExtendBO() {
    }

    public UserExtendBO(String id, String userId, String signature, String nickname, String sex, Date birthday, String bloodType, String weight, String height, String marital, String education, String graduate, String occupation, String income, String postAddress, String realName, String experience, String weixin, String qq, String safeQuestion, String safeAnswer, String province, String city, String area, Date createTime, Date lastUpdate) {
        this.id = id;
        this.userId = userId;
        this.signature = signature;
        this.nickname = nickname;
        this.sex = sex;
        this.birthday = birthday;
        this.bloodType = bloodType;
        this.weight = weight;
        this.height = height;
        this.marital = marital;
        this.education = education;
        this.graduate = graduate;
        this.occupation = occupation;
        this.income = income;
        this.postAddress = postAddress;
        this.realName = realName;
        this.experience = experience;
        this.weixin = weixin;
        this.qq = qq;
        this.safeQuestion = safeQuestion;
        this.safeAnswer = safeAnswer;
        this.province = province;
        this.city = city;
        this.area = area;
        this.createTime = createTime;
        this.lastUpdate = lastUpdate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getMarital() {
        return marital;
    }

    public void setMarital(String marital) {
        this.marital = marital;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getGraduate() {
        return graduate;
    }

    public void setGraduate(String graduate) {
        this.graduate = graduate;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getPostAddress() {
        return postAddress;
    }

    public void setPostAddress(String postAddress) {
        this.postAddress = postAddress;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getSafeQuestion() {
        return safeQuestion;
    }

    public void setSafeQuestion(String safeQuestion) {
        this.safeQuestion = safeQuestion;
    }

    public String getSafeAnswer() {
        return safeAnswer;
    }

    public void setSafeAnswer(String safeAnswer) {
        this.safeAnswer = safeAnswer;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public String toString() {
        return "UserExtendBO{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", signature='" + signature + '\'' +
                ", nickname='" + nickname + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday=" + birthday +
                ", bloodType='" + bloodType + '\'' +
                ", weight='" + weight + '\'' +
                ", height='" + height + '\'' +
                ", marital='" + marital + '\'' +
                ", education='" + education + '\'' +
                ", graduate='" + graduate + '\'' +
                ", occupation='" + occupation + '\'' +
                ", income='" + income + '\'' +
                ", postAddress='" + postAddress + '\'' +
                ", realName='" + realName + '\'' +
                ", experience='" + experience + '\'' +
                ", weixin='" + weixin + '\'' +
                ", qq='" + qq + '\'' +
                ", safeQuestion='" + safeQuestion + '\'' +
                ", safeAnswer='" + safeAnswer + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", area='" + area + '\'' +
                ", createTime=" + createTime +
                ", lastUpdate=" + lastUpdate +
                '}';
    }
}
