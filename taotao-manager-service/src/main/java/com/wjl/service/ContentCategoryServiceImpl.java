package com.wjl.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wjl.common.pojo.TaotaoResult;
import com.wjl.common.pojo.TreeNode;
import com.wjl.mapper.TbContentCategoryMapper;
import com.wjl.pojo.TbContentCategory;
import com.wjl.pojo.TbContentCategoryExample;
import com.wjl.pojo.TbContentCategoryExample.Criteria;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService{

	@Autowired
	private TbContentCategoryMapper tbContentCategoryMapper;
	
	@Override
	public List<TreeNode> getCategoryList(Long parentId) {
		//根据parentid查询节点列表
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		//执行查询
		List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
		List<TreeNode> resultList = new ArrayList<>();
		for (TbContentCategory tbContentCategory : list) {
			//创建一个节点
			TreeNode node = new TreeNode();
			node.setId(tbContentCategory.getId());
			node.setText(tbContentCategory.getName());
			node.setState(tbContentCategory.getIsParent()?"closed":"open");
			resultList.add(node);
		}
		return resultList;
	}

	@Override
	public TaotaoResult addContentCategory(Long pid, String name) {
		//创建一个pojo
		TbContentCategory contentCategory = new TbContentCategory();
		contentCategory.setName(name);
		contentCategory.setIsParent(false);
		//'状态。可选值:1(正常),2(删除)',
		contentCategory.setStatus(1);
		contentCategory.setParentId(pid);
		contentCategory.setSortOrder(1);
		contentCategory.setCreated(new Date());
		contentCategory.setUpdated(new Date());
		//添加记录
		tbContentCategoryMapper.insert(contentCategory);
		//查看父节点的isParent列是否为true，如果不是true改成true
		TbContentCategory parentCat = tbContentCategoryMapper.selectByPrimaryKey(pid);
		//判断是否为true
		if(!parentCat.getIsParent()) {
			parentCat.setIsParent(true);
			//更新父节点
			tbContentCategoryMapper.updateByPrimaryKey(parentCat);
		}
		//返回结果
		return TaotaoResult.ok(contentCategory);
	}

	@Override
	public TaotaoResult updateContentCategory(Long id, String name) {
		TbContentCategory record = new TbContentCategory();
		record.setId(id);
		record.setName(name);
		int i = tbContentCategoryMapper.updateByPrimaryKeySelective(record );
		if (i > 0) {
			return TaotaoResult.ok();
		}
		return null;
	}

	
	public TaotaoResult deleteCatgoryAndChildNode (Long id) {
		// 获取要删除的Category
		TbContentCategory tbContentCategory = tbContentCategoryMapper.selectByPrimaryKey(id) ;
		//判断是否为父节点
		if (tbContentCategory.getIsParent( )) {
		//获得所有该节点下的孩子节点
		List<TbContentCategory> list = getChildNodeList(id);
		//删除所有孩子节点
		for(TbContentCategory category : list) {
			deleteCatgoryAndChildNode(category.getId()) ;
			}
		}
		//判断父节点下是否还有具他于节点
		if (getChildNodeList (tbContentCategory.getParentId () ).size( )==1) {
		//没有则将父节点标记为叶子节点
			TbContentCategory parentCategory = tbContentCategoryMapper.selectByPrimaryKey (tbContentCategory.getParentId ()) ;
			parentCategory.setIsParent( false) ;
			tbContentCategoryMapper.updateByPrimaryKey(parentCategory) ;
		}
		//删际本下点
		tbContentCategoryMapper.deleteByPrimaryKey(id) ;
		return TaotaoResult.ok();
	}
		
		/**
		获得所有该节点下的孩子节点
		*
		*/
	private List<TbContentCategory> getChildNodeList( Long id) {
		//查询所有父节点为传入id的节点
		TbContentCategoryExample example = new TbContentCategoryExample() ;
		example.createCriteria().andParentIdEqualTo(id) ;
		//返回所有符合要求的节点
		return tbContentCategoryMapper.selectByExample(example) ;
	}
}
