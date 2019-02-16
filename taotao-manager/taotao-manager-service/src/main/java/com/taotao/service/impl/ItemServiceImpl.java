package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.commom.pojo.EasyUIDataGridResult;
import com.taotao.commom.pojo.TaotaoResult;
import com.taotao.commom.utils.IDUtils;
import com.taotao.commom.utils.JsonUtils;
import com.taotao.jedis.JedisClient;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.service.ItemService;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.Date;
import java.util.List;

/**
 * Created by lee on 2017/3/10.
 */
@SuppressWarnings(value = "all")
@Service(value = "itemServiceImpl")
public class ItemServiceImpl implements ItemService {
    //注入itemMapper
    @Autowired
    private TbItemMapper itemMapper;

    //注入itemDescMapper
    @Autowired
    private TbItemDescMapper itemDescMapper;

    //注入jmsTemplate
    @Resource(name = "jmsTemplate")
    private JmsTemplate jmsTemplate;

    //注入topicDestination
    @Resource(name = "topicDestination")
    private ActiveMQTopic topicDestination;

    //注入jedisClient
    @Resource(name = "jedisClientPool")
    private JedisClient jedisClient;

    //取值
    @Value(value = "${REDIS_ITEM_KEY}")
    private String REDIS_ITEM_KEY;

    @Value(value = "${REDIS_ITEM_EXPIRE}")
    private Integer REDIS_ITEM_EXPIRE;

    /**
     * 展示商品
     * @param page
     * @param rows
     * @return
     */
    @Override
    public EasyUIDataGridResult getItemList(int page, int rows) {
        // 分页处理
        PageHelper.startPage(page,rows);
        TbItemExample example = new TbItemExample();
        List<TbItem> list = itemMapper.selectByExample(example);
        // 创建返回结果对象
        EasyUIDataGridResult easyUIDataGridResult = new EasyUIDataGridResult();
        easyUIDataGridResult.setRows(list);
        PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(list);
        easyUIDataGridResult.setTotal((int) pageInfo.getTotal());
        // 返回结果
        return easyUIDataGridResult;
    }

    /**
     * 添加商品
     * @param tbItem
     * @param desc
     * @return
     */
    @Override
    public TaotaoResult addItem(TbItem tbItem, String desc) {
        // 1、生成商品id,匿名内部内中使用局部变量要用final修饰
        final long itemId = IDUtils.genItemId();
        // 2、补全TbItem对象的属性
        tbItem.setId(itemId);
        // 商品状态，1-正常，2-下架，3-删除
        tbItem.setStatus((byte) 1);
        Date date = new Date();
        tbItem.setCreated(date);
        tbItem.setUpdated(date);
        // 3、向商品列表插入数据
        itemMapper.insert(tbItem);
        // 4、创建一个tbItemDesc对象
        TbItemDesc tbItemDesc = new TbItemDesc();
        // 5、补全tbItemDesc对象中的属性
        tbItemDesc.setItemId(itemId);
        tbItemDesc.setItemDesc(desc);
        tbItemDesc.setCreated(date);
        tbItemDesc.setUpdated(date);
        // 6、向商品描述中插入数据
        itemDescMapper.insert(tbItemDesc);

        // 商品添加完成后发送activeMQ消息
        jmsTemplate.send(topicDestination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                // 创建一个消息对象
                TextMessage textMessage = session.createTextMessage(String.valueOf(itemId));
                return textMessage;
            }
        });

        // 7、返回到结果
        return TaotaoResult.ok();
    }

    /**
     * 返回值类型：TbItem
     * 参数列表：Long itemId
     * @param itemId
     * @return
     */
    @Override
    public TbItem getItemById(Long itemId) {
        //<--
        //先查询缓存
        try {
            String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":BASE");
            if (StringUtils.isNotBlank(json)) {
                TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
                return tbItem;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //-->
        TbItem item = itemMapper.selectByPrimaryKey(itemId);

        //<--
        //添加缓存
        try {
            jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":BASE", JsonUtils.objectToJson(item));
            //设置过期时间
            jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":BASE", REDIS_ITEM_EXPIRE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //-->
        return item;
    }

    /**
     * 返回值类型：TbItemDesc
     * 参数列表：Long itemId
     * @param itemId
     * @return
     */
    @Override
    public TbItemDesc getItemDescById(Long itemId) {
        //<--
        //先查询缓存
        try {
            String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":DESC");
            if (StringUtils.isNotBlank(json)) {
                TbItemDesc tbItemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
                return tbItemDesc;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //-->
        TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);
        //<--
        //添加缓存
        try {
            jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":DESC", JsonUtils.objectToJson(itemDesc));
            //设置过期时间
            jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":DESC", REDIS_ITEM_EXPIRE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //-->
        return itemDesc;
    }
}
