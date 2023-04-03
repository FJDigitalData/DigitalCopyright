package com.ruoyi.web.controller.business;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.dto.StoreDTO;
import com.ruoyi.system.domain.vo.StoreVO;
import com.ruoyi.system.service.IStoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author WangJiang
 * @since 2023/3/2 11:25
 */
@Tag(name = "店铺接口")
@RestController
@RequestMapping("/business/store")
public class StoreController extends BaseController {

    @Resource
    private IStoreService iStoreService;

    @PostMapping
    @Operation(summary = "新增")
    //@PreAuthorize("@ss.hasPermi('business:store:add')")
    public R<StoreVO> add(@RequestBody @Validated StoreDTO store) {
        return R.ok(iStoreService.add(store));
    }

    @DeleteMapping("/{ids}")
    @Operation(summary = "删除", parameters = {
            @Parameter(name = "ids", description = "店铺id 多个ID时以英文逗号分隔", in = ParameterIn.PATH, schema = @Schema(type = "long"), required = true)
    })
    //@PreAuthorize("@ss.hasPermi('business:store:delete')")
    public R<Integer> delete(@PathVariable("ids") List<Long> ids) {
        return R.ok(iStoreService.delete(ids));
    }

    @PutMapping("/{id}")
    @Operation(summary = "修改", parameters = {
            @Parameter(name = "id", description = "店铺id", in = ParameterIn.PATH, schema = @Schema(type = "long"), required = true)
    })
    //@PreAuthorize("@ss.hasPermi('business:store:edit')")
    public R<StoreVO> update(@PathVariable("id") Long id,
                             @RequestBody @Validated StoreDTO store) {
        return R.ok(iStoreService.update(id, store));
    }

    @GetMapping("/{id}/detail")
    @Operation(summary = "查询详情", parameters = {
            @Parameter(name = "id", description = "店铺id", in = ParameterIn.PATH, schema = @Schema(type = "long"), required = true),
            @Parameter(name = "shouldIgnoreDelete", description = "是否被删除的店铺依然可以被查询到 true:是 false:否（默认）", in = ParameterIn.QUERY, schema = @Schema(type = "boolean", allowableValues = {"true", "false"}))
    })
    //@PreAuthorize("@ss.hasPermi('business:store:get')")
    public R<StoreVO> get(@PathVariable("id") Long id,
                          @RequestParam(required = false, defaultValue = "false") Boolean shouldIgnoreDelete) {
        return R.ok(iStoreService.get(id, shouldIgnoreDelete));
    }

    @GetMapping("/page")
    @Operation(summary = "查询分页列表", parameters = {
            @Parameter(name = "name", description = "模糊匹配店铺名称", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "pageNum", description = "页码 默认：1", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "1")),
            @Parameter(name = "pageSize", description = "每页数量 默认：10", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "10"))
    })
    //@PreAuthorize("@ss.hasPermi('business:store:get')")
    public TableDataInfo<StoreVO> page(@RequestParam(required = false) String name,
                                       @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                       @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        return iStoreService.page(name, pageNum, pageSize);
    }

    @GetMapping("/list")
    @Operation(summary = "查询列表", parameters = {
            @Parameter(name = "name", description = "模糊匹配店铺名称", in = ParameterIn.QUERY, schema = @Schema(type = "string"))
    })
    //@PreAuthorize("@ss.hasPermi('business:store:get')")
    public R<List<StoreVO>> list(@RequestParam(required = false) String name) {
        return R.ok(iStoreService.list(name));
    }


}
