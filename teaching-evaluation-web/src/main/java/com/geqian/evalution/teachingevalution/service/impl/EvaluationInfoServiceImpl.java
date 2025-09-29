package com.geqian.evalution.teachingevalution.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.geqian.common.common.PageRequest;
import com.geqian.common.common.PageResult;
import com.geqian.common.common.ResponseResult;
import com.geqian.common.utils.JwtUtils;
import com.geqian.common.utils.LocalDateTimeUtils;
import com.geqian.evalution.teachingevalution.entity.*;
import com.geqian.evalution.teachingevalution.mapper.CourseTeacherMapper;
import com.geqian.evalution.teachingevalution.mapper.EvaluationInfoMapper;
import com.geqian.evalution.teachingevalution.service.CourseService;
import com.geqian.evalution.teachingevalution.service.CourseStudentService;
import com.geqian.evalution.teachingevalution.service.EvaluationInfoService;
import com.geqian.evalution.teachingevalution.service.TeacherService;
import com.geqian.evalution.teachingevalution.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【evaluation_info(评分表)】的数据库操作Service实现
* @createDate 2025-09-19 12:27:36
*/
@Service
public class EvaluationInfoServiceImpl extends ServiceImpl<EvaluationInfoMapper, EvaluationInfo>
    implements EvaluationInfoService{

    @Resource
    private CourseStudentService courseStudentService;

    @Resource
    private CourseTeacherMapper courseTeacherMapper;


    @Resource
    private TeacherService teacherService;


    @Resource
    private CourseService courseService;

    @Override
    public ResponseResult<Object> openEvaluationInfo(EvaluationInfo evaluation) {
        List<CourseStudent> courseStudentList = courseStudentService.list();
        List<EvaluationInfo> evaluationInfoList = courseStudentList.stream().map(courseStudent -> {
            CourseTeacher courseTeacher = courseTeacherMapper.selectByCourseId(courseStudent.getCourseId());
            EvaluationInfo evaluationInfo = new EvaluationInfo();
            if (Objects.nonNull(courseTeacher)){
                evaluationInfo.setStudentId(courseStudent.getStudentId());
                evaluationInfo.setTeacherId(courseTeacher.getTeacherId());
                evaluationInfo.setCourseId(courseStudent.getCourseId());
                evaluationInfo.setStatus(0);
                evaluationInfo.setStartTime(evaluation.getStartTime());
                evaluationInfo.setEndTime(evaluation.getEndTime());
                return evaluationInfo;
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(evaluationInfoList)){
            evaluationInfoList.forEach(evaluationInfo -> this.baseMapper.insertIfNotExist(evaluationInfo));
        }
        return ResponseResult.success();
    }


    @Override
    public ResponseResult<Object> getEvaluationInfoList(PageRequest pageRequest,String token) {
        String userId = JwtUtils.parseToken(token);
        LambdaQueryWrapper<EvaluationInfo> wrapper = new LambdaQueryWrapper<>();
        String now = LocalDateTimeUtils.formatToString(LocalDateTime.now(), "yyyy-MM-dd HH:mm:ss");
        wrapper.eq(EvaluationInfo::getStudentId,userId)
                .ge(EvaluationInfo::getEndTime,now)
                .le(EvaluationInfo::getStartTime,now)
                .eq(EvaluationInfo::getStatus,0);
        PageHelper.startPage(pageRequest.getPageNum(),pageRequest.getPageSize());
        List<EvaluationInfo> evaluationInfoList = this.baseMapper.selectList(wrapper);
        PageInfo<EvaluationInfo> pageInfo = new PageInfo<>(evaluationInfoList);
        PageResult<EvaluationInfo> pageResult = PageUtils.getPageResult(pageInfo);
        for (EvaluationInfo evaluationInfo : pageResult.getDataList()) {
            Course course = courseService.getById(evaluationInfo.getCourseId());
            Teacher teacher = teacherService.getById(evaluationInfo.getTeacherId());
            if (Objects.nonNull(course)){
                evaluationInfo.setCourseName(course.getCosName());
            }
            if (Objects.nonNull(teacher)){
                evaluationInfo.setTeacherName(teacher.getThName());
            }
        }
        return ResponseResult.success(pageResult);
    }

    @Override
    public ResponseResult<Object> getEvaluationTime() {
        EvaluationInfo evaluationInfo = this.baseMapper.selectOne();
        if (Objects.nonNull(evaluationInfo)){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String startTime = simpleDateFormat.format(evaluationInfo.getStartTime());
            String endTime = simpleDateFormat.format(evaluationInfo.getEndTime());
            return ResponseResult.success(Arrays.asList(startTime,endTime));
        }
        return ResponseResult.fail();
    }

}




