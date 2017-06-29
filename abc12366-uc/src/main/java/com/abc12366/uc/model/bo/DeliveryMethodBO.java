package com.abc12366.uc.model.bo;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;
import java.io.Serializable;


/**
 *
 * 配送方式
 *
 **/
@SuppressWarnings("serial")
public class DeliveryMethodBO implements Serializable {

    private String id;
    @NotEmpty
    @Size(min = 2, max = 10)
    private String name;
    private String type;
    private Integer sort;
    private Boolean status;
    @Size(min = 0, max = 200)
    private String description;
    private java.util.Date createTime;
    private java.util.Date lastUpdate;
    private Double firstWeight;
    private Double firstWeightFee;
    private Double addedWeight;
    private Double addedWeightFee;
    private Integer isInsured;
    private Double rate;
    private Double minInsuredFee;
    private String areaFeeType;


    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return this.id;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getType(){
        return this.type;
    }

    public void setSort(Integer sort){
        this.sort = sort;
    }

    public Integer getSort(){
        return this.sort;
    }

    public void setStatus(Boolean status){
        this.status = status;
    }

    public Boolean getStatus(){
        return this.status;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return this.description;
    }

    public void setCreateTime(java.util.Date createTime){
        this.createTime = createTime;
    }

    public java.util.Date getCreateTime(){
        return this.createTime;
    }

    public void setLastUpdate(java.util.Date lastUpdate){
        this.lastUpdate = lastUpdate;
    }

    public java.util.Date getLastUpdate(){
        return this.lastUpdate;
    }

    public void setFirstWeight(Double firstWeight){
        this.firstWeight = firstWeight;
    }

    public Double getFirstWeight(){
        return this.firstWeight;
    }

    public void setFirstWeightFee(Double firstWeightFee){
        this.firstWeightFee = firstWeightFee;
    }

    public Double getFirstWeightFee(){
        return this.firstWeightFee;
    }

    public void setAddedWeight(Double addedWeight){
        this.addedWeight = addedWeight;
    }

    public Double getAddedWeight(){
        return this.addedWeight;
    }

    public void setAddedWeightFee(Double addedWeightFee){
        this.addedWeightFee = addedWeightFee;
    }

    public Double getAddedWeightFee(){
        return this.addedWeightFee;
    }

    public void setIsInsured(Integer isInsured){
        this.isInsured = isInsured;
    }

    public Integer getIsInsured(){
        return this.isInsured;
    }

    public void setRate(Double rate){
        this.rate = rate;
    }

    public Double getRate(){
        return this.rate;
    }

    public void setMinInsuredFee(Double minInsuredFee){
        this.minInsuredFee = minInsuredFee;
    }

    public Double getMinInsuredFee(){
        return this.minInsuredFee;
    }

    public void setAreaFeeType(String areaFeeType){
        this.areaFeeType = areaFeeType;
    }

    public String getAreaFeeType(){
        return this.areaFeeType;
    }

}
