package com.ruoyi.web.controller.business;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.dto.FileInfoDTO;
import com.ruoyi.system.domain.vo.FileInfoVO;
import com.ruoyi.system.domain.vo.FolderOpusTreeItemVO;
import com.ruoyi.system.service.IFileInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author WangJiang
 * @since 2023/1/6 13:57
 */
@Tag(name = "作品相关文件目录接口")
@RestController
@RequestMapping("/business/file-info")
public class FileInfoController extends BaseController {

    @Resource
    private IFileInfoService iFileInfoService;

    @PostMapping
    @Operation(summary = "新增目录")
    @PreAuthorize("@ss.hasPermi('business:file-info:add-folder')")
    public R<FileInfoVO> addFolder(@RequestBody @Validated FileInfoDTO fileInfo) {
        return R.ok(iFileInfoService.addFolder(fileInfo));
    }

    @PutMapping("/{id}")
    @Operation(summary = "修改目录", parameters = {
            @Parameter(name = "id", description = "要修改的目录id", in = ParameterIn.PATH, schema = @Schema(type = "long"))
    })
    @PreAuthorize("@ss.hasPermi('business:file-info:edit-folder')")
    public R<FileInfoVO> updateFolder(@PathVariable("id") Long id,
                                      @RequestBody @Validated FileInfoDTO fileInfo) {
        return R.ok(iFileInfoService.updateFolder(id, fileInfo));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除文件", description = "会级联删除子孙目录，以及他们中的作品", parameters = {
            @Parameter(name = "id", description = "文件id", in = ParameterIn.PATH, schema = @Schema(type = "long"))
    })
    @PreAuthorize("@ss.hasPermi('business:file-info:delete')")
    public R<Boolean> delete(@PathVariable("id") Long id) {
        return R.ok(iFileInfoService.delete(id));
    }

    @GetMapping("/{id}")
    @Operation(summary = "查询文件详情", parameters = {
            @Parameter(name = "id", description = "文件id", in = ParameterIn.PATH, schema = @Schema(type = "long")),
            @Parameter(name = "shouldIgnoreDelete", description = "是否被删除的作品依然可以被查询到 true:是 false:否（默认）", in = ParameterIn.QUERY, schema = @Schema(type = "boolean", allowableValues = {"true", "false"}))
    })
    public R<FileInfoVO> getDetail(@PathVariable("id") Long id,
                                   @RequestParam(required = false, defaultValue = "false") Boolean shouldIgnoreDelete) {
        return R.ok(iFileInfoService.getDetail(id, shouldIgnoreDelete));
    }

    @PostMapping("/upload")
    @Operation(summary = "文件上传", description = "只能用于作品相关的文件上传", parameters = {
            @Parameter(name = "file", description = "文件", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "file")),
            @Parameter(name = "parentId", description = "文件所在目录id，0为根目录", in = ParameterIn.QUERY, schema = @Schema(type = "string"), required = true)
    })
    public R<FileInfoVO> upload(@RequestPart MultipartFile file,
                                @RequestPart String parentId) {
        return R.ok(iFileInfoService.upload(file, parentId));
    }

    @GetMapping("/folder-tree")
    @Operation(summary = "获取目录树", description = "只查询自己创建的, 不包含作品", parameters = {
            @Parameter(name = "name", description = "名称 模糊匹配目录名称", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "parentId", description = "文件所在目录id", in = ParameterIn.QUERY, schema = @Schema(type = "long")),

    })
    @PreAuthorize("@ss.hasPermi('business:file-info:folder-tree')")
    public R<List<FolderOpusTreeItemVO>> getFolderTree(@RequestParam(required = false) String name,
                                                       @RequestParam(required = false, defaultValue = Constants.TOP_DIC_ID_STR) Long parentId) {
        return R.ok(iFileInfoService.getFolderTree(name, parentId));
    }

}
