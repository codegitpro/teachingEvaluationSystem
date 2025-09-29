package com.geqian.evalution.teachingevalution.common.dto;

import lombok.Data;

import java.util.List;

/**
 * @author geqian
 * @date 15:05 2025/9/16
 */

@Data
public class RoleDto {

    private Long userId;

    private List<Long> roleIdList;
}
