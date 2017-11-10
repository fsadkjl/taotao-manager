package com.wjl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wjl.common.pojo.TaotaoResult;
import com.wjl.pojo.TbItemParamItem;
import com.wjl.service.ItemParamItemService;

@Controller
public class ItemParamItemController {
	@Autowired
	private ItemParamItemService itemParamItemService;

	/**
	 * 根据id获取商品编辑页面中回显的商品规格的参数
	 */
	@RequestMapping("/rest/item/param/item/query/{id}")
	@ResponseBody
	public TaotaoResult getItemParamDataById(@PathVariable Long id){
		TbItemParamItem tbItemParamItem = itemParamItemService.getItemParamDataByItemId(id);
		return TaotaoResult.ok(tbItemParamItem);
	}
	
}
