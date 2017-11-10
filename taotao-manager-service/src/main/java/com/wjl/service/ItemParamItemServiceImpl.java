package com.wjl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wjl.mapper.TbItemParamItemMapper;
import com.wjl.pojo.TbItemParamItem;
import com.wjl.pojo.TbItemParamItemExample;

@Service
public class ItemParamItemServiceImpl implements ItemParamItemService{
	
	@Autowired
	private TbItemParamItemMapper tbItemParamItemMapper;
	
	/**
	 * 根据id获取商品编辑页面中回显的商品规格的参数
	 */
	@Override
	public TbItemParamItem getItemParamDataByItemId(Long id) {
		TbItemParamItemExample example = new TbItemParamItemExample();
		example.createCriteria().andItemIdEqualTo(id);
		List<TbItemParamItem> list = tbItemParamItemMapper.selectByExampleWithBLOBs(example);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

}
