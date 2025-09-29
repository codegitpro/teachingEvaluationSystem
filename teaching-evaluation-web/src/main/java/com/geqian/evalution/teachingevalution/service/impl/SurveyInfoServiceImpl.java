package com.geqian.evalution.teachingevalution.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.geqian.common.common.PageRequest;
import com.geqian.common.common.PageResult;
import com.geqian.common.common.ResponseResult;
import com.geqian.common.utils.AssertUtils;
import com.geqian.common.utils.ExcelUtils;
import com.geqian.common.utils.JwtUtils;
import com.geqian.common.utils.LocalDateTimeUtils;
import com.geqian.evalution.teachingevalution.common.dto.SurveyChartDto;
import com.geqian.evalution.teachingevalution.common.vo.SurveyExportData;
import com.geqian.evalution.teachingevalution.entity.QuestionInfo;
import com.geqian.evalution.teachingevalution.entity.SurveyInfo;
import com.geqian.evalution.teachingevalution.mapper.AnswerInfoMapper;
import com.geqian.evalution.teachingevalution.mapper.SurveyInfoMapper;
import com.geqian.evalution.teachingevalution.service.QuestionInfoService;
import com.geqian.evalution.teachingevalution.service.SurveyInfoService;
import com.geqian.evalution.teachingevalution.service.UserService;
import com.geqian.evalution.teachingevalution.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @description 针对表【survey_info(调查问卷表)】的数据库操作Service实现
 * @createDate 2025-09-11 22:05:06
 */
@Service
public class SurveyInfoServiceImpl extends ServiceImpl<SurveyInfoMapper, SurveyInfo>
        implements SurveyInfoService {

    @Resource
    private UserService userService;


    @Resource
    private AnswerInfoMapper answerInfoMapper;

    @Resource
    private QuestionInfoService questionInfoService;

    @Override
    public ResponseResult<Object> addQuestionnaire(SurveyInfo surveyInfo, String token) {
        if (Objects.nonNull(surveyInfo)) {
            String userId = JwtUtils.parseToken(token);
            surveyInfo.setCreatorId(Integer.valueOf(userId));
            surveyInfo.setUpdatorId(Integer.valueOf(userId));
            Date date = new Date();
            surveyInfo.setCreateDate(date);
            surveyInfo.setUpdateDate(date);
            this.save(surveyInfo);
            return ResponseResult.success();
        }
        return ResponseResult.fail();
    }

    @Override
    public ResponseResult<Object> updateQuestionnaire(SurveyInfo surveyInfo, String token) {
        if (Objects.nonNull(surveyInfo)) {
            String userId = JwtUtils.parseToken(token);
            surveyInfo.setUpdatorId(Integer.valueOf(userId));
            surveyInfo.setUpdateDate(new Date());
            this.baseMapper.updateById(surveyInfo);
        }
        return ResponseResult.success();
    }

    @Override
    public ResponseResult<Object> deleteQuestionnaire(String surveyId) {
        if (StringUtils.hasText(surveyId)) {
            this.baseMapper.deleteById(surveyId);
        }
        return ResponseResult.success();
    }

    @Override
    public ResponseResult<Object> pageSearchQuestionnaire(PageRequest pageRequest) {
        LambdaQueryWrapper<SurveyInfo> wrapper = new LambdaQueryWrapper<>();
        String key = pageRequest.getKey();
        if (AssertUtils.isNotEmpty(key)) {
            wrapper.like(SurveyInfo::getSurveyName, key);
        }
        PageHelper.startPage(pageRequest.getPageNum(), pageRequest.getPageSize());
        List<SurveyInfo> surveyInfoList = this.baseMapper.selectList(wrapper);
        PageInfo<SurveyInfo> pageInfo = new PageInfo<>(surveyInfoList);
        PageResult<SurveyInfo> pageResult = PageUtils.getPageResult(pageInfo);
        for (SurveyInfo surveyInfo : pageResult.getDataList()) {
            if (surveyInfo.getEndTime().before(new Date()) && Objects.equals(surveyInfo.getStatus(),1)){
                surveyInfo.setStatus(2);
                this.updateById(surveyInfo);
            }
        }
        return ResponseResult.success(pageResult);
    }

    @Override
    public ResponseResult<Object> operateQuestionnaire(List<SurveyInfo> surveyInfoList, String token) {
        if (!CollectionUtils.isEmpty(surveyInfoList)) {
            String userId = JwtUtils.parseToken(token);
            for (SurveyInfo surveyInfo : surveyInfoList) {
                surveyInfo.setUpdatorId(Integer.valueOf(userId));
                surveyInfo.setUpdateDate(new Date());
            }
            this.updateBatchById(surveyInfoList);
            return ResponseResult.success();
        }
        return ResponseResult.fail();
    }

    @Override
    public ResponseResult<Object> getWriteQuestionnaire(PageRequest pageRequest, String token) {
        String userId = JwtUtils.parseToken(token);
        List<Long> survryIdList = answerInfoMapper.selectSubmitSurvryId(Long.valueOf(userId));
        LambdaQueryWrapper<SurveyInfo> wrapper = new LambdaQueryWrapper<>();
        String key = pageRequest.getKey();
        if (AssertUtils.isNotEmpty(key)) {
            wrapper.like(SurveyInfo::getSurveyName, key);
        }
        wrapper.eq(SurveyInfo::getStatus, 1);
        String now = LocalDateTimeUtils.formatToString(LocalDateTime.now());
        wrapper.ge(SurveyInfo::getEndTime, now).le(SurveyInfo::getStartTime, now);
        if (!CollectionUtils.isEmpty(survryIdList)) {
            wrapper.notIn(SurveyInfo::getSurveyId, survryIdList);
        }
        PageHelper.startPage(pageRequest.getPageNum(), pageRequest.getPageSize());
        List<SurveyInfo> surveyInfoList = this.baseMapper.selectList(wrapper);
        PageInfo<SurveyInfo> pageInfo = new PageInfo<>(surveyInfoList);
        PageResult<SurveyInfo> pageResult = PageUtils.getPageResult(pageInfo);
        return ResponseResult.success(pageResult);
    }

    @Override
    public ResponseResult<Object> getSurveyChartData(SurveyChartDto surveyChartDto) {
        List<Map<String, Object>> surveyChartData = this.baseMapper.selectSurveyChartData(surveyChartDto);
        List<String> options = this.baseMapper.selectOptions(surveyChartDto);
        List<String> labelList = surveyChartData.stream().map(item -> (String) item.get("name")).collect(Collectors.toList());
        options.removeAll(labelList);
        for (String option : options) {
            HashMap<String, Object> data = new HashMap<>();
            data.put("name", option);
            data.put("value", 0);
            surveyChartData.add(data);
        }
        return ResponseResult.success(surveyChartData);
    }

    @Override
    public ResponseResult<Object> getSurveyAllList() {
        List<SurveyInfo> surveyInfoList = this.list();
        return ResponseResult.success(surveyInfoList);
    }

    @Override
    public ResponseResult<Object> getSurveyQuestionList(Long surveyId) {
        LambdaQueryWrapper<QuestionInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(QuestionInfo::getSurveyId, surveyId).in(QuestionInfo::getQuestionType, 1, 2);
        List<QuestionInfo> questionInfoList = questionInfoService.list(wrapper);
        return ResponseResult.success(questionInfoList);
    }


    @SneakyThrows
    @Override
    public void exportSurveyData(Long surveyId, HttpServletResponse response) {
        List<SurveyExportData> dataList = this.baseMapper.selectExportData(surveyId);
        byte[] bytes = ExcelUtils.generateExcel(dataList, SurveyExportData.class);
        ExcelUtils.download(bytes, "问卷调查数据", response);
    }

}




