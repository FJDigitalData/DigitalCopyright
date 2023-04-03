package com.ruoyi.system.service;

import com.ruoyi.system.domain.vo.ProductTypeVO;

import java.util.List;

/**
 * @author WangJiang
 * @since 2023/3/21 16:13
 */
public interface IProductTypeService {

    /**
     * 查询亚马逊商品类型树
     *
     * @param name     名称
     * @param parentId 父级id
     * @return 亚马逊商品类型树
     */
    List<ProductTypeVO> getTree(String name, Long parentId);

    /**
     * 查询亚马逊商品类型子类型
     *
     * @param name            名称
     * @param parentId        父级id
     * @param shouldRecursion 是否需要递归
     * @return 亚马逊商品类型子类型
     */
    List<ProductTypeVO> getChild(String name, Long parentId, boolean shouldRecursion);

}
