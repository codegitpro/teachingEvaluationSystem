package com.geqian.evalution.teachingevalution.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.geqian.evalution.teachingevalution.entity.VolunteerEvaluation;
import com.geqian.evalution.teachingevalution.entity.VolunteerProject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface VolunteerEvaluationMapper extends BaseMapper<VolunteerEvaluation> {

  @Select("SELECT e.*, p.project_name " +
      "FROM volunteer_evaluation e " +
      "LEFT JOIN volunteer_project p ON e.project_id = p.project_id " +
      "ORDER BY e.create_time DESC")
  List<Map<String, Object>> selectEvaluationListWithProject();

  @Select("SELECT p.* " +
      "FROM volunteer_project p " +
      "INNER JOIN volunteer_evaluation e ON p.project_id = e.project_id " +
      "WHERE e.student_id = #{studentId} " +
      "ORDER BY p.create_time DESC")
  List<VolunteerProject> selectEvaluatedProjects(Integer studentId);

  @Select("SELECT p.* " +
      "FROM volunteer_project p " +
      "LEFT JOIN volunteer_evaluation e ON p.project_id = e.project_id AND e.student_id = #{studentId} " +
      "WHERE e.evaluation_id IS NULL " +
      "ORDER BY p.create_time DESC")
  List<VolunteerProject> selectUnevaluatedProjects(Integer studentId);
}