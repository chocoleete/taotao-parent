package com.taotao.controller;

import com.taotao.commom.pojo.EasyUIDataGridResult;
import com.taotao.commom.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 商品管理Controller
 * Created by lee on 2017/3/10.
 */
@SuppressWarnings(value = "all")
@Controller(value = "itemController")
public class ItemController {
    //注入itemService
    @Resource(name = "itemService")
    private ItemService itemService;
    /**
     * 实现商品列表的查询
     */
    @RequestMapping(value = "/item/list")
    @ResponseBody
    public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
        EasyUIDataGridResult result = itemService.getItemList(page, rows);
        return result;
    }

    @RequestMapping(value = "/item/save")
    @ResponseBody
    public TaotaoResult addItem(TbItem tbItem, String desc) {
        TaotaoResult result = itemService.addItem(tbItem, desc);
        return result;
    }
}
