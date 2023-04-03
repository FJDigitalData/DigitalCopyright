package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author WangJiang
 * @since 2023/3/21 16:13
 */
@Data
@TableName("t_product_type")
public class ProductType implements Serializable {

    private static final long serialVersionUID = -569174863890708539L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private Long parentId;

    private String note;

}
