package com.geqian.evalution.teachingevalution.common.dto;

import lombok.Data;

/**
 * @author geqian
 * @date 12:56 2025/9/21
 */
@Data
public class ChangePasswordDto {

    private Long userId;

    private String newPasswrod;
}
