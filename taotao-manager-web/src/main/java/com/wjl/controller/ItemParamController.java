package com.wjl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wjl.common.pojo.ListResult;
import com.wjl.common.pojo.TaotaoResult;
import com.wjl.pojo.TbItemParam;
import com.wjl.service.ItemParamService;

@Controller
@RequestMapping("/item/param")
public class ItemParamController {
	
	@Autowired
	private ItemParamService itemParamService;
	
	@RequestMapping("/query/itemcatid/{itemCatId}")
	@ResponseBody
	public TaotaoResult getItemParamByCid(@PathVariable  Long itemCatId){
		TaotaoResult taotaoResult = itemParamService.getItemParamByCid(itemCatId);
		return taotaoResult;
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public ListResult getItemParamList(@RequestParam("page")Integer pageNum,@RequestParam(value="rows")Integer pageSize){
		ListResult listResult = itemParamService.getItemParamList(pageNum,pageSize);
		return listResult;
	}
	
	@RequestMapping("/save/{cid}")
	@ResponseBody
	public TaotaoResult addItemParam(@PathVariable Long cid,String paramData){
		TbItemParam tbItemParam = new TbItemParam();
		tbItemParam.setItemCatId(cid);
		tbItemParam.setParamData(paramData);
		TaotaoResult taotaoResult = itemParamService.addItemParamData(tbItemParam );
		return taotaoResult;
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public TaotaoResult deleteItemParam(String[] ids){
		TaotaoResult taotaoResult = itemParamService.deleteItemParam(ids);
		return taotaoResult;
	}
	
}
