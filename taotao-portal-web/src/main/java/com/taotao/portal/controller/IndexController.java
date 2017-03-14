package com.taotao.portal.controller;

import com.taotao.commom.utils.JsonUtils;
import com.taotao.content.service.ContentService;
import com.taotao.pojo.TbContent;
import com.taotao.portal.pojo.Ad1Node;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 商城首页展示处理
 * Created by lee on 2017/3/12.
 */
@SuppressWarnings(value = "all")
@Controller(value = "indexController")
public class IndexController {
    //注入contentService
    @Resource(name = "contentService")
    private ContentService contentService;

    @Value("${AD1_CONTENT_CID}")
    private Long AD1_CONTENT_CID;

    @Value("${AD1_WIDTH}")
    private Integer AD1_WIDTH;

    @Value("${AD1_WIDTHB}")
    private Integer AD1_WIDTHB;

    @Value("${AD1_HEIGHT}")
    private Integer AD1_HEIGHT;

    @Value("${AD1_HEIGHTB}")
    private Integer AD1_HEIGHTB;


    @RequestMapping(value = "/index")
    public String showIndex(Model model) {
        //分页内容id，从属性文件中取
        List<TbContent> contentList = contentService.getContentList(AD1_CONTENT_CID);
        //创建一个返回结果的list
        List<Ad1Node> ad1NodeList = new ArrayList<>();
        for (TbContent content : contentList) {
            Ad1Node ad1Node = new Ad1Node();
            ad1Node.setAlt(content.getSubTitle());
            ad1Node.setHref(content.getUrl());
            ad1Node.setSrc(content.getPic());
            ad1Node.setSrcB(content.getPic2());
            ad1Node.setHeight(AD1_HEIGHT);
            ad1Node.setHeightB(AD1_HEIGHTB);
            ad1Node.setWidth(AD1_WIDTH);
            ad1Node.setWidthB(AD1_WIDTHB);
            //添加到ad1NodeList集合中
            ad1NodeList.add(ad1Node);
        }
        //转换成json数据
        String json = JsonUtils.objectToJson(ad1NodeList);
        //将数据传递给页面
        model.addAttribute("ad1", json);
        //根据内容分类id查询内容列表
        return "index";
    }
}
