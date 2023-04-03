package com.ruoyi.system.task;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.StopWatch;
import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ArrayUtil;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.system.domain.FileInfo;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.service.IFileInfoService;
import com.ruoyi.system.service.IOpusService;
import com.ruoyi.system.service.ISysDictDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 自动将文件转为作品
 *
 * @author WangJiang
 * @since 2023/1/11 10:50
 */
@Slf4j
@Component("createOpusTask")
public class AutoCreateOpusTask {

    private static final String PROCESSED = "*processed";

    @Resource
    private IOpusService iOpusService;

    @Resource
    private IFileInfoService iFileInfoService;

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private ISysDictDataService iSysDictDataService;

    public void autoCreateOpus(Boolean autoAuthorization, Boolean autoOpen) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            //扫描目录下文件
            File[] userFiles = FileUtil.ls(RuoYiConfig.getAutoCreateOpusPath());
            SysUser sysUser;
            List<File> legalUserFolder = new ArrayList<>(userFiles.length);
            Map<String, SysUser> user = new HashMap<>(16);
            //过滤合法的文件夹
            for (File userFile : userFiles) {
                //只处理目录并且目录不以(*processed)结尾
                if (FileUtil.isNotEmpty(userFile) && !userFile.getName().trim().endsWith(PROCESSED)) {
                    sysUser = null;
                    try {
                        //查询目录名称代表的文件夹是否存在
                        sysUser = sysUserMapper.selectUserById(Long.parseLong(userFile.getName().trim()));
                    } catch (Exception ignore) {

                    }
                    if (sysUser == null) {
                        log.error("找不到id为:{}的用户", userFile.getName());
                        continue;
                    }
                    user.put(userFile.getName().trim(), sysUser);
                    legalUserFolder.add(userFile);
                }
            }
            if (CollUtil.isNotEmpty(legalUserFolder)) {
                List<File> legalOpusFolder;
                File[] opusFiles;
                Long userId, styleCode;
                FileInfo folderFileInfo, opusFileInfo;
                //处理文件信息
                Map<String, Long> styleCache = new HashMap<>(16);
                for (File userFolder : legalUserFolder) {
                    legalOpusFolder = Arrays.stream(FileUtil.ls(userFolder.getAbsolutePath())).filter(FileUtil::isNotEmpty).collect(Collectors.toList());
                    userId = user.get(userFolder.getName().trim()).getUserId();
                    if (CollUtil.isNotEmpty(legalOpusFolder)) {
                        String style, tag, name, split = "-";
                        for (File opusFolder : legalOpusFolder) {
                            name = opusFolder.getName().trim();
                            if (name.contains(split)) {
                                style = name.substring(0, name.lastIndexOf("-"));
                                try {
                                    tag = name.substring(name.lastIndexOf("-") + 1);
                                } catch (Exception e) {
                                    tag = style;
                                }
                            } else {
                                style = name;
                                tag = style;
                            }
                            //处理作品文件夹 文件夹名称为 风格-标签 检查风格是否存在
                            if (!styleCache.containsKey(style)) {
                                styleCode = iSysDictDataService.selectDictCode(Constants.SYS_OPUS_STYLE, style);
                                styleCache.put(style, styleCode);
                            } else {
                                styleCode = styleCache.get(style);
                            }
                            if (styleCode == null) {
                                log.error("文件夹: {}对应风格不存在，放弃处理", opusFolder.getAbsolutePath());
                            } else {
                                //检查风格名代表的目录是否存在不存在就创建
                                folderFileInfo = iFileInfoService.getOrCreateFolder(userId, style, Constants.TOP_DIC_ID);
                                //准备处理
                                opusFiles = FileUtil.ls(opusFolder.getAbsolutePath());
                                for (File opusFile : opusFiles) {
                                    String fileType = Optional.ofNullable(FileTypeUtil.getType(opusFile)).orElse("").toLowerCase();
                                    //不处理非图片文件
                                    if (ArrayUtil.contains(Constants.IMG_TYPE_ARRAY, fileType)) {
                                        try {
                                            //文件上传
                                            opusFileInfo = iFileInfoService.uploadPic(opusFile, folderFileInfo.getId().toString(), fileType, userId);
                                            //创建作品
                                            iOpusService.createOpus(opusFileInfo, styleCode, tag, userId, autoAuthorization, autoOpen);
                                        } catch (Exception e) {
                                            log.error("文件：{}处理失败,错误信息:{}", opusFile.getAbsolutePath(), e.getMessage(), e);
                                        }
                                    } else {
                                        log.error("文件：{}不是图片文件放弃处理", opusFile.getAbsolutePath());
                                    }
                                }
                            }
                        }
                    }
                    //将文件夹标为已处理
                    FileUtil.rename(userFolder, userFolder.getName() + PROCESSED, false);
                }
            }
        } catch (Exception e) {
            log.error("自行将文件转为作品失败，错误信息:{}", e.getMessage(), e);
            throw new ServiceException("自行将文件转为作品失败");
        } finally {
            stopWatch.stop();
            log.info("将文件转为作品结束，耗时:{}", stopWatch.getTotalTimeSeconds());
        }
    }

}
