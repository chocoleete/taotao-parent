package com.taotao.commom.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lee on 2017/3/16.
 */
@SuppressWarnings(value = "all")
public class SearchResult implements Serializable {
    private List<SearchItem> itemList;
    private Long recordCount;
    private Long pageCount;

    public List<SearchItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<SearchItem> itemList) {
        this.itemList = itemList;
    }

    public Long getPageCount() {
        return pageCount;
    }

    public void setPageCount(Long pageCount) {
        this.pageCount = pageCount;
    }

    public Long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(Long recordCount) {
        this.recordCount = recordCount;
    }
}
