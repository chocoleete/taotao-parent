package com.taotao.order.controller;

import com.taotao.commom.pojo.TaotaoResult;
import com.taotao.commom.utils.CookieUtils;
import com.taotao.commom.utils.JsonUtils;
import com.taotao.order.pojo.OrderInfo;
import com.taotao.order.service.OrderService;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbUser;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单确认Controller
 * Created by lee on 2017/3/30.
 */
@SuppressWarnings(value = "all")
@Controller(value = "orderController")
public class OrderController {
    //取配置文件中的值
    @Value(value = "${COOKIE_CART_KEY}")
    private String COOKIE_CART_KEY;

    //注入orderService
    @Resource(name = "orderService")
    private OrderService orderService;

    /**
     * 查看购物车列表
     * 页面请求url:/order/order-cart
     * @param request
     * @return String
     */
    @RequestMapping(value = "/order/order-cart", method = RequestMethod.GET)
    public String showOrderCart(HttpServletRequest request) {
        // 取购物车商品列表
        List<TbItem> cartList = getCartList(request);
        // 取用户id
        TbUser user = (TbUser) request.getAttribute("user");
        System.out.println("username=" + user.getUsername());
        // 根据用户ID查询收货地址列表，静态数据
        // 从数据库中查询支付方式列表
        // 传递给页面
        request.setAttribute("cartList", cartList);
        return "order-cart";
    }

    /**
     * 从cookie中取购物车列表
     * @param request
     * @return List<TbItem>
     */
    private List<TbItem> getCartList(HttpServletRequest request) {
        // 使用CookieUtils取购物车列表
        String json = CookieUtils.getCookieValue(request, COOKIE_CART_KEY, true);
        // 判断cookie中是否有值
        if (StringUtils.isBlank(json)) {
            // 没有值返回一个空list
            return new ArrayList<>();
        }
        // 将json转换成list对象
        List<TbItem> tbItems = JsonUtils.jsonToList(json, TbItem.class);
        return tbItems;
    }

    /**
     * 生成订单
     * @param orderInfo
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/order/create", method = RequestMethod.POST)
    public String createOrder(OrderInfo orderInfo, Model model, HttpServletRequest request) {
        // 取用户id
        TbUser user = (TbUser) request.getAttribute("user");
        orderInfo.setUserId(user.getId());
        orderInfo.setBuyerNick(user.getUsername());
        // 生成订单
        TaotaoResult result = orderService.createOrder(orderInfo);
        // 向页面取订单号并传递给页面
        String orderId = result.getData().toString();
        model.addAttribute("orderId", orderId);
        model.addAttribute("payment", orderInfo.getPayment());
        DateTime dateTime = new DateTime();
        dateTime = dateTime.plusDays(3);
        model.addAttribute("date", dateTime.toString("yyyy-MM-dd"));//预计送达时间是三天后
        return "success";
    }

}
