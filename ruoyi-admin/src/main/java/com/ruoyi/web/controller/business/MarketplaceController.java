package com.ruoyi.web.controller.business;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.vo.MarketplaceVO;
import com.ruoyi.system.service.IMarketplaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author WangJiang
 * @since 2023/3/2 14:15
 */
@Tag(name = "市场(站点)接口")
@RestController
@RequestMapping("/business/marketplace")
public class MarketplaceController extends BaseController {

    @Resource
    private IMarketplaceService iMarketplaceService;

    @GetMapping("/page")
    @Operation(summary = "查询市场(站点)分页列表", parameters = {
            @Parameter(name = "market", description = "模糊匹配站点简码", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "name", description = "模糊匹配站点名称", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "region", description = "模糊匹配所属区域简码", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "regionName", description = "模糊匹配所属区域名称", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "pageNum", description = "页码 默认：1", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "1")),
            @Parameter(name = "pageSize", description = "每页数量 默认：10", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "10"))
    })
    //@PreAuthorize("@ss.hasPermi('business:store:get')")
    public TableDataInfo<MarketplaceVO> page(@RequestParam(required = false) String market,
                                             @RequestParam(required = false) String name,
                                             @RequestParam(required = false) String region,
                                             @RequestParam(required = false) String regionName,
                                             @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                             @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        return iMarketplaceService.page(market, name, region, regionName, pageNum, pageSize);
    }

    @GetMapping("/list")
    @Operation(summary = "查询市场(站点)列表", parameters = {
            @Parameter(name = "market", description = "模糊匹配站点简码", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "name", description = "模糊匹配站点名称", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "region", description = "模糊匹配所属区域简码", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "regionName", description = "模糊匹配所属区域名称", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
    })
    //@PreAuthorize("@ss.hasPermi('business:store:get')")
    public R<List<MarketplaceVO>> list(@RequestParam(required = false) String market,
                                       @RequestParam(required = false) String name,
                                       @RequestParam(required = false) String region,
                                       @RequestParam(required = false) String regionName) {
        return R.ok(iMarketplaceService.list(market, name, region, regionName));
    }

}
