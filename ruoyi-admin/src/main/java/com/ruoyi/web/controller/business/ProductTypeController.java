package com.ruoyi.web.controller.business;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.vo.ProductTypeVO;
import com.ruoyi.system.service.IProductTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author WangJiang
 * @since 2023/3/21 16:11
 */
@Tag(name = "亚马逊商品类型接口")
@RestController
@RequestMapping("/business/amazon-product-type")
public class ProductTypeController extends BaseController {

    @Resource
    private IProductTypeService iProductTypeService;

    @GetMapping("/tree")
    @Operation(summary = "查询亚马逊商品类型树", parameters = {
            @Parameter(name = "name", description = "名称 模糊匹配分类名称", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "parentId", description = "父级id 0为顶级 默认为0", in = ParameterIn.QUERY, schema = @Schema(type = "long", example = "0"))
    })
    //@PreAuthorize("@ss.hasPermi('business:amazon-product-type:get')")
    public R<List<ProductTypeVO>> getTree(@RequestParam(required = false) String name,
                                          @RequestParam(required = false, defaultValue = Constants.TOP_DIC_ID_STR) Long parentId) {
        return R.ok(iProductTypeService.getTree(name, parentId));
    }

    @GetMapping("/child")
    @Operation(summary = "查询亚马逊商品类型子类型", parameters = {
            @Parameter(name = "name", description = "名称 模糊匹配分类名称", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "parentId", description = "父级id 0为顶级 默认为0", in = ParameterIn.QUERY, schema = @Schema(type = "long", example = "0"))
    })
    //@PreAuthorize("@ss.hasPermi('business:amazon-product-type:get')")
    public R<List<ProductTypeVO>> getChild(@RequestParam(required = false) String name,
                                          @RequestParam(required = false, defaultValue = Constants.TOP_DIC_ID_STR) Long parentId) {
        return R.ok(iProductTypeService.getChild(name, parentId, false));
    }

}
