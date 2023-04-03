package com.ruoyi.web.controller.business;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.dto.ErpProductDTO;
import com.ruoyi.system.domain.dto.ProductAdoptDTO;
import com.ruoyi.system.domain.dto.ProductReleaseDTO;
import com.ruoyi.system.domain.dto.ValidateList;
import com.ruoyi.system.domain.vo.ErpProductVO;
import com.ruoyi.system.service.IErpProductService;
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
 * @since 2023/2/7 10:32
 */
@Tag(name = "商品接口")
@RestController
@RequestMapping("/business/erp-product")
public class ErpProductController extends BaseController {

    @Resource
    private IErpProductService iErpProductService;

    @PostMapping
    @Operation(summary = "新增商品")
    //@PreAuthorize("@ss.hasPermi('business:erp-product:add')")
    public R<ErpProductVO> add(@RequestBody @Validated ErpProductDTO product) {
        return R.ok(iErpProductService.add(product));
    }

    @PutMapping("/{id}")
    @Operation(summary = "修改商品", parameters = {
            @Parameter(name = "id", description = "商品id", in = ParameterIn.PATH, schema = @Schema(type = "string"))
    })
    //@PreAuthorize("@ss.hasPermi('business:erp-product:edit')")
    public R<ErpProductVO> update(@PathVariable("id") String id,
                                  @RequestBody @Validated ErpProductDTO product) {
        return R.ok(iErpProductService.update(id, product));
    }

    @PutMapping("/adopt")
    @Operation(summary = "商品认领", description = "返回认领成功商品数量")
    //@PreAuthorize("@ss.hasPermi('business:erp-product:adopt')")
    public R<Integer> adopt(@RequestBody @Validated ValidateList<ProductAdoptDTO> productAdopt) {
        return R.ok(iErpProductService.adopt(productAdopt.getList()));
    }

    @PutMapping("/release")
    @Operation(summary = "商品发布", description = "返回发布成功商品数量")
    //@PreAuthorize("@ss.hasPermi('business:erp-product:release')")
    public R<String> release(@RequestBody @Validated ProductReleaseDTO productRelease) {
        return R.ok(iErpProductService.release(productRelease));
    }

    @DeleteMapping("/{ids}")
    @Operation(summary = "删除商品", parameters = {
            @Parameter(name = "ids", description = "商品id 多个商品以英文逗号分隔", in = ParameterIn.PATH)
    })
    //@PreAuthorize("@ss.hasPermi('business:erp-product:delete')")
    public R<Boolean> delete(@PathVariable("ids") List<String> ids) {
        return R.ok(iErpProductService.delete(ids));
    }

    @GetMapping("/{id}")
    @Operation(summary = "查询商品详情", parameters = {
            @Parameter(name = "id", description = "商品id", in = ParameterIn.PATH, schema = @Schema(type = "string")),
            @Parameter(name = "shouldIgnoreDelete", description = "是否被删除的商品依然可以被查询到 true:是 false:否（默认）", in = ParameterIn.QUERY, schema = @Schema(type = "boolean", allowableValues = {"true", "false"}))
    })
    //@PreAuthorize("@ss.hasPermi('business:erp-product:get')")
    public R<ErpProductVO> getDetail(@PathVariable("id") String id,
                                     @RequestParam(required = false, defaultValue = "false") Boolean shouldIgnoreDelete) {
        return R.ok(iErpProductService.getDetail(id, shouldIgnoreDelete));
    }

    @GetMapping("/translate/{id}")
    @Operation(summary = "翻译某个商品", parameters = {
            @Parameter(name = "id", description = "商品id", in = ParameterIn.PATH, schema = @Schema(type = "string")),
            @Parameter(name = "from", description = "翻译源语言 默认zh 从字典sys_language_code取值", in = ParameterIn.QUERY, schema = @Schema(type = "string", defaultValue = "zh")),
            @Parameter(name = "to", description = "翻译目标语言 默认en 从字典sys_language_code取值", in = ParameterIn.QUERY, schema = @Schema(type = "string", defaultValue = "en")),
    })
    //@PreAuthorize("@ss.hasPermi('business:erp-product:translate')")
    public R<ErpProductVO> translate(@PathVariable("id") String id,
                                     @RequestParam(required = false, defaultValue = "zh") String from,
                                     @RequestParam(required = false, defaultValue = "en") String to) {
        return R.ok(iErpProductService.translate(id, from, to));
    }

    @GetMapping("/page")
    @Operation(summary = "获取商品分页列表", parameters = {
            @Parameter(name = "name", description = "商品名称", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "adoptStatus", description = "商品认领状态 未认领：0(默认), 已认领：1", in = ParameterIn.QUERY, schema = @Schema(type = "integer", nullable = true, allowableValues = {"", "0", "1"})),
            @Parameter(name = "sellType", description = "售卖形式 单品：0 变体：1", in = ParameterIn.QUERY, schema = @Schema(type = "integer", nullable = true, allowableValues = {"", "0", "1"})),
            @Parameter(name = "status", description = "商品状态 待发布：0(默认), 发布中：1, 发布成功：2, 发布失败：3, 部分失败：4, 数据缺失：5", in = ParameterIn.QUERY, schema = @Schema(type = "integer", nullable = true, allowableValues = {"", "0", "1", "2", "3", "4", "5"})),
            @Parameter(name = "shouldIncludePrintPhoto", description = "是否需要查询刊登图 默认不需要", in = ParameterIn.QUERY, schema = @Schema(type = "boolean", allowableValues = {"true", "false"})),
            @Parameter(name = "shouldIncludeReferencePhoto", description = "是否需要查询引用图 默认不需要", in = ParameterIn.QUERY, schema = @Schema(type = "boolean", allowableValues = {"true", "false"})),
            @Parameter(name = "pageNum", description = "页码 默认：1", schema = @Schema(type = "integer", defaultValue = "1"), in = ParameterIn.QUERY),
            @Parameter(name = "pageSize", description = "每页数量 默认：10", schema = @Schema(type = "integer", defaultValue = "10"), in = ParameterIn.QUERY)
    })
    //@PreAuthorize("@ss.hasPermi('business:erp-product:page')")
    public TableDataInfo<ErpProductVO> getPage(@RequestParam(required = false) String name,
                                               @RequestParam(required = false, defaultValue = "0") Integer adoptStatus,
                                               @RequestParam(required = false) Integer sellType,
                                               @RequestParam(required = false, defaultValue = "0") Integer status,
                                               @RequestParam(required = false, defaultValue = "false") Boolean shouldIncludePrintPhoto,
                                               @RequestParam(required = false, defaultValue = "false") Boolean shouldIncludeReferencePhoto,
                                               @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                               @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        return iErpProductService.getPage(null, name, adoptStatus, sellType, status, shouldIncludePrintPhoto, shouldIncludeReferencePhoto, pageNum, pageSize);
    }

    @GetMapping("/list")
    @Operation(summary = "获取商品列表", parameters = {
            @Parameter(name = "id", description = "商品id", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "name", description = "商品名称", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "adoptStatus", description = "商品认领状态 未认领：0(默认), 已认领：1", in = ParameterIn.QUERY, schema = @Schema(type = "integer", nullable = true, allowableValues = {"0", "1"})),
            @Parameter(name = "sellType", description = "售卖形式 单品：0 变体：1", in = ParameterIn.QUERY, schema = @Schema(type = "integer", nullable = true, allowableValues = {"0", "1"})),
            @Parameter(name = "status", description = "商品状态 待发布：0(默认), 发布中：1, 发布成功：2, 发布失败：3, 部分失败：4, 数据缺失：5", in = ParameterIn.QUERY, schema = @Schema(type = "integer", nullable = true, allowableValues = {"0", "1", "2", "3", "4", "5"})),
            @Parameter(name = "shouldIncludePrintPhoto", description = "是否需要查询刊登图 默认不需要", in = ParameterIn.QUERY, schema = @Schema(type = "boolean", allowableValues = {"true", "false"})),
            @Parameter(name = "shouldIncludeReferencePhoto", description = "是否需要查询引用图 默认不需要", in = ParameterIn.QUERY, schema = @Schema(type = "boolean", allowableValues = {"true", "false"}))
    })
    //@PreAuthorize("@ss.hasPermi('business:erp-product:get')")
    public R<List<ErpProductVO>> getList(@RequestParam(required = false) String id,
                                         @RequestParam(required = false) String name,
                                         @RequestParam(required = false, defaultValue = "0") Integer adoptStatus,
                                         @RequestParam(required = false) Integer sellType,
                                         @RequestParam(required = false, defaultValue = "0") Integer status,
                                         @RequestParam(required = false, defaultValue = "false") Boolean shouldIncludePrintPhoto,
                                         @RequestParam(required = false, defaultValue = "false") Boolean shouldIncludeReferencePhoto) {
        return R.ok(iErpProductService.getList(id, name, adoptStatus, sellType, status, shouldIncludePrintPhoto, shouldIncludeReferencePhoto));
    }

}
