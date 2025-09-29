package com.geqian.evalution.teachingevalution.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.geqian.common.common.PageRequest;
import com.geqian.common.common.ResponseResult;
import com.geqian.evalution.teachingevalution.entity.Course;
import com.geqian.evalution.teachingevalution.entity.EvaluationStudent;

/**
* @author Administrator
* @description 针对表【evaluation_student(学生评分表)】的数据库操作Service
* @createDate 2025-09-19 12:27:36
*/
public interface EvaluationStudentService extends IService<EvaluationStudent> {

    //ResponseResult<Object> openEvaluationStudent(EvaluationStudent evaluationStudent);

    ResponseResult<Object> getEvaluationStudentList(PageRequest pageRequest,String token);

    ResponseResult<Object> updateEvaluationStudent(EvaluationStudent evaluationStudent);

}
