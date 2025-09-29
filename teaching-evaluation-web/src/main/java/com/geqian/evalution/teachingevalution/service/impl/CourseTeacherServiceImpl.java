package com.geqian.evalution.teachingevalution.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.geqian.common.common.PageRequest;
import com.geqian.common.common.PageResult;
import com.geqian.common.common.ResponseResult;
import com.geqian.common.utils.JwtUtils;
import com.geqian.evalution.teachingevalution.common.vo.TeacherCourseVo;
import com.geqian.evalution.teachingevalution.entity.CourseTeacher;
import com.geqian.evalution.teachingevalution.entity.EvaluationInfo;
import com.geqian.evalution.teachingevalution.entity.Teacher;
import com.geqian.evalution.teachingevalution.entity.User;
import com.geqian.evalution.teachingevalution.mapper.CourseTeacherMapper;
import com.geqian.evalution.teachingevalution.mapper.TeacherMapper;
import com.geqian.evalution.teachingevalution.mapper.UserMapper;
import com.geqian.evalution.teachingevalution.service.CourseTeacherService;
import com.geqian.evalution.teachingevalution.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author Administrator
 * @description 针对表【course_teacher(课程教师关联表)】的数据库操作Service实现
 * @createDate 2025-09-19 11:53:51
 */
@Service
public class CourseTeacherServiceImpl extends ServiceImpl<CourseTeacherMapper, CourseTeacher>
        implements CourseTeacherService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Override
    public ResponseResult<Object> getTeacherCourseList(PageRequest pageRequest,String token) {
        String teacherId = JwtUtils.parseToken(token);
        Teacher teacher = teacherMapper.selectById(teacherId);
        if(teacher==null){
            teacherId = null;
        }
        //teacherId = null;
        PageHelper.startPage(pageRequest.getPageNum(), pageRequest.getPageSize());
        List<TeacherCourseVo> teacherCourseVoList = this.baseMapper.selectTeacherCourseList(pageRequest.getKey(),teacherId);
        PageInfo<TeacherCourseVo> pageInfo = new PageInfo<>(teacherCourseVoList);
        PageResult<TeacherCourseVo> pageResult = PageUtils.getPageResult(pageInfo);
        return ResponseResult.success(pageResult);
    }

    @Override
    public ResponseResult<Object> deleteTeacherCourse(List<TeacherCourseVo> teacherCourseVoList) {
        if (!CollectionUtils.isEmpty(teacherCourseVoList)) {
            for (TeacherCourseVo teacherCourseVo : teacherCourseVoList) {
                LambdaQueryWrapper<CourseTeacher> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(CourseTeacher::getCourseId, teacherCourseVo.getCourseId())
                        .eq(CourseTeacher::getTeacherId, teacherCourseVo.getTeacherId());
                this.remove(wrapper);
            }
            return ResponseResult.success();
        }
        return ResponseResult.fail();
    }
}




