package com.taotao.search.service.impl;

import com.taotao.commom.pojo.SearchItem;
import com.taotao.commom.pojo.TaotaoResult;
import com.taotao.search.mapper.ItemMapper;
import com.taotao.search.service.SearchItemService;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * 导入商品数据到索引库
 * Created by lee on 2017/3/16.
 */
@SuppressWarnings(value = "all")
@Service(value = "searchItemServiceImpl")
public class SearchItemServiceImpl implements SearchItemService {
    //注入solrService对象
    @Resource(name = "httpSolrServer")//单机版注入
    //@Resource(name = "cloudSolrServer")//集群版注入
    private SolrServer solrServer;

    //注入itemMapper对象
    @Autowired
    private ItemMapper itemMapper;

    @Override
    public TaotaoResult importAllItemToIndex() throws IOException, SolrServerException {
        //1.查询所有商品数据
        List<SearchItem> itemList = itemMapper.getItemList();
        //2.创建一个solrServer对象
        for (SearchItem SearchItem : itemList) {
            //3.创建一个solrInputDocument对象
            SolrInputDocument document = new SolrInputDocument();
            //4.为文档添加域
            document.addField("id", SearchItem.getId());
            document.addField("item_title",SearchItem.getTitle());
            document.addField("item_sell_point", SearchItem.getSell_point());
            document.addField("item_price", SearchItem.getPrice());
            document.addField("item_image", SearchItem.getImage());
            document.addField("item_category_name", SearchItem.getCategory_name());
            document.addField("item_desc", SearchItem.getItem_desc());
            //5.向索引库中添加文档
            solrServer.add(document);
        }
        //提交
        solrServer.commit();
        //6.返回TaotaoResult,前往applicationContext-service.xml中发布服务
        return TaotaoResult.ok();
    }
}
