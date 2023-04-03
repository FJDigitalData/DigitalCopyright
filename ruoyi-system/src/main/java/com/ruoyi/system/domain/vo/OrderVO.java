package com.ruoyi.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.system.domain.dto.OrderOpusDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author WangJiang
 * @since 2023/1/6 14:38
 */
@Data
@Schema(name = "orderVO", description = "订单VO")
public class OrderVO implements Serializable {

    private static final long serialVersionUID = -2973217771167328495L;

    /**
     * id
     */
    @Schema(description = "id")
    private String id;

    /**
     * 第三方平台订单id
     */
    @Excel(name = "第三方平台订单id", width = 15)
    @Schema(description = "第三方平台订单id")
    private String orderId;

    /**
     * 订单状态类型 0: 未支付、1:已支付、2:生产中、3:待发货、4:已发货、5:已完成、6:已取消
     */
    @Excel(name = "订单状态", width = 15, readConverterExp = "0=未支付,1=已支付,2=生产中,3=待发货,4=已发货,5=已完成,6=已取消")
    @Schema(description = "订单状态类型 0: 未支付、1:已支付、2:生产中、3:待发货、4:已发货、5:已完成、6:已取消")
    private String type;

    /**
     * 收货人
     */
    @Excel(name = "收货人", width = 15)
    @Schema(description = "收货人")
    private String receiver;

    /**
     * 联系方式
     */
    @Schema(description = "联系方式")
    private String phone;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remarks;

    /**
     * 物流公司
     */
    @Schema(description = "物流公司")
    private String expressName;

    /**
     * 快递单号
     */
    @Schema(description = "快递单号")
    private String expressId;

    /**
     * 删除标识（0-正常,1-删除）
     */
    @Excel(name = "是否删除", width = 15, readConverterExp = "0=正常,1=删除")
    @Schema(description = "删除标识（0-正常,1-删除）")
    private String delFlag;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    private Long creator;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新人
     */
    @Schema(description = "更新人")
    private Long updater;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;


    /**
     * 关联作品信息
     */
    @Schema(description = "关联作品信息")
    private List<OrderOpusDTO> orderOpusDTOS;

}
