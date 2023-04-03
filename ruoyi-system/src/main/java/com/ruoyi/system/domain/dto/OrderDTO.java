package com.ruoyi.system.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @author WangJiang
 * @since 2023/1/6 14:38
 */
@Data
@Schema(name = "orderDTO", description = "订单DTO")
public class OrderDTO implements Serializable {

    private static final long serialVersionUID = -3448656919985966286L;

    /**
     * 第三方平台订单id
     */
    @NotBlank(message = "第三方平台订单id为空")
    @Schema(description = "第三方平台订单id", required = true)
    private String orderId;


    /**
     * 收货人
     */
    @NotBlank(message = "收货人为空")
    @Schema(description = "收货人", required = true)
    private String receiver;

    /**
     * 联系方式
     */
    @NotBlank(message = "联系方式为空")
    @Schema(description = "联系方式", required = true)
    private String phone;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remarks;

    /**
     * 物流公司
     */
    @Schema(description = "物流公司", hidden = true)
    private String expressName;

    /**
     * 快递单号
     */

    @Schema(description = "快递单号", hidden = true)
    private String expressId;

    /**
     * 订单总额
     */
    @Schema(description = "订单总额")
    private float total;

    /**
     * 关联作品信息
     */
    @NotEmpty(message = "关联作品信息为空")
    @Schema(description = "关联作品信息", required = true)
    private List<OrderOpusDTO> orderOpusDTOS;

}
