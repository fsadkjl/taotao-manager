package com.wjl.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wjl.common.pojo.ListResult;
import com.wjl.common.pojo.TaotaoResult;
import com.wjl.common.util.ArrayToListUtils;
import com.wjl.common.util.IDUtils;
import com.wjl.common.util.JsonUtils;
import com.wjl.mapper.TbItemDescMapper;
import com.wjl.mapper.TbItemMapper;
import com.wjl.mapper.TbItemParamItemMapper;
import com.wjl.pojo.TbItem;
import com.wjl.pojo.TbItemDesc;
import com.wjl.pojo.TbItemDescExample;
import com.wjl.pojo.TbItemExample;
import com.wjl.pojo.TbItemExample.Criteria;
import com.wjl.pojo.TbItemParamItem;
import com.wjl.pojo.TbItemParamItemExample;
/**
 * 商品管理的service
 * @author wujiale
 * 2017-10-14 上午12:19:28
 */
@Service
public class ItemServiceImpl implements ItemService {
	private static final Logger log = LoggerFactory.getLogger(ItemServiceImpl.class);

	@Autowired
	private TbItemMapper tbItemMapper;
	@Autowired
	private TbItemDescMapper tbItemDescMapper;
	@Autowired
	private TbItemParamItemMapper tbItemParamItemMapper;
	
	/**
	 * 测试用
	 */
	@Override
	public TbItem getItemById(Long id) {
		TbItem tbItem = tbItemMapper.selectByPrimaryKey(id);
		return tbItem;
	}
	
	/**
	 * 获取商品列表
	 */
	@Override
	public ListResult getItemList(int pageNum, int pageSize) {
		log.info("进入到后台获取商品列表的系统中");
		TbItemExample example = new TbItemExample();
		PageHelper.startPage(pageNum, pageSize);
		List<TbItem> list = tbItemMapper.selectByExample(example );
		PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(list);
		long total = pageInfo.getTotal();
		ListResult listResult = new ListResult(total, list);
		log.info("总共查询到"+total+"条记录,值:"+JsonUtils.objectToJson(list));
		return listResult;
	}

	/**
	 * 添加商品信息
	 */
	@Override
	public TaotaoResult addItem(TbItem tbItem,String desc,String itemParams) throws Exception {
		log.info("进入到添加商品的系统中");
		Long id = IDUtils.getItemId();
		tbItem.setId(id);
		tbItem.setCreated(new Date());
		tbItem.setUpdated(new Date());
		tbItem.setStatus((byte) 1);
		log.info("商品图片路径:"+tbItem.getImage());
		int i = tbItemMapper.insert(tbItem);
		if (i <= 0) {
			log.error("添加商品基本信息失败");
			throw new Exception();
		}
		TaotaoResult taotaoResult = addItemDescription(desc, id);
		if (taotaoResult.getStatus() != 200) {
			log.error("添加商品描述失败");
			throw new Exception();
		}
		TaotaoResult taotaoResult2 = addItemParamData(itemParams, id);
		if (taotaoResult2.getStatus() != 200) {
			log.error("添加商品规格参数失败");
			throw new Exception();
		}
		log.info("添加商品成功");
		return TaotaoResult.ok();
	}
	
	/**
	 * 添加商品描述
	 * @param desc
	 * @param id
	 * @return
	 */
	private TaotaoResult addItemDescription(String desc, Long id) {
		TbItemDesc tbItemDesc = new TbItemDesc();
		tbItemDesc.setItemId(id);
		tbItemDesc.setItemDesc(desc);
		tbItemDesc.setCreated(new Date());
		tbItemDesc.setUpdated(new Date());
		int i = tbItemDescMapper.insert(tbItemDesc);
		if (i > 0) {
			return TaotaoResult.ok();
		}else{
			return null;
		}
	}
	
	/**
	 * 添加商品规格参数
	 * @param itemParams
	 * @param id
	 * @return
	 */
	private TaotaoResult addItemParamData(String itemParams,Long id) {
		TbItemParamItem record = new TbItemParamItem();
		record.setItemId(id);
		record.setParamData(itemParams);
		Date date = new Date();
		record.setCreated(date);
		record.setUpdated(date);
		int i = tbItemParamItemMapper.insert(record );
		if (i > 0) {
			return TaotaoResult.ok();
		}else{
			return null;
		}
	}
	
	/**
	 * 根据id删除商品
	 */
	@Override
	public TaotaoResult deleteItemByIds(String[] ids) {
		log.info("进入到删除商品的系统中");
		List<Long> list = ArrayToListUtils.arrayToList(ids);
		TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdIn(list);
		int i = tbItemMapper.deleteByExample(example);
		if (i > 0) {
			log.info("删除商品成功了");
			return TaotaoResult.ok();
		}
		return null;
	}

	/**
	 * 根据id下架商品
	 */
	@Override
	public TaotaoResult instockItemByIds(String[] ids) {
		List<Long> list = ArrayToListUtils.arrayToList(ids);
		TbItem tbItem = new TbItem();
		tbItem.setStatus((byte) 2);
		tbItem.setUpdated(new Date());
		TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdIn(list);
		int i = tbItemMapper.updateByExampleSelective(tbItem, example);
		if (i > 0) {
			return TaotaoResult.ok();
		}
		return null;
	}
	
	/**
	 * 根据id上架商品
	 */
	@Override
	public TaotaoResult reshelfItemByIds(String[] ids) {
		List<Long> list = ArrayToListUtils.arrayToList(ids);
		TbItem tbItem = new TbItem();
		tbItem.setStatus((byte) 1);
		tbItem.setUpdated(new Date());
		TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdIn(list);
		int i = tbItemMapper.updateByExampleSelective(tbItem, example);
		if (i > 0) {
			return TaotaoResult.ok();
		}
		return null;
	}

	/**
	 * 根据id获得商品描述
	 */
	@Override
	public TbItemDesc getItemDescById(Long id) {
		TbItemDescExample example = new TbItemDescExample();
		example.createCriteria().andItemIdEqualTo(id);
		List<TbItemDesc> list = tbItemDescMapper.selectByExampleWithBLOBs(example);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 更新商品信息(包括商品描述和商品规格参数)
	 * @throws Exception 
	 */
	@Override
	public TaotaoResult updateItem(TbItem tbItem, String desc, String itemParams) throws Exception {
		log.info("进入到更新商品的系统中");
		Date date = new Date();
		tbItem.setCreated(date);
		tbItem.setUpdated(date);
		tbItem.setStatus((byte) 1);
		log.info("商品图片路径:"+tbItem.getImage());
		int j = tbItemMapper.updateByPrimaryKey(tbItem);
		if (j <= 0) {
			log.error("更新商品基本信息失败");
			throw new Exception();
		}
		Long id = tbItem.getId();
		TaotaoResult taotaoResult = updateItemParamDataByItemId(id, itemParams);
		if (taotaoResult.getStatus() != 200) {
			log.error("更新商品描述失败");
			throw new Exception();
		}
		TaotaoResult taotaoResult2 = updateItemDescByItemId(id,desc);
		if (taotaoResult2.getStatus() != 200) {
			log.error("更新商品规格参数失败");
			throw new Exception();
		}
		log.info("更新商品成功");
		return TaotaoResult.ok();
	}
	
	/**
	 * 更新商品详细信息
	 * @param id
	 * @param itemParams
	 * @return
	 */
	private TaotaoResult updateItemDescByItemId(Long id, String desc) {
		TbItemDesc tbItemDesc = null;
		TbItemDescExample example = new TbItemDescExample();
		example.createCriteria().andItemIdEqualTo(id);
		List<TbItemDesc> list = tbItemDescMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			tbItemDesc = list.get(0);
		}
		Date date = new Date();
		tbItemDesc.setItemDesc(desc);
		tbItemDesc.setCreated(date);
		tbItemDesc.setUpdated(date);
		int i = tbItemDescMapper.updateByPrimaryKeyWithBLOBs(tbItemDesc);
		if (i > 0) {
			return TaotaoResult.ok();
		}
		return null;
	}

	/**
	 * 更新商品规格参数
	 * @param id
	 * @param itemParams
	 * @return
	 */
	private TaotaoResult updateItemParamDataByItemId(Long id,String itemParams) {
		TbItemParamItem tbItemParamItem = null;
		TbItemParamItemExample example = new TbItemParamItemExample();
		example.createCriteria().andItemIdEqualTo(id);
		List<TbItemParamItem> list = tbItemParamItemMapper.selectByExample(example );
		if (list != null && list.size() > 0) {
			tbItemParamItem = list.get(0);
		}
//		tbItemParamItem.setItemId(id);
		tbItemParamItem.setParamData(itemParams);
		Date date = new Date();
		tbItemParamItem.setCreated(date);
		tbItemParamItem.setUpdated(date);
		int i = tbItemParamItemMapper.updateByPrimaryKeyWithBLOBs(tbItemParamItem);
		if (i > 0) {
			return TaotaoResult.ok();
		}
		return null;
	}

	
}
