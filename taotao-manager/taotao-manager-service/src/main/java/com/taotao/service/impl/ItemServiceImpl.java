package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.commom.pojo.EasyUIDataGridResult;
import com.taotao.commom.pojo.TaotaoResult;
import com.taotao.commom.utils.IDUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by lee on 2017/3/10.
 */
@SuppressWarnings(value = "all")
@Service(value = "itemServiceImpl")
public class ItemServiceImpl implements ItemService {
    //注入itemMapper
    @Autowired
    private TbItemMapper itemMapper;

    //注入itemDescMapper
    @Autowired
    private TbItemDescMapper itemDescMapper;


    /**
     * 展示商品
     * @param page
     * @param rows
     * @return
     */
    @Override
    public EasyUIDataGridResult getItemList(int page, int rows) {
        //分页处理
        PageHelper.startPage(page,rows);
        TbItemExample example = new TbItemExample();
        List<TbItem> list = itemMapper.selectByExample(example);
        //创建返回结果对象
        EasyUIDataGridResult easyUIDataGridResult = new EasyUIDataGridResult();
        easyUIDataGridResult.setRows(list);
        PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(list);
        easyUIDataGridResult.setTotal((int) pageInfo.getTotal());
        //返回结果
        return easyUIDataGridResult;
    }

    /**
     * 添加商品
     * @param tbItem
     * @param desc
     * @return
     */
    @Override
    public TaotaoResult addItem(TbItem tbItem, String desc) {
        //1、生成商品id
        long itemId = IDUtils.genItemId();
        //2、补全TbItem对象的属性
        tbItem.setId(itemId);
        //商品状态，1-正常，2-下架，3-删除
        tbItem.setStatus((byte) 1);
        Date date = new Date();
        tbItem.setCreated(date);
        tbItem.setUpdated(date);
        //3、向商品列表插入数据
        itemMapper.insert(tbItem);
        //4、创建一个tbItemDesc对象
        TbItemDesc tbItemDesc = new TbItemDesc();
        //5、补全tbItemDesc对象中的属性
        tbItemDesc.setItemId(itemId);
        tbItemDesc.setItemDesc(desc);
        tbItemDesc.setCreated(date);
        tbItemDesc.setUpdated(date);
        //6、向商品描述中插入数据
        itemDescMapper.insert(tbItemDesc);
        //7、返回到结果
        return TaotaoResult.ok();
    }
}
