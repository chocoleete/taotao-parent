package com.taotao.content.service;

import com.taotao.commom.pojo.EasyUITreeNode;
import com.taotao.commom.pojo.TaotaoResult;

import java.util.List;

/**
 * Created by lee on 2017/3/12.
 */
@SuppressWarnings(value = "all")
public interface ContentCategoryService {
    /**
     * 参数列表：long parentId
     * 返回值：List<EasyUITreeNode>
     * @param parentId
     * @return
     */
    List<EasyUITreeNode> getContentCategoryList(long parentId);

    /**
     * 参数列表：long parentId, String name
     * 返回值类型：TaotaoResult
     * @param parentId
     * @param name
     * @return
     */
    TaotaoResult addContentCategory(long parentId, String name);

    /**
     * 参数列表：long id, String name
     * 返回值类型：TaotaoResult
     * @param id
     * @param name
     * @return
     */
    TaotaoResult updateContentCategory(long id, String name);

    /**
     * 参数列表：long parentId, long id
     * 返回值类型：void
     * @param parentId
     * @param id
     * @return
     */
    void deleteContentCategory(long parentId, long id);
}
