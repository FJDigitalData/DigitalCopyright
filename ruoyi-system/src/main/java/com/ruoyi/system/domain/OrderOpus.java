package com.ruoyi.system.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: xwp
 * @since: 2023/1/10 13:47
 * @description:
 */
@Data
@Schema(name = "OrderOpus", description = "订单作品关联")
public class OrderOpus implements Serializable {

    private static final long serialVersionUID = -2928077453636205863L;
    /**
     * 订单id（订单表主键id）
     */
    private String orderId;

    /**
     * 作品id
     */
    private String opusId;

    /**
     * 作品数量
     */
    private Integer quantity;

    /**
     * 单品总额
     */
    private float countSum;
}
