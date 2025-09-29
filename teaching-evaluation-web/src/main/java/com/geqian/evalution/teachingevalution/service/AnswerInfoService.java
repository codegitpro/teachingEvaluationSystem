package com.geqian.evalution.teachingevalution.service;

import com.geqian.common.common.ResponseResult;
import com.geqian.evalution.teachingevalution.entity.AnswerInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Administrator
* @description 针对表【answer_info(用户答案表)】的数据库操作Service
* @createDate 2025-09-11 22:05:06
*/
public interface AnswerInfoService extends IService<AnswerInfo> {

    ResponseResult<Object> submitQuestionnaire(List<AnswerInfo> answerInfoList, String token);
}
