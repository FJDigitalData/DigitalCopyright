package com.ruoyi.common.constant;

import io.jsonwebtoken.Claims;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用常量信息
 *
 * @author ruoyi
 */
public class Constants {
    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * GBK 字符集
     */
    public static final String GBK = "GBK";

    /**
     * www主域
     */
    public static final String WWW = "www.";

    /**
     * http请求
     */
    public static final String HTTP = "http://";

    /**
     * https请求
     */
    public static final String HTTPS = "https://";

    /**
     * 通用成功标识
     */
    public static final String SUCCESS = "0";

    /**
     * 通用失败标识
     */
    public static final String FAIL = "1";

    /**
     * 登录成功
     */
    public static final String LOGIN_SUCCESS = "Success";

    /**
     * 注销
     */
    public static final String LOGOUT = "Logout";

    /**
     * 注册
     */
    public static final String REGISTER = "Register";

    /**
     * 登录失败
     */
    public static final String LOGIN_FAIL = "Error";

    /**
     * 验证码有效期（分钟）
     */
    public static final Integer CAPTCHA_EXPIRATION = 2;

    /**
     * 令牌
     */
    public static final String TOKEN = "token";

    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 令牌前缀
     */
    public static final String LOGIN_USER_KEY = "login_user_key";

    /**
     * 用户ID
     */
    public static final String JWT_USERID = "userid";

    /**
     * 用户名称
     */
    public static final String JWT_USERNAME = Claims.SUBJECT;

    /**
     * 用户头像
     */
    public static final String JWT_AVATAR = "avatar";

    /**
     * 创建时间
     */
    public static final String JWT_CREATED = "created";

    /**
     * 用户权限
     */
    public static final String JWT_AUTHORITIES = "authorities";

    /**
     * 资源映射路径 前缀
     */
    public static final String RESOURCE_PREFIX = "/profile";

    /**
     * RMI 远程方法调用
     */
    public static final String LOOKUP_RMI = "rmi:";

    /**
     * LDAP 远程方法调用
     */
    public static final String LOOKUP_LDAP = "ldap:";

    /**
     * LDAPS 远程方法调用
     */
    public static final String LOOKUP_LDAPS = "ldaps:";

    /**
     * 定时任务白名单配置（仅允许访问的包名，如其他需要可以自行添加）
     */
    public static final String[] JOB_WHITELIST_STR = {"com.ruoyi"};

    /**
     * 定时任务违规的字符
     */
    public static final String[] JOB_ERROR_STR = {"java.net.URL", "javax.naming.InitialContext", "org.yaml.snakeyaml",
            "org.springframework", "org.apache", "com.ruoyi.common.utils.file", "com.ruoyi.common.config"};

    /**
     * 短信登录验证码redis key
     */
    public static final String SMS_LOGIN_REDIS_KEY = "sms:login:%s:%s";

    /**
     * 短信登录验证码类型
     */
    public static final int SMS_TYPE_LOGIN = 1;

    /**
     * 注册短信验证码类型
     */
    public static final int SMS_TYPE_REGISTER = 2;

    /**
     * 注册短信验证码redis key
     */
    public static final String SMS_REGISTER_REDIS_KEY = "sms:register:%s:%s";

    /**
     * 修改密码验证码类型
     */
    public static final int SMS_TYPE_RESET_PASSWORD = 3;

    /**
     * 修改密码短信验证码redis key
     */
    public static final String SMS_RESET_PASSWORD_REDIS_KEY = "sms:reset-password:%s:%s";

    /**
     * 创作者
     */
    public static final Long ROLE_CREATOR = 3L;

    /**
     * 电商售卖者
     */
    public static final Long ROLE_SALESPERSON = 4L;

    /**
     * 运维
     */
    public static final Long ROLE_MANAGER = 5L;

    /**
     * 用户类型-系统用户
     */
    public static final String USER_TYPE_SYSTEM = "00";

    /**
     * 用户类型-创作者
     */
    public static final String USER_TYPE_CREATOR = "01";

    /**
     * 用户类型-电商售卖者
     */
    public static final String USER_TYPE_SALESPERSON = "02";

    /**
     * 作品风格字典key
     */
    public static final String SYS_OPUS_STYLE = "sys_opus_style";

    /**
     * 顶级目录id
     */
    public static final Long TOP_DIC_ID = 0L;

    /**
     * 顶级目录id
     */
    public static final String TOP_DIC_ID_STR = "0";

    /**
     * 受支持的图片类型
     */
    public static final String[] IMG_TYPE_ARRAY = {
            "jpg",
            "png",
            "jpeg",
            "gif",
            "bmp"
    };

    /**
     * 是
     */
    public static final String YES = "Y";

    /**
     * 否
     */
    public static final String NO = "N";

    /**
     * 文件类型-文件夹
     */
    public static final String FILE_TYPE_FOLDER = "folder";

    /**
     * 文件类型-图片
     */
    public static final String FILE_TYPE_IMAGE = "image";

    /**
     * 文件类型-文件
     */
    public static final String FILE_TYPE_FILE = "file";

    /**
     * 根目录名称
     */
    public static final String TOP_FOLDER = "/";

    /**
     * 状态- 正常
     */
    public static final String STATUS_NORMAL = "0";

    /**
     * 状态- 停用
     */
    public static final String STATUS_DEACTIVATE = "1";

    /**
     * 搜索热词Redis关键key
     */
    public static final String HOT_KEY = "search_hot_key";

    /**
     * 1M的比特值
     */
    public static final long ONE_MB = 8388608L;

    /**
     * 10M的比特值
     */
    public static final long TEN_MB = 10 * 8388608L;

    /**
     * 货币代码
     */
    public static final String SYS_CURRENCY_CODE = "sys_currency_code";

    public static final String EU_WEST_1 = "eu-west-1";

    /**
     * 启用
     */
    public static final int ENABLE = 1;

    /**
     * 不启用
     */
    public static final int DISABLE = 0;

    /**
     * 有道翻译语言支持与系统语言对应关系
     */
    public static final Map<String, String> YD_LANGUAGE_MAP = new HashMap<>(16);

    /**
     * 支付宝支付
     */
    public static final String ALI_PAY = "ali";

    /**
     * 微信支付
     */
    public static final String WX_PAY = "wx";

    public static final String WECHAT_PLAT_FORM_CERT = "wechat_plat_form_cert";


    static {
        YD_LANGUAGE_MAP.put("zh", "zh-CHS");
        YD_LANGUAGE_MAP.put("fra", "fr");
        YD_LANGUAGE_MAP.put("frn", "fr");
        YD_LANGUAGE_MAP.put("spa", "es");
        YD_LANGUAGE_MAP.put("jp", "ja");
        YD_LANGUAGE_MAP.put("ara", "ar");
        YD_LANGUAGE_MAP.put("may", "ms");
        YD_LANGUAGE_MAP.put("tam", "ta");
        YD_LANGUAGE_MAP.put("swe", "sv");
        //YD_LANGUAGE_MAP.put("pl", "pl");
        //YD_LANGUAGE_MAP.put("it", "it");
        //YD_LANGUAGE_MAP.put("de", "de");
        //YD_LANGUAGE_MAP.put("en", "en");
        //YD_LANGUAGE_MAP.put("tr", "tr");
        //YD_LANGUAGE_MAP.put("pt", "pt");
        //YD_LANGUAGE_MAP.put("hi", "hi");
        //YD_LANGUAGE_MAP.put("nl", "nl");
    }

    public static final String FALSE = "false";

    public static final String TRUE = "true";

}
