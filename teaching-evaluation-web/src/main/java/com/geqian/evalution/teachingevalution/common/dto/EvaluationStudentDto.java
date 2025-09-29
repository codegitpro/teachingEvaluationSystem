package com.geqian.evalution.teachingevalution.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author geqian
 * @date 1:19 2025/9/20
 */
@Data
public class EvaluationStudentDto {
    private Long studentId;

    private String studentName;

    private Long courseId;

    private String courseName;

    private Long teacherId;

    private String teacherName;
}
