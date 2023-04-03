package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.exception.file.FileException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.FfmpegUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.common.utils.file.MinioUtil;
import com.ruoyi.common.utils.uuid.Seq;
import com.ruoyi.system.domain.FileInfo;
import com.ruoyi.system.domain.dto.FileInfoDTO;
import com.ruoyi.system.domain.vo.FileInfoVO;
import com.ruoyi.system.domain.vo.FolderOpusTreeItemVO;
import com.ruoyi.system.mapper.FileInfoMapper;
import com.ruoyi.system.mapper.OpusMapper;
import com.ruoyi.system.service.IFileInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author WangJiang
 * @since 2023/1/6 14:07
 */
@Slf4j
@Service
public class FileInfoService implements IFileInfoService {

    @Resource
    private OpusMapper opusMapper;

    @Resource
    private FileInfoMapper fileInfoMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FileInfoVO addFolder(FileInfoDTO fileInfo) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //判断目录是否已经存在
        FileInfo sameNameFileInfo = fileInfoMapper.findDicByNameAndParentIdAndCreateId(fileInfo.getName(), fileInfo.getParentId(), loginUser.getUserId());
        Assert.isNull(sameNameFileInfo, "当前目录下该文件夹已存在");
        FileInfo realFileInfo = new FileInfo();
        realFileInfo.setName(fileInfo.getName());
        realFileInfo.setFileName(fileInfo.getName());
        realFileInfo.setType(Constants.FILE_TYPE_FOLDER);
        realFileInfo.setIsDir(Constants.YES);
        realFileInfo.setParentId(fileInfo.getParentId());
        setCreateInfo(SecurityUtils.getLoginUser(), realFileInfo);
        fileInfoMapper.insert(realFileInfo);
        FileInfoVO result = new FileInfoVO();
        BeanUtil.copyProperties(realFileInfo, result);
        result.setCreatorName(loginUser.getUser().getNickName());
        result.setUpdaterName(result.getCreatorName());
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FileInfoVO updateFolder(Long id, FileInfoDTO fileInfo) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //判断目录是否已经存在
        FileInfo sameNameFileInfo = fileInfoMapper.findDicByNameAndParentIdAndCreateId(fileInfo.getName(), fileInfo.getParentId(), loginUser.getUserId());
        Assert.isTrue(sameNameFileInfo == null || sameNameFileInfo.getId().equals(id), "当前目录下该文件夹已存在");
        FileInfo realFileInfo = fileInfoMapper.get(id);
        Assert.notNull(realFileInfo, "要修改的目录不存在");
        Assert.isTrue(!id.toString().equals(fileInfo.getParentId()), "不能把目录移动到自身下");
        Assert.isTrue(getChildrenFolderIds(id).stream().noneMatch(fId -> StrUtil.equals(fileInfo.getParentId(), fId.toString())), "不能把目录移动到子孙目录下");
        realFileInfo.setParentId(fileInfo.getParentId());
        realFileInfo.setFileName(fileInfo.getName());
        setUpdaterInfo(loginUser, realFileInfo);
        fileInfoMapper.update(realFileInfo);
        FileInfoVO result = new FileInfoVO();
        BeanUtil.copyProperties(realFileInfo, result);
        result.setCreatorName(loginUser.getUser().getNickName());
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(Long id) {
        Assert.isTrue(!Constants.TOP_DIC_ID.equals(id), "顶级目录无法删除");
        FileInfo realFileInfo = fileInfoMapper.get(id);
        if (realFileInfo != null) {
            List<Long> selfAndChildren;
            if (Constants.YES.equals(realFileInfo.getIsDir())) {
                // 获取 子孙目录
                selfAndChildren = getChildrenFolderIds(id);
                selfAndChildren.add(id);
                selfAndChildren = selfAndChildren.stream().distinct().collect(Collectors.toList());
            } else {
                selfAndChildren = CollUtil.toList(id);
            }
            if (CollUtil.isNotEmpty(selfAndChildren)) {
                //TODO删除所有父级是这些id的作品
                opusMapper.batchDeleteByParent(selfAndChildren);
                //删除目录以及文件
                fileInfoMapper.deleteAll(selfAndChildren);
            }
        }
        return Boolean.TRUE;
    }

    @Override
    public FileInfoVO getDetail(Long id, Boolean shouldIgnoreDelete) {
        FileInfo fileInfo;
        if (Boolean.TRUE.equals(shouldIgnoreDelete)) {
            fileInfo = fileInfoMapper.getIgnoreDelete(id);
        } else {
            fileInfo = fileInfoMapper.get(id);
        }
        Assert.notNull(fileInfo, "该文件不存在");
        FileInfoVO result = new FileInfoVO();
        BeanUtil.copyProperties(fileInfo, result);
        if (Constants.TOP_DIC_ID_STR.equals(fileInfo.getParentId())) {
            result.setParentName("/");
        } else {
            try {
                if (Boolean.TRUE.equals(shouldIgnoreDelete)) {
                    fileInfo = fileInfoMapper.getIgnoreDelete(Long.getLong(fileInfo.getParentId()));
                } else {
                    fileInfo = fileInfoMapper.get(Long.getLong(fileInfo.getParentId()));
                }
                if (fileInfo != null) {
                    result.setParentName(fileInfo.getFileName());
                }
            } catch (Exception e) {
                log.error("获取目录信息出错, 错误信息:{}", e.getMessage(), e);
                result.setParentName("");
            }
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FileInfoVO upload(MultipartFile file, String parentId) {
        File temp = null, zipImgFile = null;
        try {
            //文件上传
            String fileUrl = FileUploadUtils.uploadMinio(file);
            temp = new File(RuoYiConfig.getProfile() + File.separator + RandomUtil.randomNumbers(6) + file.getOriginalFilename());
            file.transferTo(temp);
            String fileType = Optional.ofNullable(FileTypeUtil.getType(temp)).orElse("").toLowerCase();
            FileInfo fileInfo = new FileInfo();
            fileInfo.setUrl(fileUrl);
            fileInfo.setName(file.getOriginalFilename());
            fileInfo.setFileName(FileUtils.getName(fileUrl));
            fileInfo.setSuffix(fileType);
            fileInfo.setIsDir(Constants.NO);
            fileInfo.setIsImg(Constants.NO);
            fileInfo.setType(Constants.FILE_TYPE_FILE);
            fileInfo.setParentId(parentId);
            if (ArrayUtil.contains(Constants.IMG_TYPE_ARRAY, fileType)) {
                //不超过1MB的图片不用生成缩略图
                if (temp.length() > Constants.ONE_MB) {
                    //生成缩略图
                    String zipImgPath = FfmpegUtils.zipPic(temp.getAbsolutePath(), null);
                    zipImgFile = new File(zipImgPath);
                    fileUrl = MinioUtil.uploadFile(FileUploadUtils.getBucketName(), getNewFileName(zipImgFile), Files.newInputStream(zipImgFile.toPath()), "image/png");
                }
                fileInfo.setZipUrl(fileUrl);
                fileInfo.setIsImg(Constants.YES);
                fileInfo.setType(Constants.FILE_TYPE_IMAGE);
            }
            LoginUser loginUser = SecurityUtils.getLoginUser();
            setCreateInfo(loginUser, fileInfo);
            fileInfoMapper.insert(fileInfo);
            FileInfoVO result = new FileInfoVO();
            BeanUtil.copyProperties(fileInfo, result);
            result.setCreatorName(loginUser.getUser().getNickName());
            result.setUpdaterName(result.getCreatorName());
            return result;
        } catch (Exception e) {
            log.error("文件上传错误，错误信息：{}", e.getMessage(), e);
            if (e instanceof IllegalArgumentException) {
                throw (IllegalArgumentException) e;
            } else if (e instanceof FileException) {
                throw new ServiceException(e.getMessage());
            } else {
                throw new ServiceException("文件上传错误");
            }
        } finally {
            FileUtil.del(temp);
            FileUtil.del(zipImgFile);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FileInfo uploadPic(File file, String parentId, String fileType, Long userId) throws Exception {
        File zipImgFile = null;
        try {
            FileInfo fileInfo = new FileInfo();

            //文件上传
            fileInfo.setUrl(MinioUtil.uploadFile(FileUploadUtils.getBucketName(), getNewFileName(file), Files.newInputStream(file.toPath()), FileUploadUtils.getPicFileContentType(fileType)));
            //生成缩略图
            String zipImgPath = FfmpegUtils.zipPic(file.getAbsolutePath(), null);
            zipImgFile = new File(zipImgPath);
            fileInfo.setZipUrl(MinioUtil.uploadFile(FileUploadUtils.getBucketName(), getNewFileName(zipImgFile), Files.newInputStream(zipImgFile.toPath()), "image/png"));
            fileInfo.setSuffix(fileType);
            fileInfo.setName(file.getName());
            fileInfo.setFileName(FileUtils.getName(fileInfo.getUrl()));
            fileInfo.setIsDir(Constants.NO);
            fileInfo.setIsImg(Constants.NO);
            fileInfo.setType(Constants.FILE_TYPE_FILE);
            fileInfo.setParentId(parentId);
            fileInfo.setIsImg(Constants.YES);
            fileInfo.setType(Constants.FILE_TYPE_IMAGE);
            fileInfo.setCreator(userId);
            fileInfo.setCreateTime(DateUtil.date());
            fileInfo.setUpdater(fileInfo.getCreator());
            fileInfo.setUpdateTime(fileInfo.getCreateTime());
            fileInfoMapper.insert(fileInfo);
            return fileInfo;
        } finally {
            FileUtil.del(zipImgFile);
        }
    }

    @Override
    public List<FolderOpusTreeItemVO> getFolderTree(String name, Long parentId) {
        return getChildrenFolders(name, parentId, SecurityUtils.getLoginUser());
    }

    @Override
    public FileInfo getOrCreateFolder(Long userId, String folderName, Long parentId) {
        FileInfo folder = fileInfoMapper.getFile(folderName, parentId, userId);
        if (folder == null) {
            folder = new FileInfo();
            folder.setName(folderName);
            folder.setFileName(folderName);
            folder.setType(Constants.FILE_TYPE_FOLDER);
            folder.setIsDir(Constants.YES);
            folder.setParentId(parentId.toString());
            folder.setCreator(userId);
            folder.setCreateTime(DateUtil.date());
            folder.setUpdater(userId);
            folder.setUpdateTime(folder.getCreateTime());
            fileInfoMapper.insert(folder);
        }
        return folder;
    }

    private List<FolderOpusTreeItemVO> getChildrenFolders(String name, Long parentId, LoginUser loginUser) {
        List<FolderOpusTreeItemVO> children = fileInfoMapper.getChildrenFolders(name, parentId, loginUser.getUserId());
        if (CollUtil.isNotEmpty(children)) {
            for (FolderOpusTreeItemVO child : children) {
                child.setChildren(getChildrenFolders(name, Long.parseLong(child.getId()), loginUser));
            }
        }
        return children;
    }

    private List<Long> getChildrenFolderIds(Long id) {
        List<Long> children = fileInfoMapper.getChildrenFolderIds(id);
        if (CollUtil.isNotEmpty(children)) {
            List<Long> all = new ArrayList<>();
            for (Long child : children) {
                all.addAll(getChildrenFolderIds(child));
            }
            children.addAll(all);
        }
        return children;
    }

    private void setCreateInfo(LoginUser loginUser, FileInfo fileInfo) {
        fileInfo.setCreator(loginUser.getUserId());
        fileInfo.setCreateTime(DateUtil.date());
        setUpdaterInfo(loginUser, fileInfo);
    }

    private void setUpdaterInfo(LoginUser loginUser, FileInfo fileInfo) {
        fileInfo.setUpdater(loginUser.getUserId());
        fileInfo.setUpdateTime(DateUtil.date());
    }

    private String getNewFileName(File file) {
        return StringUtils.format("{}/{}_{}.{}", DateUtils.datePath(),
                FilenameUtils.getBaseName(file.getName()), Seq.getId(Seq.uploadSeqType), Optional.ofNullable(FileTypeUtil.getType(file)).orElse("").toLowerCase());
    }

}
