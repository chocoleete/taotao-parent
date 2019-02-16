package com.taotao.controller;

import com.taotao.commom.pojo.TaotaoResult;
import com.taotao.search.service.SearchItemService;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * 索引库维护
 * Created by lee on 2017/3/16.
 */
@SuppressWarnings(value = "all")
@Controller(value = "indexManagerController")
public class IndexManagerController {
    //注入searchItemService对象
    @Resource(name = "searchItemService")
    private SearchItemService searchItemService;

    /**
     * 页面请求的url:/index/import
     * @return TaotaoResult
     * @throws IOException
     * @throws SolrServerException
     */
    @RequestMapping(value = "/index/import")
    @ResponseBody
    public TaotaoResult indexImport() throws IOException, SolrServerException {
        TaotaoResult result = searchItemService.importAllItemToIndex();
        return result;
    }
}
