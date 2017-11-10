package com.wjl.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wjl.common.pojo.ListResult;
import com.wjl.common.pojo.TaotaoResult;
import com.wjl.common.util.ArrayToListUtils;
import com.wjl.mapper.TbItemParamCustomMapper;
import com.wjl.mapper.TbItemParamMapper;
import com.wjl.pojo.TbItemParam;
import com.wjl.pojo.TbItemParamCustom;
import com.wjl.pojo.TbItemParamExample;
import com.wjl.pojo.TbItemParamExample.Criteria;
/**
 * 查询规格参数
 * @author wujiale
 * 2017-10-19 上午12:24:07
 */
@Service
public class ItemParamServiceImpl implements ItemParamService{
	
	@Autowired
	private TbItemParamMapper tbItemParamMapper;
	@Autowired
	private TbItemParamCustomMapper tbItemParamCustomMapper;

	@Override
	public TaotaoResult getItemParamByCid(Long cid) {
		TbItemParamExample example = new TbItemParamExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemCatIdEqualTo(cid);
		List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(example);
		if (list != null && list.size() > 0) {
			return TaotaoResult.ok(list.get(0));
		}
		return TaotaoResult.ok();
	}

	@Override
	public ListResult getItemParamList(Integer pageNum,Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<TbItemParamCustom> list = tbItemParamCustomMapper.getItemparamList();
		PageInfo<TbItemParamCustom> pageInfo = new PageInfo<TbItemParamCustom>(list);
		Long total = pageInfo.getTotal();
		ListResult listResult = new ListResult(total,list);
		return listResult;
	}

	@Override
	public TaotaoResult deleteItemParam(String[] ids) {
		List<Long> list = ArrayToListUtils.arrayToList(ids);
		TbItemParamExample example = new TbItemParamExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdIn(list);
		int i = tbItemParamMapper.deleteByExample(example);
		if (i > 0) {
			return TaotaoResult.ok();
		}
		return null;
	}

	@Override
	public TaotaoResult addItemParamData(TbItemParam tbItemParam) {
		Date date = new Date();
		tbItemParam.setCreated(date);
		tbItemParam.setUpdated(date);
		int i = tbItemParamMapper.insert(tbItemParam);
		if (i > 0) {
			return TaotaoResult.ok();
		}
		return null;
	}
	

}
