package com.geqian.evalution.teachingevalution.controller;

import com.geqian.common.common.ResponseResult;
import com.geqian.evalution.teachingevalution.entity.QuestionInfo;
import com.geqian.evalution.teachingevalution.service.QuestionInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author geqian
 * @date 22:12 2025/9/11
 */

@Api(tags = "问卷问题管理")
@RestController
@RequestMapping("/question")
public class QuestionInfoController {

    @Resource
    private QuestionInfoService questionInfoService;

    @ApiOperation("添加问卷问题")
    @PostMapping("/addQuestionList")
    public ResponseResult<Object> addQuestionList(@RequestBody List<QuestionInfo> questionInfoList) {
        return questionInfoService.addQuestionList(questionInfoList);
    }

    @ApiOperation("查询问卷问题")
    @GetMapping("/getQuestionList")
    public ResponseResult<Object> getQuestionList(@RequestParam("surveyId") Long surveyId) {
        return questionInfoService.getQuestionList(surveyId);
    }


    @ApiOperation("填写问卷时展示数据")
    @GetMapping("/getQuestionDisplayData")
    public ResponseResult<Object> getQuestionDisplayData(@RequestParam("surveyId") Long surveyId) {
        return questionInfoService.getQuestionDisplayData(surveyId);
    }


    //@ApiOperation("删除问卷问题")
    //@GetMapping("/deleteQuestionList")
    //public ResponseResult<Object> deleteQuestion(@RequestParam("questionId") String questionId){
    //    return questionInfoService.deleteQuestion(questionId);
    //}
}
