package com.taotao.content.service.impl;

import com.taotao.commom.pojo.EasyUITreeNode;
import com.taotao.commom.pojo.TaotaoResult;
import com.taotao.content.service.ContentCategoryService;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lee on 2017/3/12.
 */
@SuppressWarnings(value = "all")
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
    //注入contentCategoryMapper
    @Autowired
    private TbContentCategoryMapper contentCategoryMapper;

    /**
     * 参数列表：long parentId
     * 返回值类型：List<EasyUITreeNode>
     * @param parentId
     * @return
     */
    @Override
    public List<EasyUITreeNode> getContentCategoryList(long parentId) {
        //根据parentId查询子节点列表
        TbContentCategoryExample example = new TbContentCategoryExample();
        //设置查询条件
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
        List<EasyUITreeNode> resultList = new ArrayList<>();
        //遍历list集合，将需要返回的参数放到easyUITreeNode对象中
        for (TbContentCategory tbContentCategory : list) {
            EasyUITreeNode easyUITreeNode = new EasyUITreeNode();
            easyUITreeNode.setId(tbContentCategory.getId());
            easyUITreeNode.setText(tbContentCategory.getName());
            easyUITreeNode.setState(tbContentCategory.getIsParent() ? "closed" : "open");
            //将easyUITreeNode对象添加到结果集
            resultList.add(easyUITreeNode);
        }
        //返回结果，去applicationContext-service中提供服务
        return resultList;
    }

    /**
     * 参数列表：long parentId, String name
     * 返回值类型：TaotaoResult
     * @param parentId
     * @param name
     * @return
     */
    @Override
    public TaotaoResult addContentCategory(long parentId, String name) {
        /*
        * 1、接收两个参数
        * 2、向tb_content_category表中插入数据
        * 2.1、创建一个tbContentCategory对象
        * */
        TbContentCategory tbContentCategory = new TbContentCategory();
        // 2.2、补全tbContentCategory属性
        tbContentCategory.setParentId(parentId);
        tbContentCategory.setName(name);
        // 新增节点一般都不是父节点
        tbContentCategory.setIsParent(false);
        // 排列序号，表示同级类的展示次序，如数值相等则按名称次序排列。取值范围：大于0的整数
        tbContentCategory.setSortOrder(1);
        // 状态：可选值：1（正常），2（删除）
        tbContentCategory.setStatus(1);
        Date date = new Date();
        tbContentCategory.setCreated(date);
        tbContentCategory.setUpdated(date);
        // 向tb_content_category表中插入数据
        contentCategoryMapper.insert(tbContentCategory);
        // 获取新增节点的父节点
        TbContentCategory parentNode = contentCategoryMapper.selectByPrimaryKey(parentId);
        if (!parentNode.getIsParent()) {
            // 3、判断父节点的isParent是否为true,不为true的改为true
            parentNode.setIsParent(true);
            // 更新节点
            contentCategoryMapper.updateByPrimaryKey(parentNode);
        }
        // 4、需要主键返回
        // 5、返回TaotaoResult,其中包装tbContentCategory对象,然后在applicationContext-service中发布服务
        return TaotaoResult.ok(tbContentCategory);
    }

    /**
     * 参数列表：long id, String name
     * 返回值类型：TaotaoResult
     * @param id
     * @param name
     * @return
     */
    @Override
    public TaotaoResult updateContentCategory(long id, String name) {
        // 1、根据id查询tb_content_category表得到一个tbContentCategory对象
        TbContentCategory tbContentCategory = contentCategoryMapper.selectByPrimaryKey(id);
        // 2、更新tbContentCategory属性
        tbContentCategory.setName(name);
        // 3、向tb_content_category表中更新数据
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        contentCategoryMapper.updateByExample(tbContentCategory,example);
        //4、返回TaotaoResult,其中包装tbContentCategory对象,然后在applicationContext-service中发布服务
        return TaotaoResult.ok(tbContentCategory);
    }

    /**
     * 参数列表：long parentId, long id
     * 返回值类型：void
     * @param parentId
     * @param id
     * @return
     */
    @Override
    public void deleteContentCategory(long parentId, long id) {
        // 1、根据id查询tb_content_category表得到一个tbContentCategory对象
        TbContentCategory tbContentCategory = contentCategoryMapper.selectByPrimaryKey(id);
        // 2、判断tbContentCategory对象是否是父节点，如果是父节点，则id变为parentId进行递归删除
        if (tbContentCategory.getIsParent()) {
            TbContentCategoryExample example = new TbContentCategoryExample();
            TbContentCategoryExample.Criteria criteria = example.createCriteria();
            criteria.andParentIdEqualTo(id);
            // 查询该节点下的子节点
            List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
            if (list.size() > 0) {
                for (TbContentCategory contentCategory : list) {
                    // 递归调用，将id作为第一个参数，子节点的id作为第二个参数
                    deleteContentCategory(id, contentCategory.getId());
                }
            }
            contentCategoryMapper.deleteByPrimaryKey(id);
        }else {
            // 不是父节点，直接删除,在applicationContext-service中发布服务
            contentCategoryMapper.deleteByPrimaryKey(id);
        }
        // 判断父节点下是否还有节点，如果没有，将父节点设置为叶子节点
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory> tbContentCategories = contentCategoryMapper.selectByExample(example);
        TbContentCategory parentNode = contentCategoryMapper.selectByPrimaryKey(parentId);
        // 将父节点设置成子节点
        parentNode.setIsParent((tbContentCategories.size() > 0) ? true : false);
        // 更新到表中
        TbContentCategoryExample example1 = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria1 = example1.createCriteria();
        criteria1.andIdEqualTo(parentId);
        contentCategoryMapper.updateByExample(parentNode, example1);
    }
}
