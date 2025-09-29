package com.geqian.evalution.teachingevalution.common.dto;

import lombok.Data;

/**
 * @author geqian
 * @date 1:18 2025/9/18
 */
@Data
public class QuestionDto {

    private Integer questionType;

    private String describe;

    private String option1;

    private String option2;

    private String option3;

    private String option4;
}
