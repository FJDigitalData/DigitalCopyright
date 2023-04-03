package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.ProductSpecs;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author WangJiang
 * @since 2023/2/7 10:35
 */
public interface ProductSpecsMapper {

    int add(ProductSpecs productSpecs);

    int delete(@Param("goodsId") String goodsId);

    List<ProductSpecs> getList(@Param("goodsId") String goodsId);

}
