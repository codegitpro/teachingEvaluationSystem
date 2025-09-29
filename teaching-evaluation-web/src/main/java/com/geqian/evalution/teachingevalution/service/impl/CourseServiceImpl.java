package com.geqian.evalution.teachingevalution.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.geqian.common.common.PageRequest;
import com.geqian.common.common.PageResult;
import com.geqian.common.common.ResponseResult;
import com.geqian.common.utils.AssertUtils;
import com.geqian.evalution.teachingevalution.entity.Course;
import com.geqian.evalution.teachingevalution.entity.CourseTeacher;
import com.geqian.evalution.teachingevalution.mapper.CourseMapper;
import com.geqian.evalution.teachingevalution.mapper.CourseTeacherMapper;
import com.geqian.evalution.teachingevalution.service.CourseService;
import com.geqian.evalution.teachingevalution.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @description 针对表【tb_course(课程表)】的数据库操作Service实现
 * @createDate 2025-09-12 22:39:04
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course>
        implements CourseService {

    @Resource
    private CourseTeacherMapper courseTeacherMapper;

    @Override
    public ResponseResult<PageResult<Course>> getCourseList(PageRequest pageRequest) {
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
        String key = pageRequest.getKey();
        if (AssertUtils.isNotEmpty(key)) {
            wrapper.like(Course::getCosName, key);
        }
        PageHelper.startPage(pageRequest.getPageNum(), pageRequest.getPageSize());
        List<Course> courseList = this.baseMapper.selectList(wrapper);
        PageInfo<Course> pageInfo = new PageInfo<>(courseList);
        PageResult<Course> pageResult = PageUtils.getPageResult(pageInfo);
        return ResponseResult.success(pageResult);
    }

    @Override
    public ResponseResult<Object> updateCourse(Course course) {
        if (Objects.nonNull(course)) {
            this.updateById(course);
        }
        return ResponseResult.success();
    }

    @Override
    public ResponseResult<Object> addCourse(Course course) {
        if (Objects.nonNull(course)) {
            this.save(course);
        }
        return ResponseResult.success();
    }

    @Override
    public ResponseResult<Object> deleteCourse(String courseId) {
        if (Objects.nonNull(courseId)) {
            this.removeById(courseId);
        }
        return ResponseResult.success();
    }

    @Override
    public ResponseResult<List<Course>> getCourseAllList() {
        List<Course> courseList = this.list();
        return ResponseResult.success(courseList);
    }

    @Override
    public ResponseResult<List<Long>> getExistsCourseList(String teacherId) {
        if (StringUtils.hasText(teacherId)) {
            LambdaQueryWrapper<CourseTeacher> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(CourseTeacher::getTeacherId, teacherId);
            List<Long> courseIdList = courseTeacherMapper.selectList(wrapper).stream().map(CourseTeacher::getCourseId).collect(Collectors.toList());
            return ResponseResult.success(courseIdList);
        }
        return ResponseResult.fail();
    }

    @Override
    public ResponseResult<List<Course>> getCourseExistList(Long teacherId) {
        List<Course> courseList = this.baseMapper.getCourseExistList(teacherId);
        return ResponseResult.success(courseList);
    }
}




