package com.ruoyi.web.controller.business;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.dto.TagDTO;
import com.ruoyi.system.domain.vo.TagVO;
import com.ruoyi.system.service.ITagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author WangJiang
 * @since 2023/3/24 10:00
 */
@Tag(name = "标签接口")
@RestController
@RequestMapping("/business/tag")
public class TagController extends BaseController {

    @Resource
    private ITagService iTagService;

    @PostMapping
    @Operation(summary = "新增")
    //@PreAuthorize("@ss.hasPermi('business:tag:add')")
    public R<TagVO> add(@RequestBody @Validated TagDTO tag) {
        return R.ok(iTagService.add(tag));
    }

    @DeleteMapping("/{ids}")
    @Operation(summary = "删除", parameters = {
            @Parameter(name = "ids", description = "id 多个ID时以英文逗号分隔", in = ParameterIn.PATH, schema = @Schema(type = "string"), required = true)
    })
    //@PreAuthorize("@ss.hasPermi('business:tag:delete')")
    public R<Integer> delete(@PathVariable("ids") List<String> ids) {
        return R.ok(iTagService.delete(ids));
    }

    @PutMapping("/{id}")
    @Operation(summary = "修改", parameters = {
            @Parameter(name = "id", description = "id", in = ParameterIn.PATH, schema = @Schema(type = "string"), required = true)
    })
    //@PreAuthorize("@ss.hasPermi('business:tag:edit')")
    public R<TagVO> update(@PathVariable("id") String id,
                           @RequestBody @Validated TagDTO tag) {
        return R.ok(iTagService.update(id, tag));
    }

    @GetMapping("/{id}/detail")
    @Operation(summary = "查询详情", parameters = {
            @Parameter(name = "id", description = "id", in = ParameterIn.PATH, schema = @Schema(type = "string"), required = true)
    })
    //@PreAuthorize("@ss.hasPermi('business:tag:get')")
    public R<TagVO> get(@PathVariable("id") String id) {
        return R.ok(iTagService.get(id));
    }

    @GetMapping("/page")
    @Operation(summary = "查询分页列表", parameters = {
            @Parameter(name = "searchKey", description = "模糊匹配风格名称", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "styleId", description = "风格id", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "tag", description = "模糊匹配标签", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "pageNum", description = "页码 默认：1", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "1")),
            @Parameter(name = "pageSize", description = "每页数量 默认：10", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "10"))
    })
    //@PreAuthorize("@ss.hasPermi('business:tag:get')")
    public TableDataInfo<TagVO> page(@RequestParam(required = false) String searchKey,
                                     @RequestParam(required = false) String styleId,
                                     @RequestParam(required = false) String tag,
                                     @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                     @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        return iTagService.page(searchKey, styleId, tag, pageNum, pageSize);
    }

    @GetMapping("/list")
    @Operation(summary = "查询列表", parameters = {
            @Parameter(name = "searchKey", description = "模糊匹配风格名称", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "styleId", description = "风格id", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "tag", description = "模糊匹配标签", in = ParameterIn.QUERY, schema = @Schema(type = "string"))
    })
    //@PreAuthorize("@ss.hasPermi('business:tag:get')")
    public R<List<TagVO>> list(@RequestParam(required = false) String searchKey,
                               @RequestParam(required = false) String styleId,
                               @RequestParam(required = false) String tag) {
        return R.ok(iTagService.list(searchKey, styleId, tag));
    }

}
