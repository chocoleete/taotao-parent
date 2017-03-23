package com.taotao.search.listener;

import com.taotao.commom.pojo.SearchItem;
import com.taotao.search.mapper.ItemMapper;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.IOException;

/**
 * Created by lee on 2017/3/21.
 */
@SuppressWarnings(value = "all")
public class ItemAddListener implements MessageListener {
    //注入itemMapper
    @Autowired
    private ItemMapper itemMapper;

    //注入solrServer
    @Resource(name = "httpSolrServer")
    private SolrServer solrServer;

    @Override
    public void onMessage(Message message) {
        try {
            //从消息中取商品id
            TextMessage textMessage = (TextMessage) message;
            String strItemId = textMessage.getText();
            Long itemId = Long.parseLong(strItemId);
            System.out.println("看到这个表示监听器工作了");
            //根据商品ID查询商品消息
            SearchItem searchItem = itemMapper.getItemById(itemId);
            //把商品信息添加到索引库
            SolrInputDocument document = new SolrInputDocument();
            //为文档添加域
            document.addField("id", searchItem.getId());
            document.addField("item_title",searchItem.getTitle());
            document.addField("item_sell_point", searchItem.getSell_point());
            document.addField("item_price", searchItem.getPrice());
            document.addField("item_image", searchItem.getImage());
            document.addField("item_category_name", searchItem.getCategory_name());
            document.addField("item_desc", searchItem.getItem_desc());
            //5.向索引库中添加文档
            solrServer.add(document);
            solrServer.commit();
            //最后配置到spring容器中
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
    }
}
