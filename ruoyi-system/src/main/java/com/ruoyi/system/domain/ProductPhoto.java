package com.ruoyi.system.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 商品图片表
 *
 * @author wangjiang
 */
@Data
public class ProductPhoto implements Serializable {

    private static final long serialVersionUID = -2667563734358671850L;

    /**
     * 刊登图
     */
    public static final int PHOTO_TYPE_PRINT = 0;

    /**
     * 引用图
     */
    public static final int PHOTO_TYPE_REFERENCE = 1;

    /**
     * 商品id
     */
    private String goodsId;

    /**
     * 图片类型 0: 刊登图 1: 引用图
     */
    private Integer photoType;

    /**
     * 图片排序
     */
    private Integer photoIndex;

    /**
     * 图片地址
     */
    private String photo;

    public ProductPhoto() {
    }

    public ProductPhoto(String goodsId, Integer photoType, Integer photoIndex, String photo) {
        this.goodsId = goodsId;
        this.photoType = photoType;
        this.photoIndex = photoIndex;
        this.photo = photo;
    }

}
