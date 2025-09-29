package com.geqian.evalution.teachingevalution.common.vo;

import lombok.Data;

@Data
public class HomeCountVo {
  private Long evaluationCount; // 待评教数
  private Long questionnaireCount; // 问卷待填写数
  private Long courseCount; // 课程数
  private Long peopleCount; // 学生数或教师数
}
