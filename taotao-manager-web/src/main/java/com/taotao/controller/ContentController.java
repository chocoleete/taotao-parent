package com.taotao.controller;

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
}
