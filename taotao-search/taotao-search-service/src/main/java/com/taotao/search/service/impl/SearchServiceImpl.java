package com.taotao.search.service.impl;

import com.taotao.commom.pojo.SearchResult;
import com.taotao.search.dao.ItemSearchDao;
import com.taotao.search.service.SearchService;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 商品搜索服务实现类
 * Created by lee on 2017/3/18.
 */
@SuppressWarnings(value = "all")
@Service(value = "searchServiceImpl")
public class SearchServiceImpl implements SearchService {
    //注入itemSearchDao
    @Resource(name = "itemSearchDao")
    private ItemSearchDao itemSearchDao;

    @Override
    public SearchResult search(String queryString, Integer page, Integer rows) throws SolrServerException {
        //1、创建一个SolrQuery对象。
        SolrQuery solrQuery = new SolrQuery();
        //2、设置查询条件
        solrQuery.setQuery(queryString);
        //3、设置分页条件
        if (page < 1) page = 1;
        solrQuery.setStart((page - 1) * rows);
        solrQuery.setRows(rows);
        //4、需要指定默认搜索域。
        solrQuery.set("df", "item_title");
        //5、设置高亮
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("item_title");
        solrQuery.setHighlightSimplePre("<em style=\"color:red\">");
        solrQuery.setHighlightSimplePost("</em>");
        //6、执行查询，调用SearchDao。得到SearchResult
        SearchResult searchResult = itemSearchDao.search(solrQuery);
        //7、需要计算总页数。
        Long recordCount = searchResult.getRecordCount();
        long pageCount = recordCount / rows;
        if (recordCount % rows != 0) {
            recordCount++;
        }
        searchResult.setPageCount(recordCount);
        //8、返回SearchResult，在applicationContext-service.xml中发布服务
        return searchResult;
    }
}
