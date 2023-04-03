package com.ruoyi.system.mapper;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.domain.Empower;
import com.ruoyi.system.domain.Opus;
import com.ruoyi.system.domain.Order;
import com.ruoyi.system.domain.OrderOpus;
import com.ruoyi.system.domain.dto.OrderOpusDTO;
import com.ruoyi.system.domain.vo.OrderOpusVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xwp
 * @since 2023/1/10 14:00
 */
public interface OrderMapper {


    int addOrder(Order order);

    int addOrderOpus(OrderOpus orderOpus);

    int updateOrder(Order order);

    Order get(@Param("id") String id);

    int deleteOrderOpus(@Param("orderId") String orderId);


    List<OrderOpusDTO> getOrderOpus(@Param("orderId") String orderId);


    /**
     * 查询订单
     *
     * @param orderId 订单id
     * @return 结果
     */
    List<Order> selectOrderList(@Param("creator") Long creator, @Param("orderId") String orderId,@Param("type") String type);


    /**
     * 校验订单名称是否唯一
     *
     * @param orderId 订单id
     * @return 结果
     */
    public Order checkOrderIdUnique(@Param("orderId") String orderId);

}
