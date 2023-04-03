package com.ruoyi.system.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author WangJiang
 * @since 2023/1/6 14:09
 */
@Data
public class Opus implements Serializable {

    public static final String UN_OPEN = "0";

    public static final String OPEN = "1";

    public static final String UN_FREEZE = "0";

    public static final String FREEZE = "1";

    /**
     * 版权-未申请
     */
    public static final String UN_APPLY = "0";

    /**
     * 版权-待审核
     */
    public static final String UN_AUDIT = "1";

    /**
     * 版权-驳回
     */
    public static final String REFUSE = "2";

    /**
     * 版权-审核通过
     */
    public static final String PASS = "3";

    private static final long serialVersionUID = 6204706761983795985L;

    /**
     * id
     */
    private String id;

    /**
     * 作品名称
     */
    private String name;

    /**
     * 作品风格
     */
    private String style;

    /**
     * 作品寓意
     */
    private String inspiration;

    /**
     * 是否上架 0:未上架 1:已上架
     */
    private String isOpen;

    /**
     * 是否冻结 0:未冻结 1:已冻结
     */
    private String isFreeze;

    /**
     * 文件id
     */
    private Long fileId;

    /**
     * 素材包文件id
     */
    private Long materialFileId;

    /**
     * 目录id 顶级目录为0
     */
    private Long parentId;

    /**
     * 版权拥有者
     */
    private Long copyrightOwner;

    /**
     * 版权申请状态 0：未申请 1：待审核 2：驳回 3：审核通过
     */
    private String applyStatus;

    /**
     * 版权申请时间
     */
    private Date applyTime;

    /**
     * 驳回理由
     */
    private String applyRemark;

    /**
     * 区块版权标识
     */
    private String blockIdentity;

    /**
     * 浏览量
     */
    private Long hot;

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

    /**
     * 更新人
     */
    private Long updater;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 价格
     */
    private Double price;

}
