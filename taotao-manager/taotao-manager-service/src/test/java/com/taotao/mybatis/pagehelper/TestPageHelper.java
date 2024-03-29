package com.taotao.mybatis.pagehelper;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by lee on 2017/3/10.
 */
@SuppressWarnings(value = "all")
public class TestPageHelper {
    @Test
    public void testPageHelper() {
        // 初始化spring容器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
        // 从容器中获取mapper代理对象
        TbItemMapper itemMapper = applicationContext.getBean(TbItemMapper.class);
        // 执行查询
        TbItemExample example = new TbItemExample();
        // 分页处理
        PageHelper.startPage(1, 30);
        List<TbItem> list = itemMapper.selectByExample(example);
        // 取分页信息
        System.out.println("结果集中的记录数：" + list.size());
        PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(list);
        System.out.println("总记录数：" + pageInfo.getTotal());
        System.out.println("总页面数："+pageInfo.getPages());

        TbContentCategoryMapper bean = applicationContext.getBean(TbContentCategoryMapper.class);
        TbContentCategoryExample tbContentCategoryExample = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = tbContentCategoryExample.createCriteria();
        criteria.andParentIdEqualTo(0L);
        List<TbContentCategory> tbContentCategories = bean.selectByExample(tbContentCategoryExample);
        for (TbContentCategory tbContentCategory : tbContentCategories) {
            System.out.printf(tbContentCategory.getName());
        }
    }
}
