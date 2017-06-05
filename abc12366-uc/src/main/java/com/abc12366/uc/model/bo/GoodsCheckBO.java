package com.abc12366.uc.model.bo;

import java.io.Serializable;
import java.util.List;


/**
 * 
 * 产品表
 * 
 **/
@SuppressWarnings("serial")
public class GoodsCheckBO implements Serializable {

	private String goodsIds;
	private Boolean status;

    public String getGoodsIds() {
        return goodsIds;
    }

    public void setGoodsIds(String goodsIds) {
        this.goodsIds = goodsIds;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
