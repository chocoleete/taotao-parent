package com.taotao.service.impl;

import com.taotao.commom.pojo.EasyUITreeNode;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 2017/3/11.
 */
@SuppressWarnings(value = "all")
@Service
public class ItemCatServiceImpl implements ItemCatService {
    //注入itemCatMapper
    @Autowired
    private TbItemCatMapper itemCatMapper;
    @Override
    public List<EasyUITreeNode> getItemCatList(long parentId) {
        //1.根据parentId查询节点列表
        //1.1设置查询条件
        TbItemCatExample example = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = example.createCriteria();
        //1.2设置父节点
        criteria.andParentIdEqualTo(parentId);
        List<TbItemCat> list = itemCatMapper.selectByExample(example);
        //2.转换成EasyUITreeNode列表
        ArrayList<EasyUITreeNode> resultList = new ArrayList<EasyUITreeNode>();
        //2.1遍历list
        for (TbItemCat tbItemCat : list) {
            EasyUITreeNode easyUITreeNode = new EasyUITreeNode();
            easyUITreeNode.setId(tbItemCat.getId());
            easyUITreeNode.setText(tbItemCat.getName());
            //2.2 判断是否为父节点
            easyUITreeNode.setState(tbItemCat.getIsParent() ? "closed" : "open");
            //3.添加到列表
            resultList.add(easyUITreeNode);
        }
        return resultList;
    }
}
