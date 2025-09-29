package com.geqian.evalution.teachingevalution.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.geqian.evalution.teachingevalution.entity.ClubEvaluation;
import com.geqian.evalution.teachingevalution.entity.ClubProject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface ClubEvaluationMapper extends BaseMapper<ClubEvaluation> {

  @Select("SELECT e.*, p.project_name " +
      "FROM club_evaluation e " +
      "LEFT JOIN club_project p ON e.project_id = p.project_id " +
      "ORDER BY e.create_time DESC")
  List<Map<String, Object>> selectEvaluationListWithProject();

  @Select("SELECT p.* " +
      "FROM club_project p " +
      "INNER JOIN club_evaluation e ON p.project_id = e.project_id " +
      "WHERE e.student_id = #{studentId} " +
      "ORDER BY p.create_time DESC")
  List<ClubProject> selectEvaluatedProjects(Integer studentId);

  @Select("SELECT p.* " +
      "FROM club_project p " +
      "LEFT JOIN club_evaluation e ON p.project_id = e.project_id AND e.student_id = #{studentId} " +
      "WHERE e.evaluation_id IS NULL " +
      "ORDER BY p.create_time DESC")
  List<ClubProject> selectUnevaluatedProjects(Integer studentId);
}