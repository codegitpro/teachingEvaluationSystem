package com.geqian.evalution.teachingevalution.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.geqian.common.common.PageRequest;
import com.geqian.common.common.PageResult;
import com.geqian.common.common.ResponseResult;
import com.geqian.common.utils.AssertUtils;
import com.geqian.evalution.teachingevalution.common.dto.CourseStudentDto;
import com.geqian.evalution.teachingevalution.entity.CourseStudent;
import com.geqian.evalution.teachingevalution.entity.Student;
import com.geqian.evalution.teachingevalution.mapper.CourseStudentMapper;
import com.geqian.evalution.teachingevalution.mapper.StudentMapper;
import com.geqian.evalution.teachingevalution.service.CourseStudentService;
import com.geqian.evalution.teachingevalution.service.StudentService;
import com.geqian.evalution.teachingevalution.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【tb_student(学生表)】的数据库操作Service实现
* @createDate 2025-09-12 11:34:26
*/
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student>
    implements StudentService{

    @Resource
    private CourseStudentService courseStudentService;

    @Resource
    private CourseStudentMapper courseStudentMapper;

    @Override
    public ResponseResult<Object> deleteStudent(String studentId) {
        if (Objects.nonNull(studentId)){
            this.baseMapper.deleteById(studentId);
        }
        return ResponseResult.success();
    }

    @Override
    public ResponseResult<Object> addStudent(Student student) {
        if (Objects.nonNull(student)){
            this.save(student);
        }
        return ResponseResult.success();
    }

    @Override
    public ResponseResult<Object> updateStudent(Student student) {
        if (Objects.nonNull(student)){
            this.updateById(student);
        }
        return ResponseResult.success();
    }

    @Override
    public ResponseResult<PageResult<Student>> getStudentList(PageRequest pageRequest) {
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        String key = pageRequest.getKey();
        if (AssertUtils.isNotEmpty(key)) {
            wrapper.like(Student::getStdName, key);
        }
        PageHelper.startPage(pageRequest.getPageNum(),pageRequest.getPageSize());
        List<Student> studentList = this.baseMapper.selectList(wrapper);
        PageInfo<Student> pageInfo = new PageInfo<>(studentList);
        PageResult<Student> pageResult = PageUtils.getPageResult(pageInfo);
        return ResponseResult.success(pageResult);
    }

    @Override
    public ResponseResult<Object> courseAllocation(CourseStudentDto dto) {
        if (Objects.nonNull(dto) && !CollectionUtils.isEmpty(dto.getStudentIdList())){
            dto.getStudentIdList().stream().map(item -> {
                CourseStudent courseStudent = new CourseStudent();
                courseStudent.setStudentId(item);
                courseStudent.setCourseId(dto.getCourseId());
                return courseStudent;
            }).collect(Collectors.toList())
                    .forEach(courseStudent -> courseStudentMapper.insertIfNotExist(courseStudent));

            return ResponseResult.success();
        }
        return ResponseResult.fail();
    }
}




