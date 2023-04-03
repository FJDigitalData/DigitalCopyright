package com.ruoyi.common.utils.file;

import cn.hutool.core.io.IoUtil;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * Minio 文件存储工具类
 *
 * @author ruoyi
 */
public class MinioUtil {

    /**
     * 上传文件
     *
     * @param bucketName    桶名称
     * @param fileName      文件名称
     * @param multipartFile 文件
     * @throws IOException
     */
    public static String uploadFile(String bucketName, String fileName, MultipartFile multipartFile) throws IOException {
        return uploadFile(bucketName, fileName, multipartFile.getInputStream(), multipartFile.getContentType());
    }

    public static String uploadFile(String bucketName, String fileName, InputStream is, String contentType) throws IOException {
        MinioClient minioClient = SpringUtils.getBean(MinioClient.class);
        try {
            minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(fileName)
                    .stream(is, is.available(), -1).contentType(contentType).build());
            String url = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().bucket(bucketName).object(fileName).method(Method.GET).build());
            url = url.substring(0, url.indexOf('?'));
            return ServletUtils.urlDecode(url);
        } catch (Exception e) {
            throw new IOException(e.getMessage(), e);
        } finally {
            IoUtil.close(is);
        }
    }

}
