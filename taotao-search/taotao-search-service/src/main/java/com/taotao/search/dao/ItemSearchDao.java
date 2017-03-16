package com.taotao.search.dao;

import com.taotao.commom.pojo.SearchItem;
import com.taotao.commom.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 商品搜索dao
 * Created by lee on 2017/3/16.
 */
@SuppressWarnings(value = "all")
@Repository(value = "itemSearchDao")
public class ItemSearchDao {
    //注入solrServer
    @Resource(name = "httpSolrServer")
    private SolrServer solrServer;

    /**
     * 返回值类型：SearchResult
     * 参数列表：SolrQuery solrQuery
     */
    public SearchResult search(SolrQuery solrQuery) throws SolrServerException {
        //根据Query对象查询索引库
        QueryResponse queryResponse = solrServer.query(solrQuery);
        //取查询结果
        SolrDocumentList solrDocumentList = queryResponse.getResults();
        SearchResult searchResult = new SearchResult();
        //将查询结果总记录数放到searchResult中
        searchResult.setRecordCount(solrDocumentList.getNumFound());
        //取结果集
        List<SearchItem> itemList = new ArrayList<>();
        for (SolrDocument solrDocument : solrDocumentList) {
            SearchItem searchItem = new SearchItem();
            searchItem.setId((Long) solrDocument.get("id"));
            //取高亮显示
            String itemTitle = null;
            Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
            List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
            if (list != null && list.size() > 0) {
                itemTitle = list.get(0);
            } else {
                itemTitle = (String) solrDocument.get("item_title");
            }
            searchItem.setTitle(itemTitle);
            //searchItem.setSell_point((String) solrDocument.get("item_sell_point"));
            searchItem.setPrice((Long) solrDocument.get("item_price"));
            searchItem.setImage((String) solrDocument.get("item_image"));
            searchItem.setCategory_name((String) solrDocument.get("item_category_name"));
            searchItem.setItem_desc((String) solrDocument.get("item_desc"));
            //添加到商品列表
            itemList.add(searchItem);
        }
        searchResult.setItemList(itemList);
        //返回结果
        return searchResult;
    }
}
