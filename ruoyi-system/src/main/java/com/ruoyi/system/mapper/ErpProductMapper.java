package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.ErpProduct;
import com.ruoyi.system.domain.vo.ErpProductVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author WangJiang
 * @since 2023/2/7 10:35
 */
public interface ErpProductMapper {

    ErpProduct findById(@Param("id") String id, @Param("includeDelete") boolean includeDelete);

    int add(ErpProduct realProduct);

    int logicDelete(@Param("id") String id);

    int update(ErpProduct oldProduct);

    List<ErpProductVO> getList(@Param("id") String id,
                               @Param("name") String name,
                               @Param("adoptStatus") Integer adoptStatus,
                               @Param("sellType") Integer sellType,
                               @Param("status") Integer status,
                               @Param("userId") Long userId);

    int deleteRelationOpus(@Param("productId") String productId);

    int addRelationOpus(@Param("productId") String productId, @Param("opusId") String opusId);

    String getRelationOpus(@Param("productId") String productId);

}
