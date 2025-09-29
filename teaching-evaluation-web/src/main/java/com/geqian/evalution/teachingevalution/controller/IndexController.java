package com.geqian.evalution.teachingevalution.controller;

import com.geqian.common.common.PageRequest;
import com.geqian.common.common.PageResult;
import com.geqian.common.common.ResponseResult;
import com.geqian.evalution.teachingevalution.entity.IndexInfo;
import com.geqian.evalution.teachingevalution.service.IndexInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author geqian
 * @date 11:36 2025/9/12
 */
@Api(tags = "指标管理")
@RestController
@RequestMapping("/index")
public class IndexController {

    @Resource
    private IndexInfoService indexInfoService;


    @ApiOperation("查询指标列表")
    @GetMapping("/getIndexList")
    public ResponseResult<PageResult<IndexInfo>> getIndexList(PageRequest pageRequest) {
        return indexInfoService.getIndexList(pageRequest);
    }

    @ApiOperation("查询全部指标列表")
    @GetMapping("/getIndexAllList")
    public ResponseResult<List<IndexInfo>> getIndexAllList() {
        return indexInfoService.getIndexAllList();
    }


    @ApiOperation("更新指标信息")
    @PostMapping("/updateIndex")
    public ResponseResult<Object> updateIndex(@RequestBody IndexInfo indexInfo) {
        return indexInfoService.updateIndex(indexInfo);
    }


    @ApiOperation("新增指标")
    @PostMapping("/addIndex")
    public ResponseResult<Object> addStudent(@RequestBody IndexInfo indexInfo) {
        return indexInfoService.addIndex(indexInfo);
    }


    @ApiOperation("删除学生")
    @GetMapping("/deleteIndex")
    public ResponseResult<Object> deleteIndex(@RequestParam("indexId") String indexId) {
        return indexInfoService.deleteIndex(indexId);
    }
}
