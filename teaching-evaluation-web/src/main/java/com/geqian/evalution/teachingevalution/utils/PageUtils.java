package com.geqian.evalution.teachingevalution.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.geqian.common.common.PageResult;
import com.github.pagehelper.PageInfo;

/**
 * @author geqian
 * @date 22:36 2023/2/4
 */
public class PageUtils {

    /**
     * 封装 pagehelper 分页结果
     *
     * @param pageInfo
     * @param <T>
     * @return
     */
    public static <T> PageResult<T> getPageResult(PageInfo<T> pageInfo) {
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setTotalPage(pageInfo.getPages());
        pageResult.setTotal(pageInfo.getTotal());
        pageResult.setpageNum(pageInfo.getPageNum());
        pageResult.setPageSize(pageInfo.getPageSize());
        pageResult.setDataList(pageInfo.getList());
        pageResult.setSize(pageInfo.getSize());
        return pageResult;
    }


    /**
     * 封装 mybatis plus 分页结果
     *
     * @param page
     * @param <T>
     * @return
     */
    //public static <T> PageResult<T> getPageResult(Page<T> page) {
    //    PageResult<T> pageResult = new PageResult<>();
    //    int totalPage = (int) (page.getTotal() % page.getSize() == 0 ? page.getTotal() / page.getSize() : page.getTotal() / page.getSize() + 1);
    //    pageResult.setTotalPage(totalPage);
    //    pageResult.setTotal(page.getTotal());
    //    pageResult.setpageNum((int) page.getCurrent());
    //    pageResult.setPageSize((int) page.getSize());
    //    pageResult.setDataList(page.getRecords());
    //    pageResult.setSize(page.getRecords().size());
    //    return pageResult;
    //}

}
