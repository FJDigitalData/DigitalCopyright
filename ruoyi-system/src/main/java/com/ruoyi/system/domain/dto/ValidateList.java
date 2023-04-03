package com.ruoyi.system.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @param <E> 泛型
 * @author WangJiang
 * @ClassName ValidateList
 * @apiNote 可被校验的list
 * @date 2019-04-30 11:28
 **/
@Data
@Schema(name = "validateList", description = "可被校验的list")
public class ValidateList<E> implements Serializable {

    private static final long serialVersionUID = 8997560986903041148L;

    @Valid
    @NotEmpty(message = "数据不能为空")
    private List<E> list;

}
