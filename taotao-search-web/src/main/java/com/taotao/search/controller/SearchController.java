package com.taotao.search.controller;

import com.taotao.commom.pojo.SearchResult;
import com.taotao.search.service.SearchService;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

/**
 * 商品搜索Controller
 * Created by lee on 2017/3/18.
 */
@SuppressWarnings(value = "all")
@Controller(value = "searchController")
public class SearchController {
    //注入searchService
    @Resource(name = "searchService")
    private SearchService searchService;
    //取resource.properties中配置的值
    @Value(value = "${ITEM_ROWS}")
    private Integer ITEM_ROWS;

    /**
     * 商品搜索
     *
     * @param queryString
     * @param page
     * @param model
     * @return String
     * @throws SolrServerException
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/search")
    public String searchItem(@RequestParam(value = "q") String queryString, @RequestParam(defaultValue = "1") Integer page, Model model) throws SolrServerException, UnsupportedEncodingException {
        queryString = new String(queryString.getBytes("iso8859-1"), "utf-8");
        // 调用服务搜索商品信息
        SearchResult searchResult = searchService.search(queryString, page, ITEM_ROWS);
        // 使用model向页面传递参数
        model.addAttribute("query", queryString);
        model.addAttribute("totalPages", searchResult.getPageCount());
        model.addAttribute("itemList", searchResult.getItemList());
        model.addAttribute("page", page);
        // 返回逻辑视图
        return "search";
    }
}
