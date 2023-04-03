package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.ProductVariant;
import com.ruoyi.system.domain.vo.ProductVariantVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author WangJiang
 * @since 2023/2/7 10:35
 */
public interface ProductVariantMapper {

    int add(ProductVariant productVariant);

    int delete(@Param("id") String id);

    int deleteByGoodsId(@Param("goodsId") String goodsId);

    List<ProductVariant> getList(@Param("goodsId") String goodsId);

    List<ProductVariantVO> getListInfo(@Param("goodsId") String goodsId);

}
