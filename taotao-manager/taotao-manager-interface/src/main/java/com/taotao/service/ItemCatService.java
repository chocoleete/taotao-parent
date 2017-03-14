package com.taotao.service;

import com.taotao.commom.pojo.EasyUITreeNode;

import java.util.List;

/**
 * Created by lee on 2017/3/11.
 */
@SuppressWarnings(value = "all")
public interface ItemCatService {
    List<EasyUITreeNode> getItemCatList(long parentId);
}
