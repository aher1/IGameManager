package com.igame.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.igame.app.entity.GoodsEntity;
import com.igame.app.entity.GoodsTypeEntity;
import com.igame.app.mapper.GoodsMapper;
import com.igame.app.mapper.GoodsTypeMapper;

@Service
public class GoodsTypeService {
	@Autowired
	private GoodsTypeMapper goodsTypeMapper;
	@Autowired
	private GoodsMapper goodsMapper;

	public List<GoodsTypeEntity> getGoodsType(long appid) {
		return goodsTypeMapper.listGoodsType(appid);
	}

	public List<GoodsEntity> getGoodsByType(long appid, int type,int pageNum,int pageCount) {
		List<Long> ids = goodsTypeMapper.listGoodsByType(appid, type);
		List<GoodsEntity> entities = null;
		if (ids != null && !ids.isEmpty()) {
			if(ids.size()>pageCount){
				ids = ids.subList(0, pageCount);
			}
			entities = goodsMapper.listByIds(ids);
		}
		return entities;
	}

}
