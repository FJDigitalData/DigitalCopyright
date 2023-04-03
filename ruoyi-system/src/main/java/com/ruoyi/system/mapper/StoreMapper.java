package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.Store;
import com.ruoyi.system.domain.vo.StoreVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author WangJiang
 * @since 2023/3/2 11:29
 */
public interface StoreMapper {

    /**
     * 根据店铺id查询店铺
     * @param id 店铺id
     * @return 店铺信息
     */
    Store getById(@Param("id") Long id);

    /**
     * 根据店铺名称和所属公司查询店铺
     * @param name 店铺名称
     * @param companyId 店铺所属公司
     * @return 店铺信息
     */
    Store getByNameAndCompanyId(@Param("name") String name, @Param("companyId") String companyId);

    /**
     * 新增
     * @param store 店铺信息
     * @return 受影响行数
     */
    int add(Store store);

    /**
     * 修改店铺
     * @param store 店铺信息
     * @return 受影响行数
     */
    int update(Store store);

    /**
     * 获取店铺列表
     * @param name 店铺名称
     * @param companyId 公司id
     * @return 店铺列表
     */
    List<StoreVO> list(@Param("name") String name, @Param("companyId") String companyId);

}
