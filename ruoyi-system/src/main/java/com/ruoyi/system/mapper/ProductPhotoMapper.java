package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.ProductPhoto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author WangJiang
 * @since 2023/2/7 10:36
 */
public interface ProductPhotoMapper {

    int add(@Param("list") List<ProductPhoto> productPhotoList);

    int delete(@Param("goodsId") String goodsId);

    List<String> getPhoto(@Param("goodsId") String goodsId, @Param("photoType") int photoType);

}
