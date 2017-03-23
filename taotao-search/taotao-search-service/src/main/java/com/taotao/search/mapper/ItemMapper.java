package com.taotao.search.mapper;

import com.taotao.commom.pojo.SearchItem;

import java.util.List;

/**
 * Created by lee on 2017/3/16.
 */
@SuppressWarnings(value = "al")
public interface ItemMapper {
    /**
     * 参数列表：无
     * 返回值类：List<SearchItem>
     */
    List<SearchItem> getItemList();

    /**
     * 参数列表：Long itemId
     * 返回值类型：SearchItem
     * @param itemId
     * @return
     */
    SearchItem getItemById(Long itemId);
}
