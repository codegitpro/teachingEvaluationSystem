package com.geqian.evalution.teachingevalution.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.geqian.evalution.teachingevalution.entity.EntrepreneurshipEvaluation;
import com.geqian.evalution.teachingevalution.entity.EntrepreneurshipProject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface EntrepreneurshipEvaluationMapper extends BaseMapper<EntrepreneurshipEvaluation> {

        @Select("SELECT e.*, p.project_name " +
                        "FROM entrepreneurship_evaluation e " +
                        "LEFT JOIN entrepreneurship_project p ON e.project_id = p.project_id " +
                        "ORDER BY e.create_time DESC")
        List<Map<String, Object>> selectEvaluationListWithProject();

        @Select("SELECT p.* " +
                        "FROM entrepreneurship_project p " +
                        "INNER JOIN entrepreneurship_evaluation e ON p.project_id = e.project_id " +
                        "WHERE e.student_id = #{studentId} " +
                        "ORDER BY p.create_time DESC")
        List<EntrepreneurshipProject> selectEvaluatedProjects(Long studentId);

        @Select("SELECT p.* " +
                        "FROM entrepreneurship_project p " +
                        "LEFT JOIN entrepreneurship_evaluation e ON p.project_id = e.project_id AND e.student_id = #{studentId} "
                        +
                        "WHERE e.evaluation_id IS NULL " +
                        "ORDER BY p.create_time DESC")
        List<EntrepreneurshipProject> selectUnevaluatedProjects(Long studentId);
}