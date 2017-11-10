package com.wjl.service;

import com.wjl.common.pojo.ListResult;
import com.wjl.common.pojo.TaotaoResult;
import com.wjl.pojo.TbItem;
import com.wjl.pojo.TbItemDesc;

public interface ItemService {
	TbItem getItemById(Long id);
	TaotaoResult deleteItemByIds(String[] ids);
	TaotaoResult instockItemByIds(String[] ids);
	TaotaoResult reshelfItemByIds(String[] ids);
	ListResult getItemList(int pageNum,int pageSize);
	TaotaoResult addItem(TbItem tbItem,String desc,String itemParams) throws Exception;
	TbItemDesc getItemDescById(Long id);
	TaotaoResult updateItem(TbItem tbItem, String desc, String itemParams) throws Exception;
}
