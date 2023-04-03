package com.ruoyi.system.service;

import com.ruoyi.system.domain.FileInfo;
import com.ruoyi.system.domain.dto.FileInfoDTO;
import com.ruoyi.system.domain.vo.FileInfoVO;
import com.ruoyi.system.domain.vo.FolderOpusTreeItemVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * @author WangJiang
 * @since 2023/1/6 14:06
 */
public interface IFileInfoService {

    /**
     * 新增文件夹
     *
     * @param fileInfo 文件信息
     * @return 文件夹信息
     */
    FileInfoVO addFolder(FileInfoDTO fileInfo);

    /**
     * 修改文件夹
     *
     * @param id       目录id
     * @param fileInfo 文件信息
     * @return 文件信息
     */
    FileInfoVO updateFolder(Long id, FileInfoDTO fileInfo);

    /**
     * 删除文件
     *
     * @param id 文件id
     * @return 删除结果
     */
    Boolean delete(Long id);

    /**
     * 获取文件详情
     *
     * @param id                 文件id
     * @param shouldIgnoreDelete 是否被删除的作品依然可以被查询到 true:是 false:否（默认）
     * @return 文件信息
     */
    FileInfoVO getDetail(Long id, Boolean shouldIgnoreDelete);

    /**
     * 上传文件
     *
     * @param file     文件
     * @param parentId 目录id
     * @return 文件信息
     */
    FileInfoVO upload(MultipartFile file, String parentId);

    /**
     * 上传文件
     *
     * @param file     文件
     * @param parentId 目录id
     * @param fileType 图片类型
     * @return 文件信息
     */
    FileInfo uploadPic(File file, String parentId, String fileType, Long userId) throws Exception;

    /**
     * 获取目录树
     *
     * @param name     名称 模糊匹配目录名称
     * @param parentId 父级素材id
     * @return 目录树
     */
    List<FolderOpusTreeItemVO> getFolderTree(String name, Long parentId);

    /**
     * 创建或获取目录
     *
     * @param userId     用户id
     * @param folderName 目录名称
     * @param parentId   目录id
     * @return 目录信息
     */
    FileInfo getOrCreateFolder(Long userId, String folderName, Long parentId);

}
