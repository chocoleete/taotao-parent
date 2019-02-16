package com.taotao.controller;

import com.taotao.commom.pojo.EasyUIDataGridResult;
import com.taotao.commom.pojo.TaotaoResult;
import com.taotao.content.service.ContentService;
import com.taotao.pojo.TbContent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by lee on 2017/3/13.
 */
@SuppressWarnings(value = "all")
@Controller(value = "contentController")
public class ContentController {
    //注入contentService
    @Resource(name = "contentService")
    private ContentService contentService;

    /**
     * 页面请求：/content/save
     * 参数列表：TbContent content
     */
    @RequestMapping(value = "/content/save")
    @ResponseBody
    public TaotaoResult addContent(TbContent content) {
        TaotaoResult result = contentService.addContent(content);
        return result;
    }

    /**
     * 展示内容 url:/content/query/list
     *
     * @param page
     * @param rows
     * @return EasyUIDataGridResult
     */
    @RequestMapping(value = "/content/query/list")
    @ResponseBody
    public EasyUIDataGridResult showContentList(long categoryId, Integer page, Integer rows) {
        EasyUIDataGridResult easyUIDataGridResult = contentService.showContentList(categoryId, page, rows);
        return easyUIDataGridResult;
    }

    /**
     * 修改内容列表 url:/rest/content/edit
     * @param tbContent
     * @return TaotaoResult
     */
    @RequestMapping(value = "/rest/content/edit")
    @ResponseBody
    public TaotaoResult editContent(TbContent tbContent) {
        TaotaoResult result = contentService.editContent(tbContent);
        return result;
    }

    /**
     * 删除选中的内容 url:/content/delete
     * @param ids
     * @return
     */
    @RequestMapping(value = "/content/delete")
    @ResponseBody
    public TaotaoResult deleteContentByIds(long ids) {
        contentService.deleteContentByIds(ids);
        return TaotaoResult.ok();
    }
}
