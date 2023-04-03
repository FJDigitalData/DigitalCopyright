package com.ruoyi.common.utils.translate;

import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONArray;
import com.ruoyi.common.utils.translate.baidu.BaiDuTransApi;
import com.ruoyi.common.utils.translate.youdao.YouDaoTransApi;
import lombok.Data;

import java.util.List;

/**
 * @author WangJiang
 * @since 2023/3/6 14:20
 */
@Data
public abstract class BaseTranslate {

    private String url;

    private String appId;

    private String secret;

    public BaseTranslate(String url, String appId, String secret) {
        this.url = url;
        this.appId = appId;
        this.secret = secret;
    }

    /**
     * 翻译
     * @param from 源语言
     * @param to  目标语言
     * @param texts 待翻译文本
     * @return 翻译结果
     */
    public abstract JSONArray translate(String from, String to, List<String> texts);

    public static BaseTranslate translateFactory(Integer type, String appId, String secret) {
        Assert.notNull(type, "翻译API类别错误");
        if (type.equals(0)) {
            return new BaiDuTransApi(appId, secret);
        } else if (type.equals(1)) {
            return new YouDaoTransApi(appId, secret);
        } else {
            throw new IllegalArgumentException("翻译API类别错误");
        }
    }

}
