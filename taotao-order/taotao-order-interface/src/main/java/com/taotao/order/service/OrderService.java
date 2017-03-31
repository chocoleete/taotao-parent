package com.taotao.order.service;

import com.taotao.commom.pojo.TaotaoResult;
import com.taotao.order.pojo.OrderInfo;

/**
 * Created by lee on 2017/3/31.
 */
@SuppressWarnings(value = "all")
public interface OrderService {
    /**
     * 生成订单
     * @param orderInfo
     * @return TaotaoResult
     */
    TaotaoResult createOrder(OrderInfo orderInfo);
}
