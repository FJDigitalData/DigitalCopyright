package com.ruoyi.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.ruoyi.system.domain.vo.ProductTypeVO;
import com.ruoyi.system.mapper.ProductTypeMapper;
import com.ruoyi.system.service.IProductTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author WangJiang
 * @since 2023/3/21 16:13
 */
@Service
public class ProductTypeServiceImpl implements IProductTypeService {

    @Resource
    private ProductTypeMapper productTypeMapper;

    @Override
    public List<ProductTypeVO> getTree(String name, Long parentId) {
        Map<Long, List<ProductTypeVO>> productTypes = productTypeMapper.getList(name)
                .stream()
                .collect(Collectors.groupingBy(ProductTypeVO::getParentId));
        List<ProductTypeVO> result = productTypes.get(parentId);
        if (CollUtil.isNotEmpty(result)) {
           deal(productTypes, result);
        }
        return result;
    }

    @Override
    public List<ProductTypeVO> getChild(String name, Long parentId, boolean shouldRecursion) {
        List<ProductTypeVO> productTypes = productTypeMapper.getChild(name, parentId);
        if (shouldRecursion) {
            for (ProductTypeVO productType : productTypes) {
                productType.setChild(getChild(name, productType.getId(), true));
            }
        }
        return productTypes;
    }

    private void deal(Map<Long, List<ProductTypeVO>> productTypes, List<ProductTypeVO> result) {
        for (ProductTypeVO pt : result) {
            pt.setChild(productTypes.get(pt.getId()));
            if (CollUtil.isNotEmpty(pt.getChild())) {
                deal(productTypes, pt.getChild());
            }
        }
    }

}
