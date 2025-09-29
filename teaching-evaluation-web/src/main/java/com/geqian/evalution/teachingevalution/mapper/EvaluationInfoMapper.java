package com.geqian.evalution.teachingevalution.mapper;

import com.geqian.evalution.teachingevalution.entity.EvaluationInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author Administrator
* @description 针对表【evaluation_info(评分表)】的数据库操作Mapper
* @createDate 2025-09-19 12:27:36
* @Entity com.geqian.evalution.teachingevalution.entity.EvaluationInfo
*/
public interface EvaluationInfoMapper extends BaseMapper<EvaluationInfo> {

    int insertIfNotExist(EvaluationInfo evaluationInfo);

    EvaluationInfo selectOne();
}




