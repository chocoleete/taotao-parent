package com.taotao.order.service.impl;

import com.taotao.commom.pojo.TaotaoResult;
import com.taotao.jedis.JedisClient;
import com.taotao.mapper.TbOrderItemMapper;
import com.taotao.mapper.TbOrderMapper;
import com.taotao.mapper.TbOrderShippingMapper;
import com.taotao.order.pojo.OrderInfo;
import com.taotao.order.service.OrderService;
import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbOrderShipping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 订单处理service
 * Created by lee on 2017/3/31.
 */
@SuppressWarnings(value = "all")
@Service(value = "orderServiceImpl")
public class OrderServiceImpl implements OrderService {
    //注入orderMapper
    @Autowired
    private TbOrderMapper orderMapper;

    //注入itemMapper
    @Autowired
    private TbOrderItemMapper orderItemMapper;

    //注入 orderShippingMapper
    @Autowired
    private TbOrderShippingMapper orderShippingMapper;

    //注入jedisClient
    @Resource(name = "jedisClientPool")
    private JedisClient jedisClient;

    //取值
    @Value(value = "${ORDER_GEN_KEY}")
    private String ORDER_GEN_KEY;

    @Value(value = "${ORDER_ID_INIT}")
    private String ORDER_ID_INIT;

    @Value(value = "${ORDER_DETAIL_GEN_KEY}")
    private String ORDER_DETAIL_GEN_KEY;

    @Override
    public TaotaoResult createOrder(OrderInfo orderInfo) {
        // 生成订单号 使用redis的incr生成,判断订单号是否存在
        if (!jedisClient.exists(ORDER_GEN_KEY)) {
            // 如果不存在，设置初始值
            jedisClient.set(ORDER_GEN_KEY, ORDER_ID_INIT);
        }
        String orderId = jedisClient.incr(ORDER_GEN_KEY).toString();
        // 向订单表插入数据订单号
        orderInfo.setOrderId(orderId);
        // 邮费
        orderInfo.setPostFee("0");
        // 状态：1、未付款，2、已付款，3、未发货，4、已发货、5、交易成功，6、交易关闭
        orderInfo.setStatus(1);
        Date date = new Date();
        // 创建时间
        orderInfo.setCreateTime(date);
        // 更新时间
        orderInfo.setUpdateTime(date);
        orderMapper.insert(orderInfo);
        // 向订单明细插入数据
        List<TbOrderItem> orderItems = orderInfo.getOrderItems();
        for (TbOrderItem orderItem : orderItems) {
            // 生成明细的主键
            Long orderDetailId = jedisClient.incr(ORDER_DETAIL_GEN_KEY);
            orderItem.setId(orderDetailId.toString());
            // 设置订单id
            orderItem.setOrderId(orderId);
            orderItemMapper.insert(orderItem);
        }
        // 向物流信息插入数据
        TbOrderShipping orderShipping = orderInfo.getOrderShipping();
        orderShipping.setOrderId(orderId);
        orderShipping.setCreated(date);
        orderShipping.setUpdated(date);
        orderShippingMapper.insert(orderShipping);
        // 返回订单号
        return TaotaoResult.ok(orderId);
    }
}
