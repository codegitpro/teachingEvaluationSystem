package com.geqian.evalution.teachingevalution.service;

import com.geqian.common.common.PageRequest;
import com.geqian.common.common.PageResult;
import com.geqian.common.common.ResponseResult;
import com.geqian.evalution.teachingevalution.common.dto.MarkSubmitDTO;
import com.geqian.evalution.teachingevalution.entity.MarkInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
* @author Administrator
* @description 针对表【mark_info(打分表)】的数据库操作Service
* @createDate 2025-09-19 12:29:24
*/
public interface MarkInfoService extends IService<MarkInfo> {

    ResponseResult<PageResult<MarkInfo>> pageSearch(PageRequest pageRequest, String token);

    ResponseResult<Object> submitMarking(MarkSubmitDTO markSubmitDTO);

    ResponseResult<Object> getMarkChartData(Long teacherId, Long indexId,Long courseId);

    ResponseResult<Object> getIndexAvgScore(Long teacherId,Long courseId);

    void exportMarkData(Long teacherId, HttpServletResponse response);

    ResponseResult<Object> getTeacherMarkDetail(Long teacherId, Long courseId);
}
