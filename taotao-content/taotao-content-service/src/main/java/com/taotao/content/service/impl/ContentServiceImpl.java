package com.taotao.content.service.impl;

import com.taotao.commom.pojo.TaotaoResult;
import com.taotao.commom.utils.JsonUtils;
import com.taotao.content.jedis.JedisClient;
import com.taotao.content.service.ContentService;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    //注入jedisClient
    @Resource(name = "jedisClientPool")
    private JedisClient jedisClient;

    //取resources中的值
    @Value(value = "${CONTENT_KEY}")
    private String CONTENT_KEY;

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

        //缓存同步，清除redis中categoryId对应的缓存信息
        jedisClient.hdel(CONTENT_KEY, content.getCategoryId().toString());

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
        //先查询缓存
        try {
            String json = jedisClient.hget(CONTENT_KEY, categoryId + "");
            //判断是否命中缓存
            if (StringUtils.isNotBlank(json)) {
                //将json转换成list
                List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //根据categoryId查询数据库
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        List<TbContent> list = contentMapper.selectByExample(example);

        //向缓存中保存结果
        try {
            jedisClient.hset(CONTENT_KEY, categoryId + "", JsonUtils.objectToJson(list));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //返回结果，然后去aplicationContext-service.xml发布服务
        return list;
    }
}
