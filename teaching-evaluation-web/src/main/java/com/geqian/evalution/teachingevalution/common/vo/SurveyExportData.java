package com.geqian.evalution.teachingevalution.common.vo;

import com.geqian.common.annotation.HeaderAlias;
import lombok.Data;

/**
 * @author geqian
 * @date 12:14 2025/9/22
 */

@Data
public class SurveyExportData {

    @HeaderAlias("问卷主题")
    private String surveyName;

    @HeaderAlias("问题描述")
    private String questionDescribe;

    @HeaderAlias("问题类型")
    private String questionType;

    @HeaderAlias("问题答案")
    private String answerContent;

    @HeaderAlias("答案次数")
    private String answerCount;

}
