package com.geqian.evalution.teachingevalution.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.geqian.common.common.PageRequest;
import com.geqian.common.common.PageResult;
import com.geqian.common.common.ResponseResult;
import com.geqian.common.utils.JwtUtils;
import com.geqian.evalution.teachingevalution.common.vo.StudentCourseVo;
import com.geqian.evalution.teachingevalution.entity.CourseStudent;
import com.geqian.evalution.teachingevalution.entity.EvaluationInfo;
import com.geqian.evalution.teachingevalution.entity.User;
import com.geqian.evalution.teachingevalution.mapper.CourseStudentMapper;
import com.geqian.evalution.teachingevalution.mapper.UserMapper;
import com.geqian.evalution.teachingevalution.service.CourseStudentService;
import com.geqian.evalution.teachingevalution.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author Administrator
 * @description 针对表【course_student】的数据库操作Service实现
 * @createDate 2025-09-19 11:53:51
 */
@Service
public class CourseStudentServiceImpl extends ServiceImpl<CourseStudentMapper, CourseStudent>
        implements CourseStudentService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseResult<Object> getStudentCourseList(PageRequest pageRequest,String token) {
        String userId = JwtUtils.parseToken(token);
        User user = userMapper.selectById(userId);
        String teacherId = null;
        if(user!=null && !user.getUserType().equals("3")){
            teacherId = userId;
        }
        //teacherId = null;
        PageHelper.startPage(pageRequest.getPageNum(), pageRequest.getPageSize());
        List<StudentCourseVo> studentCourseList = this.baseMapper.selectStudentCourseList(pageRequest.getKey(),teacherId);
        PageInfo<StudentCourseVo> pageInfo = new PageInfo<>(studentCourseList);
        PageResult<StudentCourseVo> pageResult = PageUtils.getPageResult(pageInfo);
        return ResponseResult.success(pageResult);
    }

    @Override
    public ResponseResult<Object> deleteStudentCourse(List<StudentCourseVo> studentCourseVoList) {
        if (!CollectionUtils.isEmpty(studentCourseVoList)) {
            for (StudentCourseVo studentCourseVo : studentCourseVoList) {
                LambdaQueryWrapper<CourseStudent> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(CourseStudent::getCourseId, studentCourseVo.getCourseId())
                        .eq(CourseStudent::getStudentId, studentCourseVo.getStudentId());
                this.remove(wrapper);
            }
            return ResponseResult.success();
        }
        return ResponseResult.fail();
    }
}




