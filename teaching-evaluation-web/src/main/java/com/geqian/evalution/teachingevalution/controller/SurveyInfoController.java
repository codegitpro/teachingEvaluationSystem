package com.geqian.evalution.teachingevalution.controller;

import com.geqian.common.common.PageRequest;
import com.geqian.common.common.ResponseResult;
import com.geqian.evalution.teachingevalution.common.dto.SurveyChartDto;
import com.geqian.evalution.teachingevalution.entity.SurveyInfo;
import com.geqian.evalution.teachingevalution.service.SurveyInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author geqian
 * @date 22:05 2025/9/11
 */

@Api(tags = "问卷信息管理")
@RestController
@RequestMapping("/questionnaire")
public class SurveyInfoController {

    @Resource
    private SurveyInfoService surveyInfoService;

    @ApiOperation("新增问卷调查")
    @PostMapping("/addQuestionnaire")
    public ResponseResult<Object> addQuestionnaire(@RequestBody SurveyInfo surveyInfo, @RequestHeader("token") String token) {
        return surveyInfoService.addQuestionnaire(surveyInfo, token);
    }

    @ApiOperation("修改问卷调查")
    @PostMapping("/updateQuestionnaire")
    public ResponseResult<Object> updateQuestionnaire(@RequestBody SurveyInfo surveyInfo, @RequestHeader("token") String token) {
        return surveyInfoService.updateQuestionnaire(surveyInfo, token);
    }

    @ApiOperation("批量更改问卷状态")
    @PostMapping("/operateQuestionnaire")
    public ResponseResult<Object> operateQuestionnaire(@RequestBody List<SurveyInfo> surveyInfoList, @RequestHeader("token") String token) {
        return surveyInfoService.operateQuestionnaire(surveyInfoList, token);
    }


    @ApiOperation("删除问卷调查")
    @GetMapping("/deleteQuestionnaire")
    public ResponseResult<Object> deleteQuestionnaire(@RequestParam("surveyId") String surveyId) {
        return surveyInfoService.deleteQuestionnaire(surveyId);
    }


    @ApiOperation("分页查询问卷调查")
    @GetMapping("/pageSearchQuestionnaire")
    public ResponseResult<Object> pageSearchQuestionnaire(PageRequest pageRequest) {
        return surveyInfoService.pageSearchQuestionnaire(pageRequest);
    }


    @ApiOperation("分页查询进行中的问卷调查")
    @GetMapping("/getWriteQuestionnaire")
    public ResponseResult<Object> getWriteQuestionnaire(PageRequest pageRequest, @RequestHeader("token") String token) {
        return surveyInfoService.getWriteQuestionnaire(pageRequest, token);
    }


    @ApiOperation("查询问卷图表数据")
    @GetMapping("/getSurveyChartData")
    public ResponseResult<Object> getSurveyChartData(SurveyChartDto surveyChartDto) {
        return surveyInfoService.getSurveyChartData(surveyChartDto);
    }


    @ApiOperation("查询所有问卷列表")
    @GetMapping("/getSurveyAllList")
    public ResponseResult<Object> getSurveyAllList() {
        return surveyInfoService.getSurveyAllList();
    }


    @ApiOperation("查询某个问卷题目列表")
    @GetMapping("/getSurveyQuestionList")
    public ResponseResult<Object> getSurveyQuestionList(@RequestParam("surveyId") Long surveyId) {
        return surveyInfoService.getSurveyQuestionList(surveyId);
    }


    @ApiOperation("导出问卷数据")
    @GetMapping("/exportSurveyData")
    public void exportSurveyData(@RequestParam("surveyId") Long surveyId, HttpServletResponse response) {
        surveyInfoService.exportSurveyData(surveyId,response);
    }


}
