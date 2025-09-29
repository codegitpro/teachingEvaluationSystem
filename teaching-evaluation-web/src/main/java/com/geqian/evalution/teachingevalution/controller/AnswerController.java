package com.geqian.evalution.teachingevalution.controller;

import com.geqian.common.common.ResponseResult;
import com.geqian.evalution.teachingevalution.entity.AnswerInfo;
import com.geqian.evalution.teachingevalution.service.AnswerInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author geqian
 * @date 17:57 2025/9/18
 */
@Api(tags = "问卷填写")
@RestController
@RequestMapping("answer")
public class AnswerController {

    @Resource
    private AnswerInfoService answerInfoService;

    @ApiOperation("提交问卷")
    @PostMapping("/submitQuestionnaire")
    public ResponseResult<Object> submitQuestionnaire(@RequestBody List<AnswerInfo> answerInfoList, @RequestHeader("token") String token) {
        return answerInfoService.submitQuestionnaire(answerInfoList,token);
    }
}
