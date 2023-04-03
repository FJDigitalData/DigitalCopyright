package com.ruoyi.system.service;

import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.dto.TagDTO;
import com.ruoyi.system.domain.vo.TagVO;

import java.util.List;

/**
 * @author WangJiang
 * @since 2023/3/24 10:01
 */
public interface ITagService {

    /**
     * 新增
     *
     * @param tag 标签信息
     * @return 标签信息
     */
    TagVO add(TagDTO tag);

    /**
     * 删除标签
     *
     * @param ids 标签id集合
     * @return 删除成功个数
     */
    Integer delete(List<String> ids);

    /**
     * 修改
     *
     * @param id  标签id
     * @param tag 标签信息
     * @return 标签信息
     */
    TagVO update(String id, TagDTO tag);

    /**
     * 获取标签详情
     *
     * @param id 标签id
     * @return
     */
    TagVO get(String id);

    /**
     * 分页获取标签列表
     *
     * @param searchKey 模糊匹配风格名称
     * @param styleId   风格id
     * @param tag       模糊匹配标签
     * @param pageNum   页码
     * @param pageSize  每页数量
     * @return 标签列表
     */
    TableDataInfo<TagVO> page(String searchKey, String styleId, String tag, Integer pageNum, Integer pageSize);

    /**
     * 获取标签列表
     *
     * @param searchKey 模糊匹配风格名称
     * @param styleId   风格id
     * @param tag       模糊匹配标签
     * @return 标签列表
     */
    List<TagVO> list(String searchKey, String styleId, String tag);

}
