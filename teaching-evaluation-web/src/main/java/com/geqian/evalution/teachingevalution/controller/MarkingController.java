package com.geqian.evalution.teachingevalution.controller;

import com.geqian.common.common.PageRequest;
import com.geqian.common.common.PageResult;
import com.geqian.common.common.ResponseResult;
import com.geqian.evalution.teachingevalution.common.dto.MarkSubmitDTO;
import com.geqian.evalution.teachingevalution.entity.MarkInfo;
import com.geqian.evalution.teachingevalution.service.MarkInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 用户登录管理
 *
 * @author geqian
 */
@RestController
@RequestMapping("/marking")
@Api(tags = "评分管理")
public class MarkingController {

    @Resource
    private MarkInfoService markInfoService;

    @PreAuthorize("hasAuthority('system:admin')")
    @ApiOperation("分页查询")
    @GetMapping("/pageSearch")
    public ResponseResult<PageResult<MarkInfo>> pageSearch(PageRequest pageRequest, @RequestHeader("token") String token) {
        return markInfoService.pageSearch(pageRequest, token);
    }


    @ApiOperation("提交评分和评语")
    @PostMapping("/submitMarking")
    public ResponseResult<Object> submitMarking(@RequestBody MarkSubmitDTO markSubmitDTO) {
        return markInfoService.submitMarking(markSubmitDTO);
    }


    @ApiOperation("根据教师名称、课程、指标查询图表数据")
    @GetMapping("/getMarkChartData")
    public ResponseResult<Object> getMarkChartData(@RequestParam("teacherId") Long teacherId, @RequestParam("indexId") Long indexId,@RequestParam("courseId") Long courseId) {
        return markInfoService.getMarkChartData(teacherId, indexId,courseId);
    }


    @ApiOperation("查询教师指标综合数据")
    @GetMapping("/getIndexAvgScore")
    public ResponseResult<Object> getIndexAvgScore(@RequestParam("teacherId") Long teacherId,@RequestParam("courseId") Long courseId) {
        return markInfoService.getIndexAvgScore(teacherId,courseId);
    }

    @ApiOperation("导出教师评教数据")
    @GetMapping("/exportMarkData")
    public void exportMarkData(@RequestParam("teacherId") Long teacherId, HttpServletResponse response) {
        markInfoService.exportMarkData(teacherId,response);
    }

    @GetMapping("/getTeacherMarkDetail")
    @ApiOperation("获取教师评教详情")
    public ResponseResult<Object> getTeacherMarkDetail(
            @RequestParam Long teacherId,
            @RequestParam(required = false) Long courseId) {
        return markInfoService.getTeacherMarkDetail(teacherId, courseId);
    }
}