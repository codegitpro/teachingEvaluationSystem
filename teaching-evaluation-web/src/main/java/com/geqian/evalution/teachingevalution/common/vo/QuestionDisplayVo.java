package com.geqian.evalution.teachingevalution.common.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author geqian
 * @date 14:09 2025/9/18
 */

@Data
public class QuestionDisplayVo {

    private Long questionId;

    private Integer questionType;

    private String questionDescribe;

    private Integer requiredFlag;

    private List<Map<String,Object>> options;
}
