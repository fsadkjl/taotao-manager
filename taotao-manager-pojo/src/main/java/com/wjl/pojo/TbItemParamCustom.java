package com.wjl.pojo;

import java.util.Date;

public class TbItemParamCustom extends TbItemParam{
	
	private TbItemCat tbItemCat;
	
	public TbItemParamCustom() {
		// TODO Auto-generated constructor stub
	}

	public TbItemParamCustom(Long id, Long itemCatId, Date created, Date updated, String paramData,
			TbItemCat tbItemCat) {
		super(id, itemCatId, created, updated, paramData);
		this.tbItemCat = tbItemCat;
	}

	public TbItemCat getTbItemCat() {
		return tbItemCat;
	}

	public void setTbItemCat(TbItemCat tbItemCat) {
		this.tbItemCat = tbItemCat;
	}
	
	
}
