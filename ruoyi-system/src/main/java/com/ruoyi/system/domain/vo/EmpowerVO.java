package com.ruoyi.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author WangJiang
 * @since 2023/1/10 09:14
 */
@Data
@Schema(name = "empowerVO", description = "作品授权信息VO")
public class EmpowerVO implements Serializable {

    private static final long serialVersionUID = 7756596396076824041L;

    @Schema(description = "请求授权作品id")
    private String opusId;

    @Schema(description = "请求授权用户id")
    private Long userId;

    @Schema(description = "请求授权用户昵称")
    private String userName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "请求授权时间")
    private Date createTime;

}
