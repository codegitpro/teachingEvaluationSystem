package com.geqian.evalution.teachingevalution.service;

import com.geqian.common.common.PageRequest;
import com.geqian.common.common.ResponseResult;
import com.geqian.evalution.teachingevalution.common.dto.SurveyChartDto;
import com.geqian.evalution.teachingevalution.entity.SurveyInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Administrator
 * @description 针对表【survey_info(调查问卷表)】的数据库操作Service
 * @createDate 2025-09-11 22:05:06
 */
public interface SurveyInfoService extends IService<SurveyInfo> {

    ResponseResult<Object> addQuestionnaire(SurveyInfo surveyInfo, String token);

    ResponseResult<Object> updateQuestionnaire(SurveyInfo surveyInfo, String token);

    ResponseResult<Object> deleteQuestionnaire(String surveyId);

    ResponseResult<Object> pageSearchQuestionnaire(PageRequest pageRequest);

    ResponseResult<Object> operateQuestionnaire(List<SurveyInfo> surveyInfoList, String token);

    ResponseResult<Object> getWriteQuestionnaire(PageRequest pageRequest, String token);

    ResponseResult<Object> getSurveyChartData(SurveyChartDto surveyChartDto);

    ResponseResult<Object> getSurveyAllList();

    ResponseResult<Object> getSurveyQuestionList(Long surveyId);

    void exportSurveyData(Long surveyId, HttpServletResponse response);
}
