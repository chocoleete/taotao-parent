package com.taotao.content.service;

import com.taotao.commom.pojo.EasyUIDataGridResult;
import com.taotao.commom.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

import java.util.List;

/**
 * Created by lee on 2017/3/12.
 */
@SuppressWarnings(value = "all")
public interface ContentService {
    /**
     * 参数列表：TbContent content
     * 返回值类型:TaotaoResult
     * @param content
     * @return TaotaoResult
     */
    TaotaoResult addContent(TbContent content);

    /**
     * 返回值类型：List<TbContent>
     * 参数列表：long categoryId
     */
    List<TbContent> getContentList(long categoryId);

    /**
     * 展示内容
     * @param categoryId
     * @param page
     * @param rows
     * @return EasyUIDataGridResult
     */
    EasyUIDataGridResult showContentList(long categoryId, Integer page, Integer rows);

    /**
     * 修改内容
     * @param content
     * @return TaotaoResult
     */
    TaotaoResult editContent(TbContent content);

    /**
     * 删除所选内容
     * @param ids
     */
    void deleteContentByIds(long ids);
}
