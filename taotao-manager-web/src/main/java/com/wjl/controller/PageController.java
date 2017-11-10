package com.wjl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面之间跳转的Controller
 * @author wujiale
 * 2017-10-17 下午5:47:36
 */
@Controller
public class PageController {
	
	
	/**
	 * 展示首页
	 * @return
	 */
	@RequestMapping("/")
	public String showIndex(){
		return "index";
	}
	
	/**
	 * 展示其他页面
	 * @param page
	 * @return
	 */
	@RequestMapping("/{page}")
	public String showPage(@PathVariable String page){
		return page;
	}
	
	/**
	 * 跳转到修改商品界面
	 * @param ids
	 * @return
	 */
//	@RequestMapping("/rest/page/item-edit")
//	public String editPage(){
//		return "item-edit";
//	}
	
	
}
