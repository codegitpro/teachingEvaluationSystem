package com.geqian.evalution.teachingevalution.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.geqian.evalution.teachingevalution.common.dto.EvaluationStudentDto;
import com.geqian.evalution.teachingevalution.common.vo.EvaluationStudentVo;
import com.geqian.evalution.teachingevalution.common.vo.StudentCourseVo;
import com.geqian.evalution.teachingevalution.entity.EvaluationInfo;
import com.geqian.evalution.teachingevalution.entity.EvaluationStudent;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author Administrator
* @description 针对表【evaluation_info(评分表)】的数据库操作Mapper
* @createDate 2025-09-19 12:27:36
* @Entity com.geqian.evalution.teachingevalution.entity.EvaluationInfo
*/
public interface EvaluationStudentMapper extends BaseMapper<EvaluationStudent> {

    int insertIfNotExist(EvaluationStudent evaluationStudent);

    EvaluationStudent selectOne();

    List<EvaluationStudentVo> selectEvaluationStudentList(EvaluationStudentDto req);

}




