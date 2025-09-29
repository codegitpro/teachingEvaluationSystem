package com.geqian.evalution.teachingevalution.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.geqian.common.common.PageRequest;
import com.geqian.common.common.PageResult;
import com.geqian.common.common.ResponseResult;
import com.geqian.common.utils.AssertUtils;
import com.geqian.evalution.teachingevalution.entity.IndexInfo;
import com.geqian.evalution.teachingevalution.mapper.IndexInfoMapper;
import com.geqian.evalution.teachingevalution.service.IndexInfoService;
import com.geqian.evalution.teachingevalution.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
* @author Administrator
* @description 针对表【index_info(指标)】的数据库操作Service实现
* @createDate 2025-09-19 10:31:39
*/
@Service
public class IndexInfoServiceImpl extends ServiceImpl<IndexInfoMapper, IndexInfo>
    implements IndexInfoService{

    @Override
    public ResponseResult<Object> deleteIndex(String indexId) {
        this.removeById(indexId);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult<Object> updateIndex(IndexInfo indexInfo) {
        if (Objects.nonNull(indexInfo)){
            this.updateById(indexInfo);
            return ResponseResult.success();
        }
        return ResponseResult.fail();
    }

    @Override
    public ResponseResult<PageResult<IndexInfo>> getIndexList(PageRequest pageRequest) {
        LambdaQueryWrapper<IndexInfo> wrapper = new LambdaQueryWrapper<>();
        String key = pageRequest.getKey();
        if (AssertUtils.isNotEmpty(key)) {
            wrapper.like(IndexInfo::getIndexName, key);
        }
        PageHelper.startPage(pageRequest.getPageNum(),pageRequest.getPageSize());
        List<IndexInfo> indexInfoList = this.baseMapper.selectList(wrapper);
        PageInfo<IndexInfo> pageInfo = new PageInfo<>(indexInfoList);
        PageResult<IndexInfo> pageResult = PageUtils.getPageResult(pageInfo);
        return ResponseResult.success(pageResult);
    }

    @Override
    public ResponseResult<Object> addIndex(IndexInfo indexInfo) {
        if (Objects.nonNull(indexInfo)){
            this.save(indexInfo);
            return ResponseResult.success();
        }
        return ResponseResult.fail();
    }

    @Override
    public ResponseResult<List<IndexInfo>> getIndexAllList() {
        List<IndexInfo> indexInfoList = this.list();
        return ResponseResult.success(indexInfoList);
    }
}




