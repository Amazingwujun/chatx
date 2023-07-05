package com.chatx.commons.entity;

import com.github.pagehelper.IPage;

import java.time.LocalDateTime;

/**
 * 公共请求参数, 包含
 * <ol>
 *     <li>pageNum: 分页页码</li>
 *     <li>pageSize: 分页大小</li>
 *     <li>startAt: 起始查询时间</li>
 *     <li>endAt: 结束查询时间</li>
 * </ol>
 *
 * @author Jun
 * @since 1.0.0
 */
public class CommonParams {
    //@formatter:off

    /** 分页页码 */
    private Integer pageNum = 1;

    /** 分页大小 */
    private Integer pageSize = 15;

    private LocalDateTime startAt;

    private LocalDateTime endAt;

    //@formatter:on

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public LocalDateTime getStartAt() {
        return startAt;
    }

    public void setStartAt(LocalDateTime startAt) {
        this.startAt = startAt;
    }

    public LocalDateTime getEndAt() {
        return endAt;
    }

    public void setEndAt(LocalDateTime endAt) {
        this.endAt = endAt;
    }

    public IPage toPage(){
        return toPage(null);
    }

    public IPage toPage(String orderBy){
        return new IPage() {
            @Override
            public Integer getPageNum() {
                return pageNum;
            }

            @Override
            public Integer getPageSize() {
                return pageSize;
            }

            @Override
            public String getOrderBy() {
                return orderBy;
            }
        };
    }

    @Override
    public String toString() {
        return "CommonParams{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", startAt=" + startAt +
                ", endAt=" + endAt +
                '}';
    }
}
