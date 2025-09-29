package com.geqian.evalution.teachingevalution.common.dto;

import lombok.Data;

import java.util.List;

/**
 * @author geqian
 * @date 22:11 2025/9/19
 */
@Data
public class CourseTeacherDto {

    private Long teacherId;

    private List<Long> courseIdList;
}
