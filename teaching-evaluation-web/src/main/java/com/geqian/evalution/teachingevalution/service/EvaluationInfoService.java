package com.geqian.evalution.teachingevalution.service;

import com.geqian.common.common.PageRequest;
import com.geqian.common.common.ResponseResult;
import com.geqian.evalution.teachingevalution.entity.EvaluationInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Administrator
* @description 针对表【evaluation_info(评分表)】的数据库操作Service
* @createDate 2025-09-19 12:27:36
*/
public interface EvaluationInfoService extends IService<EvaluationInfo> {

    ResponseResult<Object> openEvaluationInfo(EvaluationInfo evaluationInfo);

    ResponseResult<Object> getEvaluationInfoList(PageRequest pageRequest,String token);

    ResponseResult<Object> getEvaluationTime();
}
