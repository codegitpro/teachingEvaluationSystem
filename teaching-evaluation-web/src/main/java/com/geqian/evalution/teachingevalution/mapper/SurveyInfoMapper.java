package com.geqian.evalution.teachingevalution.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.geqian.evalution.teachingevalution.common.dto.SurveyChartDto;
import com.geqian.evalution.teachingevalution.common.vo.SurveyExportData;
import com.geqian.evalution.teachingevalution.entity.SurveyInfo;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @description 针对表【survey_info(调查问卷表)】的数据库操作Mapper
 * @createDate 2025-09-11 22:05:06
 * @Entity com.geqian.evalution.teachingevalution.entity.SurveyInfo
 */
public interface SurveyInfoMapper extends BaseMapper<SurveyInfo> {

    List<Map<String, Object>> selectSurveyChartData(SurveyChartDto surveyChartDto);

    List<String> selectOptions(SurveyChartDto surveyChartDto);

    List<SurveyExportData> selectExportData(Long surveyId);
}




