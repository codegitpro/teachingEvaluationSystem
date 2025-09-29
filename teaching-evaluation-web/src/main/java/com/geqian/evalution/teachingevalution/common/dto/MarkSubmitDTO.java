package com.geqian.evalution.teachingevalution.common.dto;

import com.geqian.evalution.teachingevalution.entity.MarkInfo;
import lombok.Data;
import java.util.Collection;

@Data
public class MarkSubmitDTO {
    private Collection<MarkInfo> markInfoList; // 评分列表
    private String comment; // 评语
    private Long evaluationId; // 评教ID
    private Long teacherId; // 教师ID
    private Long studentId; // 学生ID
    private Long courseId; // 课程ID
}