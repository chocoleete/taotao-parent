package com.taotao.controller;

import com.taotao.commom.pojo.EasyUITreeNode;
import com.taotao.commom.pojo.TaotaoResult;
import com.taotao.content.service.ContentCategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lee on 2017/3/12.
 */
@SuppressWarnings(value = "all")
@Controller(value = "contentCategoryController")
@RequestMapping(value = "/content/category")
public class ContentCategoryController {
    //注入contentCategoryService对象
    @Resource(name = "contentCategoryService")
    private ContentCategoryService contentCategoryService;

    /**
     * 页面内容分类管理
     * 页面请求的url:/content/category/list
     * 参数列表：Long parentId
     * 返回值类型：json数据
     * @param parentId
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public List<EasyUITreeNode> getContentCategoryList(@RequestParam(value = "id", defaultValue = "0") Long parentId) {
        List<EasyUITreeNode> list = contentCategoryService.getContentCategoryList(parentId);
        return list;
    }

    /**
     * 新增节点
     * 页面请求的url:/content/category/create
     * 参数列表：Long parentId,String name
     * 返回值类型 json数据
     */
    @RequestMapping(value = "/create")
    @ResponseBody
    public TaotaoResult createContCategory(Long parentId, String name) {
        TaotaoResult result = contentCategoryService.addContentCategory(parentId, name);
        return result;
    }

    /**
     * 更新节点
     * 页面请求url:/content/category/update
     * 参数列表:long id,String name
     * 返回值类型：json数据
     * @return
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public TaotaoResult updateContentCategory(long id,String name) {
        TaotaoResult taotaoResult = contentCategoryService.updateContentCategory(id, name);
        return taotaoResult;
    }

    /**
     * 删除节点
     * 页面请求url:/content/category/delete
     * 参数列表：long partentId,long id
     * 返回值类型:json数据 TaotaoResult
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public TaotaoResult deleteContentCategory(long parentId, long id) {
        contentCategoryService.deleteContentCategory(parentId, id);
        return TaotaoResult.ok();
    }
}
