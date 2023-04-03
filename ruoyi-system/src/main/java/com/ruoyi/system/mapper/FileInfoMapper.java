package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.FileInfo;
import com.ruoyi.system.domain.vo.FolderOpusTreeItemVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author WangJiang
 * @since 2023/1/6 15:48
 */
public interface FileInfoMapper {

    FileInfo findDicByNameAndParentIdAndCreateId(@Param("name") String name, @Param("parentId") String parentId, @Param("creatorId") Long creatorId);

    int insert(FileInfo realFileInfo);

    FileInfo get(@Param("id") Long id);

    FileInfo getIgnoreDelete(@Param("id") Long id);

    int update(FileInfo realFileInfo);

    List<Long> getChildrenFolderIds(@Param("id") Long id);

    int deleteAll(@Param("fileInfoIds") List<Long> fileInfoIds);

    List<FolderOpusTreeItemVO> getChildrenFolders(@Param("name") String name, @Param("parentId") Long parentId, @Param("creatorId") Long creatorId);

    List<FileInfo> getChildrenFolderInfo(@Param("name") String name, @Param("parentId") Long parentId, @Param("userId") Long userId);

    String getFileName(@Param("fileId") Long fileId);

    FileInfo getFile(@Param("fileName") String fileName, @Param("parentId") Long parentId, @Param("userId") Long userId);

}
