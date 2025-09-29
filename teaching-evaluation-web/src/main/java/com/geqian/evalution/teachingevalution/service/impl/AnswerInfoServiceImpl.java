package com.geqian.evalution.teachingevalution.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.geqian.common.common.ResponseResult;
import com.geqian.common.utils.JwtUtils;
import com.geqian.evalution.teachingevalution.entity.AnswerInfo;
import com.geqian.evalution.teachingevalution.mapper.AnswerInfoMapper;
import com.geqian.evalution.teachingevalution.service.AnswerInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【answer_info(用户答案表)】的数据库操作Service实现
* @createDate 2025-09-11 22:05:06
*/
@Service
public class AnswerInfoServiceImpl extends ServiceImpl<AnswerInfoMapper, AnswerInfo> implements AnswerInfoService{

    @Override
    public ResponseResult<Object> submitQuestionnaire(List<AnswerInfo> answerInfoList, String token) {
        if (!CollectionUtils.isEmpty(answerInfoList)){
            String userId = JwtUtils.parseToken(token);
            List<AnswerInfo> dataList = answerInfoList.stream()
                    .filter(answerInfo -> !CollectionUtils.isEmpty(answerInfo.getAnswerOptionList()) || StringUtils.hasText(answerInfo.getAnswerContent()))
                    .collect(Collectors.toList());

            Date date = new Date();

            List<AnswerInfo> answerInfos = answerInfoList.stream()
                    .filter(answerInfo -> !CollectionUtils.isEmpty(answerInfo.getAnswerOptionList()))
                    .map(answerInfo -> {
                        return answerInfo.getAnswerOptionList().stream().map(item -> {
                            AnswerInfo answer = new AnswerInfo();
                            BeanUtils.copyProperties(answerInfo, answer);
                            answer.setAnswerContent(item);
                            answer.setStudentId(userId);
                            answer.setAnswerDate(date);
                            return answer;
                        }).collect(Collectors.toList());
                    }).flatMap(Collection::stream).collect(Collectors.toList());

            answerInfos.addAll(dataList.stream().filter(answerInfo -> StringUtils.hasText(answerInfo.getAnswerContent())).map(answerInfo -> {
                answerInfo.setStudentId(userId);
                answerInfo.setAnswerDate(date);
                return answerInfo;
            }).collect(Collectors.toList()));

            this.saveBatch(answerInfos);

            return ResponseResult.success();
        }
        return ResponseResult.fail();
    }

}




