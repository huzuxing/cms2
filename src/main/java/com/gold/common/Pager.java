package com.gold.common;

import java.util.List;

/**
 * Created by huzuxing on 2016/9/25.
 */
public class Pager<T> {

    private Integer prePage;

    private Integer nextPage;

    private Integer totalPage;

    private Integer totalCount;

    private Integer pageNo;

    private Integer pageSize;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    private List<T> list;

    public Pager() {

    }

    public Pager(Integer pageNo, Integer pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPrePage() {
        return null == pageNo || 1 >= pageNo ? 1 : pageNo - 1;
    }

    public Integer getNextPage() {
        return getTotalPage() == pageNo || getTotalPage() <= pageNo ? getTotalPage() : pageNo + 1;
    }

    public Integer getTotalPage() {
        return getTotalCount() % getPageSize() == 0 ? getTotalCount() / getPageSize() : getTotalCount() / getPageSize() + 1;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}
