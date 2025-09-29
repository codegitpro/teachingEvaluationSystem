package com.geqian.evalution.teachingevalution.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.geqian.common.common.ResponseResult;
import com.geqian.common.utils.UUIDUtils;
import com.geqian.evalution.teachingevalution.common.vo.QuestionDisplayVo;
import com.geqian.evalution.teachingevalution.entity.QuestionInfo;
import com.geqian.evalution.teachingevalution.mapper.QuestionInfoMapper;
import com.geqian.evalution.teachingevalution.service.QuestionInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @description 针对表【question_info(问卷调查问题表)】的数据库操作Service实现
 * @createDate 2025-09-18 01:45:49
 */
@Service
public class QuestionInfoServiceImpl extends ServiceImpl<QuestionInfoMapper, QuestionInfo>
        implements QuestionInfoService {

    @Override
    public ResponseResult<Object> addQuestionList(List<QuestionInfo> questionInfoList) {
        if (!CollectionUtils.isEmpty(questionInfoList)) {
            LambdaQueryWrapper<QuestionInfo> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(QuestionInfo::getSurveyId,questionInfoList.get(0).getSurveyId());
            //删除旧题目
            this.remove(wrapper);
            //保存新题目
            this.saveBatch(questionInfoList);
            return ResponseResult.success();
        }
        return ResponseResult.fail();
    }

    @Override
    public ResponseResult<Object> getQuestionList(Long surveyId) {
        if (!Objects.equals(surveyId, null)) {
            LambdaQueryWrapper<QuestionInfo> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(QuestionInfo::getSurveyId,surveyId);
            List<QuestionInfo> questionInfoList = this.list(wrapper);
            return ResponseResult.success(questionInfoList);
        }
        return ResponseResult.fail();
    }

    @Override
    public ResponseResult<Object> getQuestionDisplayData(Long surveyId) {
        if (!Objects.equals(surveyId, null)) {
            LambdaQueryWrapper<QuestionInfo> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(QuestionInfo::getSurveyId,surveyId);
            List<QuestionInfo> questionInfoList = this.list(wrapper);
            List<QuestionDisplayVo> dataList = questionInfoList.stream().map(item -> {
                QuestionDisplayVo questionDisplayVo = new QuestionDisplayVo();
                BeanUtils.copyProperties(item, questionDisplayVo);
                Map<String, Object> option1 = new HashMap<>();
                option1.put("key", UUIDUtils.generateUUID());
                option1.put("value", item.getOption1());

                Map<String, Object> option2 = new HashMap<>();
                option2.put("key", UUIDUtils.generateUUID());
                option2.put("value", item.getOption2());

                Map<String, Object> option3 = new HashMap<>();
                option3.put("key", UUIDUtils.generateUUID());
                option3.put("value", item.getOption3());

                Map<String, Object> option4 = new HashMap<>();
                option4.put("key", UUIDUtils.generateUUID());
                option4.put("value", item.getOption4());
                questionDisplayVo.setOptions(Arrays.asList(option1, option2, option3, option4));
                return questionDisplayVo;
            }).sorted((a,b)->a.getQuestionType() - b.getQuestionType()).collect(Collectors.toList());

            return ResponseResult.success(dataList);
        }
        return ResponseResult.fail();
    }
}




