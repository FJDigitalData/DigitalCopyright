package com.ruoyi.common.utils.translate.youdao;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.translate.BaseTranslate;
import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.util.Assert;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WangJiang
 * @since 2023/2/8 15:29
 */
@Slf4j
public class YouDaoTransApi extends BaseTranslate {

    private static final char[] HEX_DIGEST_ARRAY = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private static final String SHA_256 = "SHA-256";

    private static final String URL = "https://openapi.youdao.com/v2/api";

    public YouDaoTransApi(String appId, String secret) {
        super(URL, appId, secret);
    }

    @Override
    public JSONArray translate(String from, String to, List<String> texts) {
        Map<String, String> params = buildParams(from, to, texts);
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey(),
                    value = entry.getValue();
            builder.add(key, value);
        }
        for (String q : texts) {
            builder.add("q", q);
        }
        try (Response response = new OkHttpClient().newCall(new Request.Builder().url(getUrl()).post(builder.build()).build()).execute()) {
            if (response.isSuccessful()) {
                Assert.notNull(response.body(), "翻译失败，有道云没有响应翻译结果");
                String jsonResultStr = response.body().string();
                JSONObject originResult = JSONUtil.parseObj(jsonResultStr);
                String errorCode = originResult.getStr("errorCode");
                String resultKey = "translateResults", successCode = "0";
                if (successCode.equals(errorCode)) {
                    JSONObject transResultObj, tem;
                    JSONArray array = new JSONArray();
                    for (Object transResult : originResult.getJSONArray(resultKey)) {
                        transResultObj = (JSONObject) transResult;
                        tem = new JSONObject();
                        tem.set("src", transResultObj.getStr("query"));
                        tem.set("dst", transResultObj.getStr("translation"));
                        array.add(tem);
                    }
                    return array;
                } else {
                    log.error("翻译失败，返回：str：{}, json:{}", jsonResultStr, originResult);
                    throw new IllegalArgumentException("翻译失败，错误码：" + errorCode);
                }
            } else {
                log.error("翻译失败，返回：{}", response);
                throw new IllegalArgumentException("翻译失败");
            }
        } catch (Exception e) {
            if (e instanceof IllegalArgumentException) {
                throw (IllegalArgumentException) e;
            } else {
                log.error("翻译失败，错误信息：{}", e.getMessage(), e);
                throw new IllegalArgumentException("翻译失败");
            }
        }
    }

    private Map<String, String> buildParams(String from, String to, List<String> texts) {
        Map<String, String> params = new HashMap<>(16);
        String salt = String.valueOf(System.currentTimeMillis());
        params.put("from", Constants.YD_LANGUAGE_MAP.getOrDefault(from, from));
        params.put("to", Constants.YD_LANGUAGE_MAP.getOrDefault(to, to));
        params.put("signType", "v3");
        String currentTime = String.valueOf(System.currentTimeMillis() / 1000);
        params.put("curtime", currentTime);
        String signStr = getAppId() + truncate(texts) + salt + currentTime + getSecret();
        String sign = getDigest(signStr);
        params.put("appKey", getAppId());
        params.put("salt", salt);
        params.put("sign", sign);
        return params;
    }
    /**
     * 生成加密字段
     */
    private static String getDigest(String text) {
        String result = null;
        if (StrUtil.isNotBlank(text)) {
            try {
                MessageDigest mdInst = MessageDigest.getInstance(SHA_256);
                mdInst.update(text.getBytes(StandardCharsets.UTF_8));
                byte[] md = mdInst.digest();
                int j = md.length;
                char[] str = new char[j * 2];
                int k = 0;
                for (byte byte0 : md) {
                    str[k++] = HEX_DIGEST_ARRAY[byte0 >>> 4 & 0xf];
                    str[k++] = HEX_DIGEST_ARRAY[byte0 & 0xf];
                }
                result = new String(str);
            } catch (Exception e) {
                log.error("生成加密字段出错，错误信息:{}", e.getMessage(), e);
            }
        }
        return result;
    }

    private static String truncate(List<String> qList) {
        if (qList == null) {
            return null;
        }
        String batchQStr = String.join("", qList);
        int len = batchQStr.length();
        return len <= 20 ? batchQStr : (batchQStr.substring(0, 10) + len + batchQStr.substring(len - 10, len));
    }

    //public static void main(String[] args) throws Exception {
    //    YouDaoTransApi transApi = new YouDaoTransApi("759544cc322afb1b", "MkRfoRTN4iSe8k7RbovJKELryuLjbqLi");
    //    Map<String, String> params = new HashMap<>(16);
    //    List<String> qArray = new ArrayList<>();
    //    qArray.add("待输入的文字1");
    //    qArray.add("待输入的文字2");
    //    qArray.add("待输入的文字3");
    //    String salt = String.valueOf(System.currentTimeMillis());
    //    params.put("from", "zh-CHS");
    //    params.put("to", "en");
    //    params.put("signType", "v3");
    //    String curtime = String.valueOf(System.currentTimeMillis() / 1000);
    //    params.put("curtime", curtime);
    //    String signStr = transApi.getAppId() + truncate(qArray) + salt + curtime + transApi.getSecret();
    //    String sign = getDigest(signStr);
    //    params.put("appKey", transApi.getAppId());
    //    params.put("salt", salt);
    //    params.put("sign", sign);
    //    // params.put("vocabId", "您的用户词表ID");
    //
    //    /** 处理结果 */
    //    System.out.println(transApi.requestForHttp(params, qArray)); ;
    //}

}
