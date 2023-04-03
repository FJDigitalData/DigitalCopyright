package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author WangJiang
 * @since 2023/3/6 10:49
 */
@Data
@TableName("t_translate_config")
public class TranslateConfig implements Serializable {

    private static final long serialVersionUID = -1212434979879260835L;

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 翻译类型 0: 百度翻译 1：有道翻译
     */
    private Integer apiType;

    /**
     * 翻译API的appId
     */
    private String appId;

    /**
     * 翻译API的密钥
     */
    private String securityKey;

    /**
     * 公司id
     */
    private String companyId;

    @TableLogic(value = "0", delval = "1")
    /**
     * 删除标识（0-正常,1-删除）
     */
    private String delFlag;

    /**
     * 是否启用（0-不启用,1-启用）
     */
    private Integer enable;

    /**
     * 创建人
     */
    private Long creator;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private Long updater;

    /**
     * 更新时间
     */
    private Date updateTime;

}
