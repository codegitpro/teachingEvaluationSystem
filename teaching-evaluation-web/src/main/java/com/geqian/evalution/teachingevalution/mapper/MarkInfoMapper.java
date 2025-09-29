package com.geqian.evalution.teachingevalution.mapper;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.geqian.evalution.teachingevalution.common.vo.MarkExportData;
import com.geqian.evalution.teachingevalution.common.vo.SelecionVo;
import com.geqian.evalution.teachingevalution.entity.MarkInfo;

import java.util.List;

/**
 * @author Administrator
 * @description 针对表【mark_info(打分表)】的数据库操作Mapper
 * @createDate 2025-09-19 12:29:24
 * @Entity com.geqian.evalution.teachingevalution.entity.MarkInfo
 */
public interface MarkInfoMapper extends BaseMapper<MarkInfo> {

    List<Long> selectMarkChartData(@Param("teacherId") Long teacherId, @Param("indexId") Long indexId, @Param("courseId") Long courseId);


    List<SelecionVo> selectIndexAvgScore(@Param("teacherId") Long teacherId, @Param("courseId") Long courseId);

    List<MarkExportData> selectMarkExportData(Long teacherId);

    int insertIfNotExist(MarkInfo markInfo);


    int deleteByEvaluationId(@Param("evaluationId") Long evaluationId);
}




