package com.geqian.evalution.teachingevalution.common.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author geqian
 * @date 1:19 2025/9/20
 */
@Data
public class EvaluationStudentVo {
    private Long evaluationstuId;

    private Long studentId;

    private String studentName;

    private Long courseId;

    private String courseName;

    private Long teacherId;

    private String teacherName;

    private Integer score;

    private String comment;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
