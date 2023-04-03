package com.ruoyi.common.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.system.SystemUtil;
import com.ruoyi.common.constant.Constants;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author WangJiang
 * @since 2023/1/3 13:34
 */
@Slf4j
public class FfmpegUtils {

    /**
     * 最小开启16倍压缩尺寸
     */
    private static final int ENABLE_16_MIN_SIZE = 1000;

    /**
     * 最小开启32倍压缩尺寸
     */
    private static final int ENABLE_32_MIN_SIZE = 10000;

    private FfmpegUtils() {
    }

    public static boolean checkFfmpegExist() {
        String path = null,
                result = SystemUtil.getOsInfo().isWindows() ?
                        RuntimeUtil.execForStr("Get-Command ffmpeg exit") :
                        RuntimeUtil.execForStr("/usr/bin/which ffmpeg exit");
        if (StrUtil.isNotBlank(result)) {
            path = result.trim();
            if (SystemUtil.getOsInfo().isWindows()) {
                final String regex = "([A-Za-z]:\\\\.*\\.exe)";
                final Pattern pattern = Pattern.compile(regex);
                final Matcher matcher = pattern.matcher(result);
                if (matcher.find()) {
                    path = matcher.group(1);
                }
            }
        }
        final File file = path == null ? null : new File(path.trim());
        return file != null && file.exists() && file.canExecute();
    }

    /**
     * 压缩图片
     *
     * @param picPath 要压缩的图片地址
     * @param param   压缩参数 -lossless 0 -q 100（无损压缩 压缩率：100） 调整图片大小用:-vf scale=iw/4:ih/4 -lossless 0 -q 100(默认)
     * @return 压缩后的图片地址
     */
    public static String zipPic(String picPath, String param) {
        Assert.isTrue(FileUtil.exist(picPath), "要压缩的文件不存在");
        Assert.isTrue(checkFfmpegExist(), "Ffmpeg工具不存在");
        String newFilePath = picPath.substring(0, picPath.lastIndexOf(".")) + System.currentTimeMillis() + ".png";
        if (StrUtil.isBlank(param)) {
            File pic = new File(picPath);
            int zipSize = 4;
            //10兆以下图片采用4倍 其他的根据尺寸中的宽度
            if (pic.length() > Constants.TEN_MB) {
                try {
                    BufferedImage image = ImageIO.read(pic);
                    if (image.getWidth() < ENABLE_16_MIN_SIZE) {
                        zipSize = 8;
                    } else if (image.getWidth() < ENABLE_32_MIN_SIZE) {
                        zipSize = 16;
                    }
                } catch (Exception ignore) {

                }
            }
            param = String.format("-vf scale=iw/%s:ih/%s -lossless 0 -q 100", zipSize, zipSize);
        }
        String zipResult = RuntimeUtil.execForStr(String.format("ffmpeg -i %s %s %s", picPath, param, newFilePath));
        log.info(zipResult);
        Assert.isTrue(FileUtil.exist(newFilePath), "压缩图片文件失败");
        return newFilePath;
    }

}
