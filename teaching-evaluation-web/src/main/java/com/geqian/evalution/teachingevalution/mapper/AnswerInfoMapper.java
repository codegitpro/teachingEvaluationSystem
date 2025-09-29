package com.geqian.evalution.teachingevalution.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.geqian.evalution.teachingevalution.entity.AnswerInfo;

import java.util.List;

/**
* @author Administrator
* @description 针对表【answer_info(用户答案表)】的数据库操作Mapper
* @createDate 2025-09-11 22:05:06
* @Entity com.geqian.evalution.teachingevalution.entity.AnswerInfo
*/
public interface AnswerInfoMapper extends BaseMapper<AnswerInfo> {

    List<Long> selectSubmitSurvryId(Long studentId);
}




