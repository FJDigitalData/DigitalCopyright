package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.Empower;
import com.ruoyi.system.domain.Opus;
import com.ruoyi.system.domain.vo.EmpowerVO;
import com.ruoyi.system.domain.vo.FolderOpusTreeItemVO;
import com.ruoyi.system.domain.vo.OpusVO;
import com.ruoyi.system.domain.vo.TagVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author WangJiang
 * @since 2023/1/6 15:00
 */
public interface OpusMapper {

    Opus findByNameAndParentId(@Param("name") String name, @Param("parentId") Long parentId);

    int add(Opus opus);

    int update(Opus opus);

    Opus get(@Param("id") String id);

    Opus getIgnoreDelete(@Param("id") String id);

    int cleanOpusTag(@Param("opusId") String opusId);

    int addOpusTag(@Param("opusId") String opusId, @Param("tags") List<String> tags);

    Empower getEmpower(@Param("opusId") String opusId, @Param("userId") Long userId);

    int insertEmpower(Empower empower);

    int updateEmpower(Empower empower);

    List<EmpowerVO> getEmpowerList(@Param("opusId") String opusId);

    int addHot(@Param("opusId") String opusId);

    int batchDeleteByParent(@Param("parentIds") List<Long> parentIds);

    List<OpusVO> getPublicPage(@Param("name") String name, @Param("style") String style, @Param("tag") String tag, @Param("key") String key);

    List<OpusVO> getPage(@Param("style") String style,
                         @Param("key") String key,
                         @Param("isOpen") String isOpen,
                         @Param("isFreeze") String isFreeze,
                         @Param("applyStatus") String applyStatus,
                         @Param("uploadTime") String uploadTime,
                         @Param("onlyAuthorization") boolean onlyAuthorization,
                         @Param("shouldContainFileUrl") boolean shouldContainFileUrl,
                         @Param("shouldContainMaterialFileUrl") boolean shouldContainMaterialFileUrl,
                         @Param("userId") Long userId,
                         @Param("sortType") Integer sortType,
                         @Param("orderType") Integer orderType);

    List<OpusVO> getChildrenInfo(@Param("name") String name, @Param("parentId") Long parentId, @Param("userId") Long userId);

    List<TagVO> getTagsList(@Param("opusId") String opusId);

    List<FolderOpusTreeItemVO> getChildrenOpus(@Param("name") String name, @Param("parentId") Long parentId, @Param("userId") Long userId);

    int insert(Opus opus);

    String checkAuthorization(@Param("userId") Long userId, @Param("opusId") String opusId);

}
