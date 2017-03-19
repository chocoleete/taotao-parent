package com.taotao.search.service;

import com.taotao.commom.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrServerException;

/**
 * 商品探索服务
 * Created by lee on 2017/3/18.
 */
@SuppressWarnings(value = "all")
public interface SearchService {
    SearchResult search(String queryString, Integer page, Integer rows) throws SolrServerException;
}
