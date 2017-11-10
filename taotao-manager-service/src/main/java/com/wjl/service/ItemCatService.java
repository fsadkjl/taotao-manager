package com.wjl.service;

import java.util.List;

import com.wjl.pojo.TbItemCat;

public interface ItemCatService {
	List<TbItemCat> getItemCatList(Long parentId);
}
