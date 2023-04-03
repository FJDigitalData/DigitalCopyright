package com.ruoyi.system.domain.vo;

import com.ruoyi.common.annotation.Excel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: xwp
 * @since: 2023/1/10 13:47
 * @description:
 */
@Data
@Schema(name = "OrderOpusVO", description = "订单作品关联VO")
public class OrderOpusVO implements Serializable {
    private static final long serialVersionUID = 7884024650262231629L;

    /**
     * 订单id
     */
    @Excel(name = "订单id", width = 15)
    @Schema(description = "订单id")
    private String orderId;

    /**
     * 作品id
     */
    @Excel(name = "作品id", width = 15)
    @Schema(description = "作品id")
    private String opusId;

    /**
     * 作品数量
     */
    @Excel(name = "作品数量", width = 15)
    @Schema(description = "作品数量")
    private Integer quantity;

    /**
     * 单品总额
     */
    @Excel(name = "单品总额", width = 15)
    @Schema(description = "单品总额")
    private float countSum;
}
