package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.domain.Tag;
import com.ruoyi.system.domain.vo.TagVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author WangJiang
 * @since 2023/3/24 10:02
 */
public interface TagMapper extends BaseMapper<Tag> {

    List<TagVO> list(@Param("searchKey") String searchKey, @Param("styleId") String styleId, @Param("tag") String tag);

}
