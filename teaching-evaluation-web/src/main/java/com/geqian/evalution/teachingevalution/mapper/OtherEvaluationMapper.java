package com.geqian.evalution.teachingevalution.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.geqian.evalution.teachingevalution.entity.OtherEvaluation;
import com.geqian.evalution.teachingevalution.entity.OtherProject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface OtherEvaluationMapper extends BaseMapper<OtherEvaluation> {

  @Select("SELECT e.*, p.project_name " +
      "FROM other_evaluation e " +
      "LEFT JOIN other_project p ON e.project_id = p.project_id " +
      "ORDER BY e.create_time DESC")
  List<Map<String, Object>> selectEvaluationListWithProject();

  @Select("SELECT p.* " +
      "FROM other_project p " +
      "INNER JOIN other_evaluation e ON p.project_id = e.project_id " +
      "WHERE e.student_id = #{studentId} " +
      "ORDER BY p.create_time DESC")
  List<OtherProject> selectEvaluatedProjects(Integer studentId);

  @Select("SELECT p.* " +
      "FROM other_project p " +
      "LEFT JOIN other_evaluation e ON p.project_id = e.project_id AND e.student_id = #{studentId} " +
      "WHERE e.evaluation_id IS NULL " +
      "ORDER BY p.create_time DESC")
  List<OtherProject> selectUnevaluatedProjects(Integer studentId);
}