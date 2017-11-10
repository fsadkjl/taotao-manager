package com.wjl.service;

import com.wjl.common.pojo.ListResult;
import com.wjl.common.pojo.TaotaoResult;
import com.wjl.pojo.TbContent;

public interface ContentService {
	ListResult getContentList(Long categoryId, int pageNum,int pageSize);
	TaotaoResult updateContent(TbContent tbContent);
	TaotaoResult addContent(TbContent tbContent);
	TaotaoResult deleteContent(String[] ids);
}
