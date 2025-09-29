package com.geqian.evalution.teachingevalution.service;

import com.geqian.common.common.PageRequest;
import com.geqian.common.common.PageResult;
import com.geqian.common.common.ResponseResult;
import com.geqian.evalution.teachingevalution.entity.IndexInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Administrator
* @description 针对表【index_info(指标)】的数据库操作Service
* @createDate 2025-09-19 10:31:39
*/
public interface IndexInfoService extends IService<IndexInfo> {

    ResponseResult<Object> deleteIndex(String indexId);

    ResponseResult<Object> updateIndex(IndexInfo indexInfo);

    ResponseResult<PageResult<IndexInfo>> getIndexList(PageRequest pageRequest);

    ResponseResult<Object> addIndex(IndexInfo indexInfo);

    ResponseResult<List<IndexInfo>> getIndexAllList();

}
