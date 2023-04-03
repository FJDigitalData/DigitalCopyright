package com.ruoyi.web.controller.business;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.dto.CopyrightCertificateDTO;
import com.ruoyi.system.domain.dto.OpusDTO;
import com.ruoyi.system.domain.vo.FolderOpusTreeItemVO;
import com.ruoyi.system.domain.vo.FolderOpusVO;
import com.ruoyi.system.domain.vo.OpusVO;
import com.ruoyi.system.service.IOpusService;
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
 * @since 2023/1/6 13:57
 */
@Tag(name = "作品接口")
@RestController
@RequestMapping("/business/opus")
public class OpusController extends BaseController {

    @Resource
    private IOpusService iOpusService;

    @PostMapping
    @Operation(summary = "新增作品")
    @PreAuthorize("@ss.hasPermi('business:opus:add')")
    public R<OpusVO> add(@RequestBody @Validated OpusDTO opus) {
        return R.ok(iOpusService.add(opus));
    }

    @PutMapping("/{id}")
    @Operation(summary = "修改作品", parameters = {
            @Parameter(name = "id", description = "作品id", in = ParameterIn.PATH, required = true, schema = @Schema(type = "string"))
    })
    @PreAuthorize("@ss.hasPermi('business:opus:edit')")
    public R<OpusVO> update(@PathVariable("id") String id,
                            @RequestBody @Validated OpusDTO opus) {
        return R.ok(iOpusService.update(id, opus));
    }

    @PutMapping("/{id}/name")
    @Operation(summary = "修改作品名称", parameters = {
            @Parameter(name = "id", description = "作品id", in = ParameterIn.PATH, schema = @Schema(type = "string"), required = true),
            @Parameter(name = "newName", description = "作品新名词", in = ParameterIn.QUERY, schema = @Schema(type = "string"), required = true)
    })
    @PreAuthorize("@ss.hasPermi('business:opus:edit')")
    public R<Boolean> updateName(@PathVariable("id") String id,
                                 @RequestParam String newName) {
        return R.ok(iOpusService.updateName(id, newName));
    }

    @PutMapping("/{id}/parent")
    @Operation(summary = "修改作品所在目录", parameters = {
            @Parameter(name = "id", description = "作品id", in = ParameterIn.PATH, schema = @Schema(type = "string"), required = true),
            @Parameter(name = "newParent", description = "作品所在目录id", in = ParameterIn.QUERY, schema = @Schema(type = "long"), required = true)
    })
    @PreAuthorize("@ss.hasPermi('business:opus:edit')")
    public R<Boolean> updateParent(@PathVariable("id") String id,
                                   @RequestParam Long newParent) {
        return R.ok(iOpusService.updateParent(id, newParent));
    }

    @PutMapping("/{id}/up-down/{type}")
    @Operation(summary = "修改作品上下架状态", parameters = {
            @Parameter(name = "id", description = "作品id", in = ParameterIn.PATH, schema = @Schema(type = "string"), required = true),
            @Parameter(name = "type", description = "操作类型 1: 上架 2或其他非空：下架", in = ParameterIn.PATH, schema = @Schema(type = "integer"), required = true)
    })
    @PreAuthorize("@ss.hasPermi('business:opus:edit-up-down')")
    public R<Boolean> upDown(@PathVariable("id") String id,
                             @PathVariable("type") Integer type) {
        return R.ok(iOpusService.upDown(id, type));
    }

    @PutMapping("/{id}/freeze/{type}")
    @Operation(summary = "修改作品冻结状态", parameters = {
            @Parameter(name = "id", description = "作品id", in = ParameterIn.PATH, schema = @Schema(type = "string"), required = true),
            @Parameter(name = "type", description = "操作类型 0: 解冻 1或其他非空：冻结", in = ParameterIn.PATH, schema = @Schema(type = "integer"), required = true)
    })
    @PreAuthorize("@ss.hasPermi('business:opus:edit-freeze')")
    public R<Boolean> freeze(@PathVariable("id") String id,
                             @PathVariable("type") Integer type) {
        return R.ok(iOpusService.freeze(id, type));
    }

    @PutMapping("/{id}/authorization/{type}")
    @Operation(summary = "修改作品授权状态", description = "只能用于电商售卖者", parameters = {

            @Parameter(name = "id", description = "作品id", in = ParameterIn.PATH, schema = @Schema(type = "string"), required = true),
            @Parameter(name = "type", description = "操作类型 1: 发起授权 2或其他非空：取消授权", in = ParameterIn.PATH, schema = @Schema(type = "integer"), required = true)
    })
    @PreAuthorize("@ss.hasPermi('business:opus:edit-authorization')")
    public R<Boolean> editAuthorization(@PathVariable("id") String id,
                                        @PathVariable("type") Integer type) {
        return R.ok(iOpusService.editAuthorization(id, type));
    }

    @Operation(summary = "发起版权存证", parameters = {
            @Parameter(name = "id", description = "作品id", in = ParameterIn.PATH, schema = @Schema(type = "string"), required = true)
    })
    @PutMapping("/{id}/call-copyright-certificate")
    @PreAuthorize("@ss.hasPermi('business:opus:call-copyright-certificate')")
    public R<Boolean> callCopyrightCertificate(@PathVariable("id") String id) {
        return R.ok(iOpusService.callCopyrightCertificate(id));
    }

    @Operation(summary = "审核版权存证", parameters = {
            @Parameter(name = "id", description = "作品id", in = ParameterIn.PATH, schema = @Schema(type = "string"), required = true)
    })
    @PutMapping("/{id}/audit-copyright-certificate/audit")
    @PreAuthorize("@ss.hasPermi('business:opus:audit-copyright-certificate')")
    public R<Boolean> auditCopyrightCertificate(@PathVariable("id") String id,
                                                @RequestBody @Validated CopyrightCertificateDTO copyrightCertificate) {
        return R.ok(iOpusService.auditCopyrightCertificate(id, copyrightCertificate));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除作品", parameters = {
            @Parameter(name = "id", description = "作品id", in = ParameterIn.PATH, schema = @Schema(type = "string"), required = true)
    })
    @PreAuthorize("@ss.hasPermi('business:opus:delete')")
    public R<Boolean> delete(@PathVariable("id") String id) {
        return R.ok(iOpusService.delete(id));
    }

    @GetMapping("/detail/{id}")
    @Operation(summary = "查询作品详情", parameters = {
            @Parameter(name = "id", description = "作品id", in = ParameterIn.PATH, schema = @Schema(type = "string"), required = true),
            @Parameter(name = "shouldAddHot", description = "是否增加热度 true:增加 false:不增加（默认）", in = ParameterIn.QUERY, schema = @Schema(type = "boolean", allowableValues = {"true", "false"})),
            @Parameter(name = "shouldIgnoreDelete", description = "是否被删除的作品依然可以被查询到 true:是 false:否（默认）", in = ParameterIn.QUERY, schema = @Schema(type = "boolean", allowableValues = {"true", "false"})),
            @Parameter(name = "shouldSearchAuthorization", description = "是否需要查询授权列表 true:是 false:否（默认）", in = ParameterIn.QUERY, schema = @Schema(type = "boolean", allowableValues = {"true", "false"}))
    })
    public R<OpusVO> getDetail(@PathVariable("id") String id,
                               @RequestParam(required = false, defaultValue = "false") Boolean shouldAddHot,
                               @RequestParam(required = false, defaultValue = "false") Boolean shouldIgnoreDelete,
                               @RequestParam(required = false, defaultValue = "false") Boolean shouldSearchAuthorization) {
        return R.ok(iOpusService.getDetail(id, shouldAddHot, shouldIgnoreDelete, shouldSearchAuthorization));
    }

    @GetMapping("/public-page")
    @Operation(summary = "查询作品分页列表-免登录", description = "查询结果不包含目录, 不用登录即可调用", parameters = {
            @Parameter(name = "name", description = "名称 模糊匹配作品名称", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "style", description = "模糊匹配风格", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "tag", description = "模糊匹配标签", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "key", description = "搜索关键字 模糊匹配作品名称、风格、标签", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "pageNum", description = "页码 默认：1", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "1")),
            @Parameter(name = "pageSize", description = "每页数量 默认：10", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "10"))
    })
    public TableDataInfo<OpusVO> getPublicPage(@RequestParam(required = false) String name,
                                               @RequestParam(required = false) String style,
                                               @RequestParam(required = false) String tag,
                                               @RequestParam(required = false) String key,
                                               @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                               @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        return iOpusService.getPublicPage(name, style, tag, key, pageNum, pageSize);
    }

    @GetMapping("/hot-key")
    @Operation(summary = "查询搜索热词-免登录", parameters = {
            @Parameter(name = "size", description = "获取热词数量 默认：10", in = ParameterIn.QUERY, schema = @Schema(type = "integer")),
            @Parameter(name = "key", description = "热词 模糊匹配热词", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
    })
    public R<List<String>> getHotKey(@RequestParam(required = false, defaultValue = "10") Integer size,
                                     @RequestParam(required = false) String key) {
        return R.ok(iOpusService.getHotKey(size, key));
    }

    @GetMapping("/page")
    @PreAuthorize("@ss.hasPermi('business:opus:list')")
    @Operation(summary = "查询作品分页列表-需要登录", description = "查询结果不包含目录 创作方:只能看到自己的文件 电商售卖方：只能看到被授权的 其他人员：全部", parameters = {
            @Parameter(name = "style", description = "风格 取字典sys_opus_style对应的字典值中的dictCode", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "key", description = "搜索关键字 模糊匹配作品名称、标签", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "isOpen", description = "是否上架(对电商售卖方无效) 0:未上架 1:已上架", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "isFreeze", description = "是否冻结(对电商售卖方无效) 0:未冻结 1:已冻结", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "applyStatus", description = "版权申请状态(对电商售卖方无效) 0：未申请 1：待审核 2：驳回 3：审核通过 4: 待审核 + 驳回 + 审核通过", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "shouldContainFileUrl", description = "是否包含源文件链接 默认false", in = ParameterIn.QUERY, schema = @Schema(type = "boolean", allowableValues = {"true", "false"})),
            @Parameter(name = "shouldContainMaterialFileUrl", description = "是否包含素材文件链接 默认false", in = ParameterIn.QUERY, schema = @Schema(type = "boolean", allowableValues = {"true", "false"})),
            @Parameter(name = "shouldContainTag", description = "是否包含标签数据 默认false", in = ParameterIn.QUERY, schema = @Schema(type = "boolean", allowableValues = {"true", "false"})),
            @Parameter(name = "uploadTime", description = "上传日期 格式:yyyy-MM-dd", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "pageNum", description = "页码 默认：1", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "1")),
            @Parameter(name = "pageSize", description = "每页数量 默认：10", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "10")),
            @Parameter(name = "sortType", description = "排序类型 1:创建时间(默认) 2:授权时间(只对电商售卖方有效) 3:版权申请时间 4:版权申请状态(0：未申请 1：待审核 2：驳回 3：审核通过) 5:热度", in = ParameterIn.QUERY, schema = @Schema(type = "integer")),
            @Parameter(name = "orderType", description = "排序方式 1: 倒序 其他: 正序(默认)", in = ParameterIn.QUERY, schema = @Schema(type = "integer"))
    })
    public TableDataInfo<OpusVO> getPage(@RequestParam(required = false) String style,
                                         @RequestParam(required = false) String key,
                                         @RequestParam(required = false) String isOpen,
                                         @RequestParam(required = false) String isFreeze,
                                         @RequestParam(required = false) String applyStatus,
                                         @RequestParam(required = false) String uploadTime,
                                         @RequestParam(required = false, defaultValue = "false") Boolean shouldContainFileUrl,
                                         @RequestParam(required = false, defaultValue = "false") Boolean shouldContainMaterialFileUrl,
                                         @RequestParam(required = false, defaultValue = "false") Boolean shouldContainTag,
                                         @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                         @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                         @RequestParam(required = false, defaultValue = "1") Integer sortType,
                                         @RequestParam(required = false, defaultValue = "0") Integer orderType) {
        return iOpusService.getPage(style, key, isOpen, isFreeze, applyStatus, uploadTime, shouldContainFileUrl, shouldContainMaterialFileUrl, shouldContainTag, pageNum, pageSize, sortType, orderType);
    }

    @GetMapping("/list")
    @Operation(summary = "查询指定目录下的作品和目录", description = "只查询自己创建的，包含目录", parameters = {
            @Parameter(name = "name", description = "名称 模糊匹配作品名称和目录名称", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "parentId", description = "目录id 0为根目录 默认为0", in = ParameterIn.QUERY, schema = @Schema(type = "long", example = "0"))
    })
    @PreAuthorize("@ss.hasPermi('business:opus:list')")
    public R<FolderOpusVO> getList(@RequestParam(required = false) String name,
                                   @RequestParam(required = false, defaultValue = Constants.TOP_DIC_ID_STR) Long parentId) {
        return R.ok(iOpusService.getList(name, parentId));
    }

    @GetMapping("/tree")
    @Operation(summary = "查询作品树", description = "只查询自己创建的，包含目录", parameters = {
            @Parameter(name = "name", description = "名称 模糊匹配作品名称和目录名称", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "parentId", description = "目录id 0为根目录 默认为0", in = ParameterIn.QUERY, schema = @Schema(type = "long", example = "0"))
    })
    @PreAuthorize("@ss.hasPermi('business:opus:list')")
    public R<List<FolderOpusTreeItemVO>> getTree(@RequestParam(required = false) String name,
                                                 @RequestParam(required = false, defaultValue = Constants.TOP_DIC_ID_STR) Long parentId) {
        return R.ok(iOpusService.getTree(name, parentId));
    }

}
