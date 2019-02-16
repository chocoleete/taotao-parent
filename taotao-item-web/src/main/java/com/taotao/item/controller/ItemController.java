package com.taotao.item.controller;

import com.taotao.item.pojo.Item;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by lee on 2017/3/22.
 */
@SuppressWarnings(value = "all")
@Controller(value = "itemController")
public class ItemController {
    //注入itemService
    @Resource(name = "itemService")
    private ItemService itemService;

    /**
     * 页面请求url:/item/{itemId}
     * 参数列表:Long itemId,Model model
     *
     * @param itemId
     * @param model
     * @return
     */
    @RequestMapping(value = "/item/{itemId}")
    public String showItemInfo(@PathVariable(value = "itemId") Long itemId, Model model) {
        //根据商品Id查询商品基本信息
        TbItem tbItem = itemService.getItemById(itemId);
        //使用tbItem初始化item对象
        Item item = new Item(tbItem);
        //根据商品Id查询商品描述信息
        TbItemDesc tbItemDesc = itemService.getItemDescById(itemId);
        //传递给页面
        model.addAttribute("item", item);
        model.addAttribute("itemDesc", tbItemDesc);
        //返回逻辑视图
        return "item";
    }
}
