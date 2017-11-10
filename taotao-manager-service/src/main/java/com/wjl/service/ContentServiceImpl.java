package com.wjl.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wjl.common.pojo.ListResult;
import com.wjl.common.pojo.TaotaoResult;
import com.wjl.common.util.ArrayToListUtils;
import com.wjl.common.util.HttpClientUtil;
import com.wjl.common.util.JsonUtils;
import com.wjl.mapper.TbContentMapper;
import com.wjl.pojo.TbContent;
import com.wjl.pojo.TbContentExample;

@Service
public class ContentServiceImpl implements ContentService {
	private static final Logger log = LoggerFactory.getLogger(ContentServiceImpl.class);
	//图片地址 IMAGE_URL=http://192.168.203.132:8081
	@Value("${IMAGE_URL}")
	private String url;
	//rest服务地址 REST_BASE_URL=http://localhost:8081/rest
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	
	@Autowired
	private TbContentMapper tbContentMapper;

	@Override
	public ListResult getContentList(Long categoryId,int pageNum, int pageSize) {
		log.info("进入到后台获取内容列表的系统中");
		//PageHelper.startPage(pageNum, pageSize)必须要放到下一条执行语句之前才有用
		PageHelper.startPage(pageNum, pageSize);
		TbContentExample example = new TbContentExample();
		example.createCriteria().andCategoryIdEqualTo(categoryId);
		List<TbContent> list = tbContentMapper.selectByExampleWithBLOBs(example );
		PageInfo<TbContent> pageInfo = new PageInfo<TbContent>(list);
		Long total = pageInfo.getTotal();
		ListResult listResult = new ListResult(total, list);
		log.info("总共查询到"+total+"条记录,值:"+JsonUtils.objectToJson(list));
		return listResult;
	}

	@Override
	public TaotaoResult updateContent(TbContent tbContent) {
		log.info("进入到后台更新内容的系统中");
		Date date = new Date();
		tbContent.setCreated(date);
		tbContent.setUpdated(date);
		log.info("这是resource文件中的url:"+url);
		log.info("内容图片路径:"+tbContent.getPic());
		if (!(tbContent.getPic().contains(url))) {
			tbContent.setPic(url+tbContent.getPic());
			log.info("修改之后的内容图片路径:"+tbContent.getPic());
			tbContent.setPic2(url+tbContent.getPic2());
		}
		int i = tbContentMapper.updateByPrimaryKeyWithBLOBs(tbContent);
		if (i > 0) {
			log.info("修改内容成功了,准备同步大广告位的缓存");
			HttpClientUtil.doGet(REST_BASE_URL+"/syncBigAdCache/"+tbContent.getCategoryId());
			log.info("同步大广告位缓存的url:"+REST_BASE_URL+"/syncBigAdCache/"+tbContent.getCategoryId());
			return TaotaoResult.ok();
		}
		return null;
	}

	@Override
	public TaotaoResult addContent(TbContent tbContent) {
		log.info("进入到添加内容的系统中");
		Date date = new Date();
		tbContent.setCreated(date);
		tbContent.setUpdated(date);
		log.info("这是resource文件中的url:"+url);
		log.info("内容图片路径:"+tbContent.getPic());
		tbContent.setPic(url+tbContent.getPic());
		tbContent.setPic2(url+tbContent.getPic2());
		log.info("修改之后的内容图片路径:"+tbContent.getPic());
		int i = tbContentMapper.insert(tbContent);
		if (i > 0) {
			log.info("添加内容成功了,准备同步大广告位的缓存");
			HttpClientUtil.doGet(REST_BASE_URL+"/syncBigAdCache/"+tbContent.getCategoryId());
			log.info("同步大广告位缓存的url:"+REST_BASE_URL+"/syncBigAdCache/"+tbContent.getCategoryId());
			return TaotaoResult.ok();
		}
		return null;
	}

	@Override
	public TaotaoResult deleteContent(String[] ids) {
		log.info("进入到删除内容的系统中");
		TbContentExample example = new TbContentExample();
		example.createCriteria().andIdEqualTo(Long.valueOf(ids[0]));
		List<TbContent> list2 = tbContentMapper.selectByExample(example);
		List<Long> list = ArrayToListUtils.arrayToList(ids);
		example.clear();
		example.createCriteria().andIdIn(list);
		int i = tbContentMapper.deleteByExample(example);
		log.info("删除内容失败,不能同步大广告位的缓存");
		if (i > 0) {
			log.info("删除内容成功了,准备同步大广告位的缓存");
			HttpClientUtil.doGet(REST_BASE_URL+"/syncBigAdCache/"+list2.get(0).getCategoryId());
			log.info("同步大广告位缓存的url:"+REST_BASE_URL+"/syncBigAdCache/"+list2.get(0).getCategoryId());
			return TaotaoResult.ok();
		}
		return null;
	}

}
