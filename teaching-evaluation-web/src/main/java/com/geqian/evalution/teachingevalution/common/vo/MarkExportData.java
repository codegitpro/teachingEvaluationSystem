package com.geqian.evalution.teachingevalution.common.vo;

import com.geqian.common.annotation.HeaderAlias;
import lombok.Data;

/**
 * @author geqian
 * @date 12:14 2025/9/22
 */

@Data
public class MarkExportData {

    @HeaderAlias("学生编号")
    private String studentId;

    @HeaderAlias("学生姓名")
    private String studentName;

    @HeaderAlias("教师编号")
    private String teacherId;

    @HeaderAlias("教师姓名")
    private String teacherName;

    @HeaderAlias("课程编号")
    private String courseId;

    @HeaderAlias("课程名称")
    private String courseName;

    @HeaderAlias("评分指标")
    private String indexName;

    @HeaderAlias("指标权重（占比）")
    private String weight;

    @HeaderAlias("分数")
    private String score;


}
