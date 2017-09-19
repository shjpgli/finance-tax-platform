package com.abc12366.uc.model.bo;

import java.util.Date;

/**
 * Admin: lingsuzhi <554600654@qq.com.com>
 * Date: 2017-09-05
 */


public class UpointsLotteryBO{
 /** key */
private String id;
 /** 系号 */
private Integer orderId;
 /** 名称 */
private String name;
 /** 描述 */
private String description;
 /** 库存 为0则不生成 */
private Integer stock;
 /** 图片 */
private String image;
 /** 概率 百分比 */
private Double luck;
 /** 谢谢参与：0否，1是 */
private Integer notluck;
 /** 创建时间 */
private Date createTime;
 /** 修改时间 */
private Date lastUpdate;
    /**类别*/
    private String types;
    /**每天数量*/
    private Integer stockDay;

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public Integer getStockDay() {
        return stockDay;
    }

    public void setStockDay(Integer stockDay) {
        this.stockDay = stockDay;
    }


    public String getId(){
    return id;
}
public void setId(String id){
   this.id = id;
}
public Integer getOrderId(){
    return orderId;
}
public void setOrderId(Integer orderId){
   this.orderId = orderId;
}
public String getName(){
    return name;
}
public void setName(String name){
   this.name = name;
}
public String getDescription(){
    return description;
}
public void setDescription(String description){
   this.description = description;
}
public Integer getStock(){
    return stock;
}
public void setStock(Integer stock){
   this.stock = stock;
}
public String getImage(){
    return image;
}
public void setImage(String image){
   this.image = image;
}
public Double getLuck(){
    return luck;
}
public void setLuck(Double luck){
   this.luck = luck;
}
public Integer getNotluck(){
    return notluck;
}
public void setNotluck(Integer notluck){
   this.notluck = notluck;
}
public Date getCreateTime(){
    return createTime;
}
public void setCreateTime(Date createTime){
   this.createTime = createTime;
}
public Date getLastUpdate(){
    return lastUpdate;
}
public void setLastUpdate(Date lastUpdate){
   this.lastUpdate = lastUpdate;
}

@Override
public String toString() {
return "UpointsLottery {\r\n"+
"\""+"id"+"\""+":"+"\""+id+"\""+",\r\n"+
"\""+"orderId"+"\""+":"+"\""+orderId+"\""+",\r\n"+
"\""+"name"+"\""+":"+"\""+name+"\""+",\r\n"+
"\""+"description"+"\""+":"+"\""+description+"\""+",\r\n"+
"\""+"stock"+"\""+":"+"\""+stock+"\""+",\r\n"+
"\""+"image"+"\""+":"+"\""+image+"\""+",\r\n"+
"\""+"luck"+"\""+":"+"\""+luck+"\""+",\r\n"+
"\""+"notluck"+"\""+":"+"\""+notluck+"\""+",\r\n"+
"\""+"createTime"+"\""+":"+"\""+createTime+"\""+",\r\n"+
"\""+"lastUpdate"+"\""+":"+"\""+lastUpdate+"\""+"}";
}
}
