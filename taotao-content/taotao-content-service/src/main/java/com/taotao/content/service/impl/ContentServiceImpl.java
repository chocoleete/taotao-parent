package com.taotao.content.service.impl;

import com.taotao.commom.pojo.TaotaoResult;
import com.taotao.content.service.ContentService;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by lee on 2017/3/12.
 */
@SuppressWarnings(value = "all")
@Service
public class ContentServiceImpl implements ContentService {
    //注入contentMapper
    @Autowired
    private TbContentMapper contentMapper;

    /**
     * 参数列表：TbContent content
     * 返回值类型：TaotaoResult
     * @param content
     * @return
     */
    @Override
    public TaotaoResult addContent(TbContent content) {
        //补全属性
        Date date = new Date();
        content.setCreated(date);
        content.setUpdated(date);
        //插入数据
        contentMapper.insert(content);
        //返回结果，然后去applicationContext-service.xml发布服务
        return TaotaoResult.ok();
    }

    /**
     * 参数列表：long categoryId
     * 返回值类型：List<TbContent>
     * @param categoryId
     * @return
     */
    @Override
    public List<TbContent> getContentList(long categoryId) {
        //根据categoryId查询数据库
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        List<TbContent> list = contentMapper.selectByExample(example);
        //返回结果，然后去aplicationContext-service.xml发布服务
        return list;
    }
}
