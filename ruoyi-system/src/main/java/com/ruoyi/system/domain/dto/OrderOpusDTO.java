package com.ruoyi.system.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author: XWP
 * @since: 2023/1/10 11:42
 * @description:
 */
@Data
@Schema(name = "orderOpusDTO", description = "订单_作品DTO")
public class OrderOpusDTO implements Serializable {
    private static final long serialVersionUID = -7473670124720031358L;

    /**
     * 作品id
     */
    @NotBlank(message = "作品id为空")
    @Schema(description = "作品id")
    private String opusId;

    /**
     * 作品数量
     */
    @NotNull(message = "作品数量为空")
    @Schema(description = "作品数量")
    private Integer quantity;

    /**
     * 单品总额
     */
    @NotNull(message = "单品总额为空")
    @Schema(description = "单品总额")
    private float countSum;


}
