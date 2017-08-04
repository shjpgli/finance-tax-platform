package com.abc12366.uc.model.bo;

import com.abc12366.uc.model.UvipPrice;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * 产品参数
 **/
@SuppressWarnings("serial")
public class ProductBO implements Serializable {

    private String id;
    private String goodsId;
    private Double marketPrice;
    private Double sellingPrice;
    private Double costPrice;
    private java.util.Date createTime;
    private java.util.Date lastUpdate;
    private Integer stock;
    private String goodsName;

    private ProductRepoBO productRepoBO;
    private List<DictBO> dictList = new ArrayList<>();
    private List<UvipPrice> uvipPriceList = new ArrayList<>();
    private List<ProductRepoBO> repoBOList = new ArrayList<>();

    private Integer startRepo;
    private Integer endRepo;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodsId() {
        return this.goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public Double getMarketPrice() {
        return this.marketPrice;
    }

    public void setMarketPrice(Double marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Double getSellingPrice() {
        return this.sellingPrice;
    }

    public void setSellingPrice(Double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Double getCostPrice() {
        return this.costPrice;
    }

    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }

    public java.util.Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }

    public java.util.Date getLastUpdate() {
        return this.lastUpdate;
    }

    public void setLastUpdate(java.util.Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public List<DictBO> getDictList() {
        return dictList;
    }

    public void setDictList(List<DictBO> dictList) {
        this.dictList = dictList;
    }

    public List<UvipPrice> getUvipPriceList() {
        return uvipPriceList;
    }

    public void setUvipPriceList(List<UvipPrice> uvipPriceList) {
        this.uvipPriceList = uvipPriceList;
    }

    public List<ProductRepoBO> getRepoBOList() {
        return repoBOList;
    }

    public void setRepoBOList(List<ProductRepoBO> repoBOList) {
        this.repoBOList = repoBOList;
    }

    public ProductRepoBO getProductRepoBO() {
        return productRepoBO;
    }

    public void setProductRepoBO(ProductRepoBO productRepoBO) {
        this.productRepoBO = productRepoBO;
    }

    public Integer getStartRepo() {
        return startRepo;
    }

    public void setStartRepo(Integer startRepo) {
        this.startRepo = startRepo;
    }

    public Integer getEndRepo() {
        return endRepo;
    }

    public void setEndRepo(Integer endRepo) {
        this.endRepo = endRepo;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
}
