package com.wjl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wjl.common.pojo.ListResult;
import com.wjl.common.pojo.TaotaoResult;
import com.wjl.pojo.TbItem;
import com.wjl.pojo.TbItemDesc;
import com.wjl.service.ItemService;
/**
 * 商品管理的Controller
 * @author wujiale
 * 2017-10-14 上午12:26:47
 */
@Controller
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	/**
	 * 仅做测试用
	 * @param id
	 * @return
	 */
	@RequestMapping("/item/{id}")
	@ResponseBody
	public TbItem getItemById(@PathVariable Long id){
		TbItem item = itemService.getItemById(id);
		return item;
	}
	/**
	 * 根据id删除商品
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="/rest/item/delete",method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult deleteItemByIds(String[] ids){
		TaotaoResult taotaoResult = itemService.deleteItemByIds(ids);
		return taotaoResult;
	}
	
	/**
	 * 根据id下架商品
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="/rest/item/instock",method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult instockItemByIds(String[] ids){
		TaotaoResult taotaoResult = itemService.instockItemByIds(ids);
		return taotaoResult;
	}
	
	/**
	 * 根据id上架商品
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="/rest/item/reshelf",method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult reshelfItemByIds(String[] ids){
		TaotaoResult taotaoResult = itemService.reshelfItemByIds(ids);
		return taotaoResult;
	}
	
	/**
	 * 查询商品列表
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/item/list")
	@ResponseBody
	public ListResult getItemList(@RequestParam("page")int pageNum,@RequestParam(value="rows")int pageSize){
		ListResult result = itemService.getItemList(pageNum, pageSize);
		return result;
	}
	
	/**
	 * 添加商品信息
	 * @param tbItem
	 * @param desc
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/item/save",method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult addItem(TbItem tbItem,String desc ,String itemParams) throws Exception{
		TaotaoResult result = itemService.addItem(tbItem,desc,itemParams);
		return result;
	}
	
	/**
	 * 更新商品信息
	 * @throws Exception 
	 */
	@RequestMapping(value="/rest/item/update",method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult editItem(TbItem tbItem,String desc ,String itemParams) throws Exception{
		TaotaoResult result = itemService.updateItem(tbItem,desc,itemParams);
		return result;
	}
	
	/**
	 * 根据id获取商品描述信息
	 * @param id
	 * @return
	 */
	@RequestMapping("/rest/item/query/item/desc/{id}")
	@ResponseBody
	public TaotaoResult geItemDescById(@PathVariable Long id){
		TbItemDesc tbItemDesc = itemService.getItemDescById(id);
		return TaotaoResult.ok(tbItemDesc);
	}
	
}
