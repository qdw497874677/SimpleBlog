package com.qdw.pojo;

import lombok.Data;

import java.util.List;

@Data
public class Pager<T> {
    //分页起始页
    private int page;
    //每页数量
    private int size;
    //返回的集合
    private List<T> rows;
    //总记录数
    private int total;
    private int totalNow;
    //是否为第一个
    private boolean first;
    //是否为最后一个
    private boolean last;
    public boolean isFirst() {
        return page == 1;
    }
    public boolean isLast() {
        return page*size >= total;
    }

    public int getTotalNow() {
        return rows.size();
    }
}
