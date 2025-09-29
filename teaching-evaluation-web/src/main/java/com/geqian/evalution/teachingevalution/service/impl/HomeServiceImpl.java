package com.geqian.evalution.teachingevalution.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.geqian.common.common.ResponseResult;
import com.geqian.common.utils.JwtUtils;
import com.geqian.evalution.teachingevalution.common.vo.HomeCountVo;
import com.geqian.evalution.teachingevalution.entity.*;
import com.geqian.evalution.teachingevalution.service.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Service
public class HomeServiceImpl implements HomeService {

  @Resource
  private EvaluationInfoService evaluationInfoService;

  @Resource
  private SurveyInfoService surveyInfoService;

  @Resource
  private CourseService courseService;

  @Resource
  private StudentService studentService;

  @Resource
  private TeacherService teacherService;

  @Resource
  private UserService userService;

  @Override
  public ResponseResult<Object> getCount(String token) {
    String userId = JwtUtils.parseToken(token);
    User user = userService.getById(userId);
    HomeCountVo countVO = new HomeCountVo();
    String now = LocalDateTime.now().toString();

    if (user.getUserType() == 3) { // 管理员
      // 获取评教总数
      countVO.setEvaluationCount(evaluationInfoService.count());
      // 获取问卷总数
      countVO.setQuestionnaireCount(surveyInfoService.count());
      // 获取教师总数
      countVO.setCourseCount(teacherService.count());
      // 获取学生总数
      countVO.setPeopleCount(studentService.count());
    } else {
      if (user.getUserType() == 0) { // 学生
        // 获取待评教数
        LambdaQueryWrapper<EvaluationInfo> evaluationWrapper = new LambdaQueryWrapper<>();
        evaluationWrapper.eq(EvaluationInfo::getStudentId, userId)
            .eq(EvaluationInfo::getStatus, 0)
            .ge(EvaluationInfo::getEndTime, now)
            .le(EvaluationInfo::getStartTime, now);
        countVO.setEvaluationCount(evaluationInfoService.count(evaluationWrapper));
        countVO.setPeopleCount(teacherService.count());
      } else if (user.getUserType() == 1) { // 教师
        // 获取评教总数和问卷总数与管理员相同
        countVO.setEvaluationCount(evaluationInfoService.count());
        countVO.setQuestionnaireCount(surveyInfoService.count());
        countVO.setPeopleCount(studentService.count());
      }

      if (user.getUserType() == 0) { // 只有学生需要查询待填写问卷
        // 获取问卷待填写数
        LambdaQueryWrapper<SurveyInfo> surveyWrapper = new LambdaQueryWrapper<>();
        surveyWrapper.eq(SurveyInfo::getStatus, 1)
            .ge(SurveyInfo::getEndTime, now)
            .le(SurveyInfo::getStartTime, now);
        countVO.setQuestionnaireCount(surveyInfoService.count(surveyWrapper));
      }

      // 获取课程数
      countVO.setCourseCount(courseService.count());
    }

    return ResponseResult.success(countVO);
  }
}