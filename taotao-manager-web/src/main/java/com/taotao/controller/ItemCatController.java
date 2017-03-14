package com.taotao.controller;

import com.taotao.commom.pojo.EasyUITreeNode;
import com.taotao.service.ItemCatService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lee on 2017/3/11.
 */
@SuppressWarnings(value = "all")
@Controller(value = "itemCatController")
public class ItemCatController {
    //注入itemCatService
    @Resource(name = "itemCatService")
    private ItemCatService itemCatService;

    /**
     * 将查询到的节点显示到页面
     * @param parentId
     * @return
     */
    @RequestMapping(value = "/item/cat/list")
    @ResponseBody
    public List<EasyUITreeNode> getItemCatList(@RequestParam(value = "id",defaultValue = "0")Long parentId) {
        List<EasyUITreeNode> list = itemCatService.getItemCatList(parentId);
        return list;
    }
}
