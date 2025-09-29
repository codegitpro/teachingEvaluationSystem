package com.geqian.evalution.teachingevalution.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.geqian.common.common.PageRequest;
import com.geqian.common.common.PageResult;
import com.geqian.common.common.ResponseResult;
import com.geqian.common.utils.JwtUtils;
import com.geqian.common.utils.LocalDateTimeUtils;
import com.geqian.evalution.teachingevalution.common.dto.EvaluationStudentDto;
import com.geqian.evalution.teachingevalution.common.vo.EvaluationStudentVo;
import com.geqian.evalution.teachingevalution.entity.Course;
import com.geqian.evalution.teachingevalution.entity.CourseStudent;
import com.geqian.evalution.teachingevalution.entity.CourseTeacher;
import com.geqian.evalution.teachingevalution.entity.EvaluationInfo;
import com.geqian.evalution.teachingevalution.entity.EvaluationStudent;
import com.geqian.evalution.teachingevalution.entity.Student;
import com.geqian.evalution.teachingevalution.entity.Teacher;
import com.geqian.evalution.teachingevalution.mapper.CourseTeacherMapper;
import com.geqian.evalution.teachingevalution.mapper.EvaluationStudentMapper;
import com.geqian.evalution.teachingevalution.mapper.StudentMapper;
import com.geqian.evalution.teachingevalution.mapper.TeacherMapper;
import com.geqian.evalution.teachingevalution.service.CourseService;
import com.geqian.evalution.teachingevalution.service.CourseStudentService;
import com.geqian.evalution.teachingevalution.service.EvaluationStudentService;
import com.geqian.evalution.teachingevalution.service.TeacherService;
import com.geqian.evalution.teachingevalution.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【evaluation_student(学生评分表)】的数据库操作Service实现
* @createDate 2025-09-19 12:27:36
*/
@Service
public class EvaluationStudentServiceImpl extends ServiceImpl<EvaluationStudentMapper, EvaluationStudent>
    implements EvaluationStudentService{

    @Resource
    private CourseStudentService courseStudentService;

    @Resource
    private CourseTeacherMapper courseTeacherMapper;


    @Resource
    private TeacherService teacherService;


    @Resource
    private CourseService courseService;

    @Autowired
    private EvaluationStudentMapper evaluationStudentMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private StudentMapper studentMapper;


    @Override
    public ResponseResult<Object> getEvaluationStudentList(PageRequest pageRequest,String token) {
        String id = JwtUtils.parseToken(token);
        EvaluationStudentDto evaluationStudentDto = new EvaluationStudentDto();
        evaluationStudentDto.setCourseName(pageRequest.getKey());
        Teacher teacher = teacherMapper.selectById(id);
        if(teacher != null){
            evaluationStudentDto.setTeacherId(teacher.getThId());
        }else {
            Student student = studentMapper.selectById(id);
            if (student != null){
                evaluationStudentDto.setStudentId(student.getStdId());
            }
        }
        PageHelper.startPage(pageRequest.getPageNum(),pageRequest.getPageSize());
        List<EvaluationStudentVo> evaluationStudentList = evaluationStudentMapper.selectEvaluationStudentList(evaluationStudentDto);
        PageInfo<EvaluationStudentVo> pageStudent = new PageInfo<>(evaluationStudentList);
        PageResult<EvaluationStudentVo> pageResult = PageUtils.getPageResult(pageStudent);
        for (EvaluationStudentVo evaluationStudentVo : pageResult.getDataList()) {
            LambdaQueryWrapper<EvaluationStudent> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(EvaluationStudent::getStudentId,evaluationStudentVo.getStudentId())
                    .eq(EvaluationStudent::getCourseId,evaluationStudentVo.getCourseId())
                    .eq(EvaluationStudent::getTeacherId,evaluationStudentVo.getTeacherId());
            EvaluationStudent evaluationStudent = evaluationStudentMapper.selectOne(wrapper);
            if(evaluationStudent != null){
                evaluationStudentVo.setScore(evaluationStudent.getScore());
                evaluationStudentVo.setComment(evaluationStudent.getComment());
                evaluationStudentVo.setUpdateTime(evaluationStudent.getUpdateTime());
            }
        }
        return ResponseResult.success(pageResult);
    }

    @Override
    public ResponseResult<Object> updateEvaluationStudent(EvaluationStudent evaluationStudent) {
        Date date = new Date();
        if(evaluationStudent.getEvaluationstuId() == null){
            evaluationStudent.setCreateTime(date);
            evaluationStudent.setUpdateTime(date);
            this.save(evaluationStudent);
        }else {
            evaluationStudent.setUpdateTime(date);
            this.updateById(evaluationStudent);
        }
        return ResponseResult.success();
    }

}




