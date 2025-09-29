package com.geqian.evalution.teachingevalution.controller;

import com.geqian.common.common.PageRequest;
import com.geqian.common.common.ResponseResult;
import com.geqian.evalution.teachingevalution.entity.EvaluationInfo;
import com.geqian.evalution.teachingevalution.service.EvaluationInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author geqian
 * @date 12:33 2025/9/19
 */
@Api(tags = "评教管理")
@RestController
@RequestMapping("/evaluation")
public class EvaluationController {


    @Resource
    private EvaluationInfoService evaluationInfoService;


    @ApiOperation("开启评教功能")
    @PostMapping("/openEvaluationInfo")
    public ResponseResult<Object> openEvaluationInfo(@RequestBody EvaluationInfo evaluationInfo){
        return evaluationInfoService.openEvaluationInfo(evaluationInfo);
    }


    @ApiOperation("查询评教列表")
    @GetMapping("/getEvaluationInfoList")
    public ResponseResult<Object> getEvaluationInfoList(PageRequest pageRequest,@RequestHeader("token")String token){
        return evaluationInfoService.getEvaluationInfoList(pageRequest,token);
    }


    @ApiOperation("查询设置评教时间返回")
    @GetMapping("/getEvaluationTime")
    public ResponseResult<Object> getEvaluationTime(){
        return evaluationInfoService.getEvaluationTime();
    }
}
