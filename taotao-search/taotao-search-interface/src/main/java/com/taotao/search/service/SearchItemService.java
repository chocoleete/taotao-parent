package com.taotao.search.service;

import com.taotao.commom.pojo.TaotaoResult;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;

/**
 * 导入商品数据到索引库
 * Created by lee on 2017/3/16.
 */
@SuppressWarnings(value = "all")
public interface SearchItemService {
    /**
     * 导入所有的商品数据
     * @return TaotaoResult
     * @throws IOException
     * @throws SolrServerException
     */
    TaotaoResult importAllItemToIndex() throws IOException, SolrServerException;
}
