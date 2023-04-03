package com.ruoyi.system.service;

import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.dto.StoreDTO;
import com.ruoyi.system.domain.vo.StoreVO;

import java.util.List;

/**
 * @author WangJiang
 * @since 2023/3/2 11:27
 */
public interface IStoreService {

    /**
     * 新增
     *
     * @param store 店铺信息
     * @return 店铺信息
     */
    StoreVO add(StoreDTO store);

    /**
     * 删除
     *
     * @param ids 店铺id集合
     * @return 删除成功数量
     */
    int delete(List<Long> ids);

    /**
     * 修改
     *
     * @param id    店铺id
     * @param store 店铺信息
     * @return 店铺信息
     */
    StoreVO update(Long id, StoreDTO store);

    /**
     * 查询店铺详情
     *
     * @param id                 店铺id
     * @param shouldIgnoreDelete 是否被删除的店铺依然可以被查询到
     * @return 店铺详情
     */
    StoreVO get(Long id, Boolean shouldIgnoreDelete);

    /**
     * 获取店铺分页列表
     *
     * @param name     店铺名称
     * @param pageNum  页码
     * @param pageSize 每页数量
     * @return 店铺分页列表
     */
    TableDataInfo<StoreVO> page(String name, Integer pageNum, Integer pageSize);

    /**
     * 获取店铺列表
     *
     * @param name 店铺名称
     * @return 店铺列表
     */
    List<StoreVO> list(String name);

}
