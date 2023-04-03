package com.ruoyi.web.controller.other.common;

import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.framework.config.ServerConfig;
import com.ruoyi.system.domain.vo.FileVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 通用请求处理
 *
 * @author ruoyi
 */
@Tag(name = "文件接口")
@RestController
@RequestMapping("/common")
public class CommonController {

    private static final Logger log = LoggerFactory.getLogger(CommonController.class);

    private static final String FILE_SEPARATOR = ",";

    @Resource
    private ServerConfig serverConfig;

    /**
     * 通用下载请求
     *
     * @param fileName 文件名称
     * @param delete   是否删除
     */
    @Operation(summary = "通用下载请求", hidden = true)
    @GetMapping("/download")
    public void fileDownload(String fileName, Boolean delete, HttpServletResponse response) {
        try {
            Assert.isTrue(FileUtils.checkAllowDownload(fileName), StringUtils.format("文件名称({})非法，不允许下载。 ", fileName));
            String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
            String filePath = RuoYiConfig.getDownloadPath() + fileName;
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, realFileName);
            FileUtils.writeBytes(filePath, response.getOutputStream());
            if (delete) {
                FileUtils.deleteFile(filePath);
            }
        } catch (Exception e) {
            log.error("下载文件失败, 失败原因:{}", e.getMessage(), e);
        }
    }

    /**
     * 通用上传请求（单个）
     */
    @Operation(summary = "通用上传请求(单个)", hidden = true)
    @PostMapping("/upload")
    public R<FileVO> uploadFile(MultipartFile file) {
        R<FileVO> ajax;
        try {
            // 上传文件路径
            String filePath = RuoYiConfig.getUploadPath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);
            String url = serverConfig.getUrl() + fileName;
            FileVO fileInfo = new FileVO();
            fileInfo.setUrl(url);
            fileInfo.setFileName(fileName);
            fileInfo.setNewFileName(FileUtils.getName(fileName));
            fileInfo.setOriginalFilename(file.getOriginalFilename());
            ajax = R.ok(fileInfo);
        } catch (Exception e) {
            ajax = R.fail(e.getMessage());
        }
        return ajax;
    }

    /**
     * 通用上传请求（多个）
     */
    @Operation(summary = "通用上传请求(多个)", hidden = true)
    @PostMapping("/uploads")
    public R<FileVO> uploadFiles(List<MultipartFile> files) {
        R<FileVO> ajax;
        try {
            // 上传文件路径
            String filePath = RuoYiConfig.getUploadPath();
            List<String> urls = new ArrayList<>();
            List<String> fileNames = new ArrayList<>();
            List<String> newFileNames = new ArrayList<>();
            List<String> originalFilenames = new ArrayList<>();
            for (MultipartFile file : files) {
                // 上传并返回新文件名称
                String fileName = FileUploadUtils.upload(filePath, file);
                String url = serverConfig.getUrl() + fileName;
                urls.add(url);
                fileNames.add(fileName);
                newFileNames.add(FileUtils.getName(fileName));
                originalFilenames.add(file.getOriginalFilename());
            }
            FileVO fileInfo = new FileVO();
            fileInfo.setUrl(StringUtils.join(urls, FILE_SEPARATOR));
            fileInfo.setFileName(StringUtils.join(fileNames, FILE_SEPARATOR));
            fileInfo.setNewFileName(StringUtils.join(newFileNames, FILE_SEPARATOR));
            fileInfo.setOriginalFilename(StringUtils.join(originalFilenames, FILE_SEPARATOR));
            ajax = R.ok(fileInfo);
        } catch (Exception e) {
            ajax = R.fail(e.getMessage());
        }
        return ajax;
    }

    /**
     * 本地资源通用下载
     */
    @Operation(summary = "本地资源通用下载", hidden = true)
    @GetMapping("/download/resource")
    public void resourceDownload(String resource, HttpServletResponse response) {
        try {
            Assert.isTrue(FileUtils.checkAllowDownload(resource), StringUtils.format("资源文件({})非法，不允许下载。 ", resource));
            // 本地资源路径
            String localPath = RuoYiConfig.getProfile();
            // 数据库资源地址
            String downloadPath = localPath + StringUtils.substringAfter(resource, Constants.RESOURCE_PREFIX);
            // 下载名称
            String downloadName = StringUtils.substringAfterLast(downloadPath, "/");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, downloadName);
            FileUtils.writeBytes(downloadPath, response.getOutputStream());
        } catch (Exception e) {
            log.error("下载文件失败, 失败原因:{}", e.getMessage(), e);
        }
    }

    /**
     * 自定义 Minio 服务器上传请求
     */
    @Operation(summary = "文件上传minio", parameters = {
            @Parameter(name = "file", description = "文件", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "file"))
    })
    @PostMapping("/minio/upload")
    public R<FileVO> uploadFileMinio(@RequestPart MultipartFile file) {
        R<FileVO> result;
        try {
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.uploadMinio(file);
            FileVO minioFile = new FileVO();
            minioFile.setUrl(fileName);
            minioFile.setNewFileName(FileUtils.getName(fileName));
            minioFile.setFileName(minioFile.getNewFileName());
            minioFile.setOriginalFilename(file.getOriginalFilename());
            result = R.ok(minioFile);
        } catch (Exception e) {
            result = R.fail(e.getMessage());
        }
        return result;
    }

}
