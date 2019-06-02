package com.taotao.cart.controller;

import com.taotao.commom.pojo.TaotaoResult;
import com.taotao.commom.utils.CookieUtils;
import com.taotao.commom.utils.JsonUtils;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 购物车管理Controller
 * Created by lee on 2017/3/29.
 */
@SuppressWarnings(value = "all")
@Controller(value = "cartController")
public class CartController {
    //取值
    @Value(value = "${COOKIE_CART_KEY}")
    private String COOKIE_CART_KEY;

    @Value(value = "${COOKIE_CART_EXPIRE}")
    private Integer COOKIE_CART_EXPIRE;

    //注入ItemService
    @Resource(name = "itemService")
    private ItemService itemService;

    /**
     * 添加商品到购物车
     *
     * @param itemId   商品ID
     * @param num      商品数量
     * @param request  请求
     * @param response 响应
     * @return 页面
     */
    @RequestMapping(value = "/cart/add/{itemId}")
    public String addCart(@PathVariable(value = "itemId") Long itemId, Integer num,
                          HttpServletRequest request, HttpServletResponse response) {
        // 从cookie中查询购物车列表
        List<TbItem> cartList = getCartList(request);
        // 判断列表中是否有此商品
        boolean hasItem = false;
        for (TbItem tbItem : cartList) {
            if (tbItem.getId() == itemId.longValue()) {
                // 列表中存在此商品，数量相加
                tbItem.setNum(tbItem.getNum() + num);
                hasItem = true;
                break;
            }
        }
        if (!hasItem) {
            // 如果没有，根据商品ID查询商品信息，调用商品服务实现
            TbItem tbItem = itemService.getItemById(itemId);
            // 设置商品数量
            tbItem.setNum(num);
            // 取一张图片
            String image = tbItem.getImage();
            if (StringUtils.isNotBlank(image)) {
                tbItem.setImage(image.split(",")[0]);
            }
            // 添加到商品列表
            cartList.add(tbItem);
        }
        // 把购物车写入cookie
        CookieUtils.setCookie(request, response, COOKIE_CART_KEY,
                JsonUtils.objectToJson(cartList), COOKIE_CART_EXPIRE, true);
        // 返回添加成功页面
        return "cartSuccess";
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
     * 展示购物车商品列表
     * 页面请求url:/cart/cart
     *
     * @param request
     * @param model
     * @return String
     */
    @RequestMapping(value = "/cart/cart")
    public String showCartList(HttpServletRequest request, Model model) {
        //取购物车商品列表
        List<TbItem> cartList = getCartList(request);
        //传递给页面
        model.addAttribute("cartList", cartList);
        return "cart";
    }

    /**
     * 修改购物车商品数量
     * 页面请示url:/cart/update/num/{itemId}/{num}
     *
     * @param itemId 商品ID
     * @param num    数量
     * @return
     */
    @RequestMapping(value = "/cart/update/num/{itemId}/{num}")
    @ResponseBody
    public TaotaoResult updateNum(@PathVariable(value = "itemId") Long itemId,
                                  @PathVariable(value = "num") Integer num,
                                  HttpServletRequest request, HttpServletResponse response) {
        // 1.接收页面传来的参数
        // 2.取购物车中的商品列表
        List<TbItem> cartList = getCartList(request);
        // 3.遍历商品列表，找到对应的商品
        for (TbItem tbItem : cartList) {
            if (tbItem.getId() == itemId.longValue()) {
                // 4.将数据设置成页面传来的值
                tbItem.setNum(num);
                break;
            }
        }
        // 5.将商品列表写入cookie
        CookieUtils.setCookie(request, response, COOKIE_CART_KEY,
                JsonUtils.objectToJson(cartList), COOKIE_CART_EXPIRE, true);
        // 6.响应TaotaoResult Json数据
        return TaotaoResult.ok();
    }

    /**
     * 删除购物车中的商品
     *
     * @param itemId   商品ID
     * @param request  请求
     * @param response 响应
     * @return
     */
    @RequestMapping(value = "/cart/delete/{itemId}")
    public String deleteCartItem(@PathVariable(value = "itemId") Long itemId,
                                 HttpServletRequest request, HttpServletResponse response) {
        //获取从页面传来的商品id
        //从cookie中取购物车商品列表
        List<TbItem> cartList = getCartList(request);
        //遍历列表找到对应的商品
        for (TbItem tbItem : cartList) {
            if (tbItem.getId() == itemId.longValue()) {
                //删除商品
                cartList.remove(tbItem);
                break;
            }
        }
        //将商品列表写入cookie
        CookieUtils.setCookie(request, response, COOKIE_CART_KEY,
                JsonUtils.objectToJson(cartList), COOKIE_CART_EXPIRE, true);
        //返回逻辑视图，在逻辑视图中做redirect跳转
        return "redirect:/cart/cart.html";
    }
}
