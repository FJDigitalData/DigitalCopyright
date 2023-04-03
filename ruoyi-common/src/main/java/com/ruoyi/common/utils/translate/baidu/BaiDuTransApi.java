package com.ruoyi.common.utils.translate.baidu;

import cn.hutool.core.lang.Assert;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.ruoyi.common.utils.translate.BaseTranslate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangjiang
 */
@Slf4j
public class BaiDuTransApi extends BaseTranslate {

    private static final String URL = "http://api.fanyi.baidu.com/api/trans/vip/translate";

    public BaiDuTransApi(String appId, String secret) {
        super(URL, appId, secret);
    }

    @Override
    public JSONArray translate(String from, String to, List<String> texts) {
        try {
            String text = dealText(texts);
            Map<String, String> params = buildParams(text, from, to);
            String resultStr = HttpRequest.post(getUrl()).formStr(params).contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE).execute().body();
            log.info("翻译返回: {}", resultStr);
            JSONObject result = JSONUtil.parseObj(resultStr);
            String resultKey = "trans_result";
            if (result.containsKey(resultKey)) {
                JSONArray resultArray = result.getJSONArray(resultKey);
                Assert.isTrue(resultArray != null && !resultArray.isEmpty(), "没有获取到翻译结果");
               return resultArray;
            } else {
                log.error("翻译失败，错误代码: " + result.getStr("error_code") + ", 错误信息: " + result.getStr("error_msg"));
                throw new IllegalArgumentException("翻译失败，错误信息: " + result.getStr("error_msg"));
            }
        } catch (Exception e) {
            if (!(e instanceof IllegalArgumentException)) {
                log.error("翻译失败，出现错误: {}", e.getMessage(), e);
                throw new IllegalArgumentException("商品翻译失败");
            } else {
                throw e;
            }
        }
    }

    private String dealText(List<String> texts) {
        return String.join("\n", texts);
    }

    private Map<String, String> buildParams(String query, String from, String to) {
        Map<String, String> params = new HashMap<>(16);
        params.put("q", query);
        params.put("from", from);
        params.put("to", to);

        params.put("appid", getAppId());

        // 随机数
        String salt = String.valueOf(System.currentTimeMillis());
        params.put("salt", salt);


        // 加密前的原文
        String src = getAppId() + query + salt + getSecret();
        // 签名
        params.put("sign", MD5.create().digestHex(src));

        return params;
    }

    //public static void main(String[] args) {
    //    String appId = "20230208001554065", secret = "rKR2JC_F1AltBeifAtP8";
    //    BaiDuTransApi api = new BaiDuTransApi(appId, secret);
    //
    //    String text = "众里寻她千百度\n暮然回首那人却在灯火阑珊处\n我\n是\n谁\n水\n在\n哪\n里";
    //
    //    String from = "zh", to = "en";
    //    System.out.println(from + "->" + to + ":result:" + api.translate(from, to, text));
    //
    //    to = "fra";
    //    System.out.println(from + "->" + to + ":result:" + api.translate(from, to, text));
    //
    //    to = "th";
    //    System.out.println(from + "->" + to + ":result:" + api.translate(from, to, text));
    //
    //    to = "hu";
    //    System.out.println(from + "->" + to + ":result:" + api.translate(from, to, text));
    //
    //    to = "frn";
    //    System.out.println(from + "->" + to + ":result:" + api.translate(from, to, text));
    //
    //    to = "jp";
    //    System.out.println(from + "->" + to + ":result:" + api.translate(from, to, text));
    //}

}
