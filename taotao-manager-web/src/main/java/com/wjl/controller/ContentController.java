package com.wjl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wjl.common.pojo.ListResult;
import com.wjl.common.pojo.TaotaoResult;
import com.wjl.pojo.TbContent;
import com.wjl.service.ContentService;

@Controller
public class ContentController {
	
	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/content/query/list")
	@ResponseBody
	public ListResult getContentList(Long categoryId,@RequestParam("page")int pageNum,@RequestParam(value="rows")int pageSize){
		ListResult result = contentService.getContentList(categoryId , pageNum, pageSize);
		return result;
	}
	
	//http://localhost:8080/rest/content/edit 404 (Not Found)
	@RequestMapping(value="/rest/content/edit",method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult editContent(TbContent tbContent) throws Exception{
		TaotaoResult result = contentService.updateContent(tbContent);
		return result;
	}
	
	//http://localhost:8080/content/save 404 (Not Found)
	@RequestMapping(value="/content/save",method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult addContent(TbContent tbContent){
		TaotaoResult taotaoResult = contentService.addContent(tbContent);
		return taotaoResult;
	}
	
	@RequestMapping(value="/content/delete",method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult deleteContent(String[] ids){
		TaotaoResult result = contentService.deleteContent(ids);
		return result;
	}
	
	
	
}
