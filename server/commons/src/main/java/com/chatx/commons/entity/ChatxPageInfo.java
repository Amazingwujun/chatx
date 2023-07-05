package com.chatx.commons.entity;

import com.github.pagehelper.Page;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;

/**
 * 简化版分页对象
 *
 * @author Jun
 * @since 1.0.0
 */
public class ChatxPageInfo<T> {
    //@formatter:Off

    /** 分页大小 */
    private Integer pageSize;

    /** 分页页码 */
    private Integer pageNum;

    /** 记录条数 */
    private Long total;

    /** 结果 */
    private List<T> list;

    //@formatter:on

    private static final ChatxPageInfo<?> empty = new ChatxPageInfo<>();
    static {
        empty.list = Collections.emptyList();
        empty.total = 0L;
        empty.pageNum = 1;
        empty.pageSize = 15;
    }

    /**
     * 获取一个内容为空的对象
     */
    public static <T> ChatxPageInfo<T> empty(){
        //noinspection unchecked
        return (ChatxPageInfo<T>) empty;
    }

    /**
     * 由分页查询结果 {@link Page} 生成简化版 pageInfo
     *
     * @param page 入参，{@link Page}
     * @param <T>  元素类型
     * @see com.github.pagehelper.PageInfo 复杂版本
     */
    public static <T> ChatxPageInfo<T> of(Page<T> page) {
        return of(page, page.getResult());
    }

    /**
     * 由分页查询结果 {@link Page} 生成简化版 pageInfo
     *
     * @param page   入参，{@link Page}
     * @param result 结果对象集合
     * @param <T>    元素类型
     * @see com.github.pagehelper.PageInfo 复杂版本
     */
    public static <T> ChatxPageInfo<T> of(Page<?> page, List<T> result) {
        Assert.notNull(page, "page 不能为 null");
        ChatxPageInfo<T> chatxPageInfo = new ChatxPageInfo<>();
        chatxPageInfo.pageNum = page.getPageNum();
        chatxPageInfo.pageSize = page.getPageSize();
        chatxPageInfo.total = page.getTotal();
        chatxPageInfo.list = result;
        return chatxPageInfo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public Long getTotal() {
        return total;
    }

    public List<T> getList() {
        return list;
    }

    @Override
    public String toString() {
        return "ChatxPageInfo{" +
                "pageSize=" + pageSize +
                ", pageNum=" + pageNum +
                ", total=" + total +
                ", list=" + list +
                '}';
    }
}
