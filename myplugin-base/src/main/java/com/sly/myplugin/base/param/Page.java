package com.sly.myplugin.base.param;

/**
 * 分页参数
 *
 * @author SLY
 * @date 2021/11/25
 */
public class Page {
    /**
     * 页数
     */
    private int pageNum = 1;
    /**
     * 分页大小
     */
    private int pageSize = 10;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStart() {
        return (pageNum - 1) * 10;
    }
}
