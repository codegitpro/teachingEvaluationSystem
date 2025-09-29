package com.geqian.evalution.teachingevalution.common.vo;

import lombok.Data;

import java.util.List;

/**
 * @author geqian
 * @date 22:11 2025/9/19
 */
@Data
public class CourseTeacherVo {

    private Long teacherId;

    private List<Long> courseIdList;
}
