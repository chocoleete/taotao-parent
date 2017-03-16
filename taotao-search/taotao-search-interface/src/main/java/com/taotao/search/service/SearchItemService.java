package com.taotao.search.service;

import com.taotao.commom.pojo.TaotaoResult;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;

/**
 * Created by lee on 2017/3/16.
 */
@SuppressWarnings(value = "all")
public interface SearchItemService {
    /**
     * 返回值类型:TaotaoResult
     * 参数列表:无
     */
    TaotaoResult importAllItemToIndex() throws IOException, SolrServerException;
}
