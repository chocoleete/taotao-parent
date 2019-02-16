package com.taotao.item.listener;

import com.taotao.item.pojo.Item;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.service.ItemService;
import freemarker.core.ParseException;
import freemarker.template.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 监听商品添加事件，生成静态页面
 * Created by lee on 2017/3/23.
 */
@SuppressWarnings(value = "all")
public class HtmlGenListener implements MessageListener {
    //注入服务
    @Resource(name = "itemService")
    private ItemService itemService;

    //注入freeMarkerConfigurer
    @Resource(name = "freeMarkerConfigurer")
    private FreeMarkerConfigurer freeMarkerConfigurer;

    //取配置文件中的值
    @Value(value = "${HTML_OUT_PATH}")
    private String HTML_OUT_PATH;

    @Override
    public void onMessage(Message message) {
        try {
            // 1.创建MessageListener接口的实现类
            // 2.从messag中获取商品id
            TextMessage textMessage = (TextMessage) message;
            String strItemId = textMessage.getText();
            Long itemId = Long.parseLong(strItemId);
            // 3.根据id查询商品基本信息和商品描述信息
            TbItem tbItem = itemService.getItemById(itemId);
            Item item = new Item(tbItem);
            TbItemDesc tbItemDesc = itemService.getItemDescById(itemId);
            // 创建数据集
            Map data = new HashMap<>();
            data.put("item", item);
            data.put("itemDesc", tbItemDesc);
            // 4.创建商品详情页面模板
            Configuration configuration = freeMarkerConfigurer.getConfiguration();
            Template template = configuration.getTemplate("item.htm");
            // 5.指定商品详情页面的输出目录
            File fileOutPath = new File(HTML_OUT_PATH + itemId + ".html");
            FileWriter out = new FileWriter(fileOutPath);
            //6.生成静态页面
            template.process(data, out);
            //7.关闭流
            out.close();
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (TemplateNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedTemplateNameException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}
