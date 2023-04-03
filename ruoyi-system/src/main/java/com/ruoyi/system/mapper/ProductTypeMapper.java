package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.domain.ProductType;
import com.ruoyi.system.domain.vo.ProductTypeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author WangJiang
 * @since 2023/3/21 16:23
 */
public interface ProductTypeMapper extends BaseMapper<ProductType> {

    List<ProductTypeVO> getChild(@Param("name") String name, @Param("parentId") Long parentId);

    List<ProductTypeVO> getList(@Param("name") String name);

}
