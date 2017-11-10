package com.wjl.service;

import java.util.List;

import com.wjl.common.pojo.TaotaoResult;
import com.wjl.common.pojo.TreeNode;

public interface ContentCategoryService {
	List<TreeNode> getCategoryList(Long pid);
	TaotaoResult addContentCategory(Long pid,String name);
	TaotaoResult updateContentCategory(Long id,String name);
	TaotaoResult deleteCatgoryAndChildNode(Long id);
}
