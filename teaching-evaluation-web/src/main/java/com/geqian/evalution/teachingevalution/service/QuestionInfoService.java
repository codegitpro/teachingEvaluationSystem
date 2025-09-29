package com.geqian.evalution.teachingevalution.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.geqian.common.common.ResponseResult;
import com.geqian.evalution.teachingevalution.entity.QuestionInfo;

import java.util.List;

/**
* @author Administrator
* @description 针对表【question_info(问卷调查问题表)】的数据库操作Service
* @createDate 2025-09-18 01:45:49
*/
public interface QuestionInfoService extends IService<QuestionInfo> {

    ResponseResult<Object> addQuestionList(List<QuestionInfo> questionInfoList);

    ResponseResult<Object> getQuestionList(Long surveyId);

    ResponseResult<Object> getQuestionDisplayData(Long surveyId);
}
