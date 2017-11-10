package com.wjl.service;

import com.wjl.common.pojo.ListResult;
import com.wjl.common.pojo.TaotaoResult;
import com.wjl.pojo.TbItemParam;

public interface ItemParamService {
	ListResult getItemParamList(Integer pageNum, Integer pageSize);
	TaotaoResult getItemParamByCid(Long cid);
	TaotaoResult deleteItemParam(String[] ids);
	TaotaoResult addItemParamData(TbItemParam tbItemParam);
}
