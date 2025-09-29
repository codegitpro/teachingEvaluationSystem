package com.geqian.evalution.teachingevalution.controller;

import com.geqian.common.common.ResponseResult;
import com.geqian.evalution.teachingevalution.service.HomeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "首页统计")
@RestController
@RequestMapping("/home")
public class HomeController {

    @Resource
    private HomeService homeService;

    @ApiOperation("获取首页统计数据")
    @GetMapping("/getCount")
    public ResponseResult<Object> getCount(@RequestHeader("token") String token) {
        return homeService.getCount(token);
    }
}
