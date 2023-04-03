package com.ruoyi.system.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author WangJiang
 * @since 2023/1/6 14:10
 */
@Data
public class Empower implements Serializable {

    private static final long serialVersionUID = 4044718699561183817L;

    /**
     * 作品id
     */
    private String opusId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 删除标识（0-正常,1-删除）
     */
    private String delFlag;

    /**
     * 创建人
     */
    private Long creator;

    /**
     * 创建时间
     */
    private Date createTime;

}
