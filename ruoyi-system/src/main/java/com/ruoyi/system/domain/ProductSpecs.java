package com.ruoyi.system.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品规格表
 *
 * @author wangjiang
 */
@Data
public class ProductSpecs implements Serializable {

    private static final long serialVersionUID = -2667563734358671850L;

    /**
     * 商品库id
     */
    private String goodsId;

    /**
     * 规格名
     */
    private String specsName;

    /**
     * 规格值
     */
    private String specsValue;

    /**
     * 创建人
     */
    private Long creator;

    /**
     * 创建时间
     */
    private Date createTime;

    public ProductSpecs() {
    }

    public ProductSpecs(String goodsId, String specsName, String specsValue, Long creator, Date createTime) {
        this.goodsId = goodsId;
        this.specsName = specsName;
        this.specsValue = specsValue;
        this.creator = creator;
        this.createTime = createTime;
    }

}
