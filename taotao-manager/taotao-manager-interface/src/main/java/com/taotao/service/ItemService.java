package com.taotao.service;

import com.taotao.commom.pojo.EasyUIDataGridResult;
import com.taotao.commom.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;

/**
 * Created by lee on 2017/3/10.
 */
@SuppressWarnings(value = "all")
public interface ItemService {
    /**
     * 展示商品
     * @param page
     * @param rows
     * @return
     */
    EasyUIDataGridResult getItemList(int page, int rows);

    /**
     * 添加商品
     * @param tbItem
     * @param desc
     * @return
     */
    TaotaoResult addItem(TbItem tbItem, String desc);

    /**
     * 根据itemId查询item
     * @param itemId
     * @return
     */
    TbItem getItemById(Long itemId);

    /**
     * 根据itemId查询itemDesc
     * @param itemId
     * @return
     */
    TbItemDesc getItemDescById(Long itemId);
}
