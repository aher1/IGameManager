/*
 * The MIT License
 *
 * Copyright 2013 jdmr.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.igame.app.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.igame.app.entity.GoodsEntity;
import com.igame.app.entity.GoodsTypeEntity;
import com.igame.app.exception.AppException;
import com.igame.app.service.GoodsService;
import com.igame.app.service.GoodsTypeService;
import com.igame.app.vo.GoodsTypeResponeVO;
import com.igame.app.vo.GoodsVO;
import com.igame.app.vo.GoodsResponeVO;
import com.igame.app.vo.RequestVO;
import com.igame.app.vo.SaleRequest;
import com.igame.app.vo.TypeListRequest;

/**
 *
 * @author jdmr
 */
@Controller
@RequestMapping("/shop")
public class AppController {
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private GoodsTypeService goodsTypeService;

	private static final Logger log = LoggerFactory.getLogger(AppController.class);

	// @RequestMapping(value = "/createGoods", method =
	// RequestMethod.POST,produces="application/json")
	// @ResponseBody
	// public String createGoods(@RequestBody GoodsVO goodsVO) {
	// log.debug("====== +++++++++" );
	// // System.out.println(goodsVO.toJSONString());
	// System.out.println(JSON.toJSON(goodsVO));
	// return "hello";
	// }

	@RequestMapping(value = "/hot", method = RequestMethod.POST)
	@ResponseBody
	public GoodsResponeVO getHotGoods(@RequestBody RequestVO hotRequestVO) {
		long appid = hotRequestVO.getAppid();
		int actionCode = hotRequestVO.getActionCode();
		log.debug("getHotGoods --> appid:{},actionCode:{}", appid, actionCode);
		try {
			List<GoodsEntity> goods = goodsService.getGoodsForHot(appid);
			GoodsResponeVO hotResponeVO = new GoodsResponeVO();
			hotResponeVO.setActionCode(actionCode);
			hotResponeVO.setProductList(goods);
			return hotResponeVO;
		} catch (AppException e) {
			throw e;
		} catch (Exception e) {
			log.error("getHotGoods error ", e);
			throw new AppException(actionCode, "系统错误");
		}
	}

	@RequestMapping(value = "/sale", method = RequestMethod.POST)
	@ResponseBody
	public GoodsResponeVO getSaleGoods(@RequestBody SaleRequest saleRequest) {
		long appid = saleRequest.getAppid();
		int actionCode = saleRequest.getActionCode();
		int pageNum = saleRequest.getPageNum();
		int pageCount = saleRequest.getPageCount();
		log.debug("getGoodsType --> appid:{},actionCode:{},pageNum:{},pageCount:{}", appid, actionCode, pageNum, pageCount);
		try {
			if (pageCount <= 0 || pageNum <= 0) {
				throw new AppException(actionCode, "分页参数错误");
			}
			List<GoodsEntity> goods = goodsService.getGoodsForSale(appid, pageNum, pageCount);
			GoodsResponeVO hotResponeVO = new GoodsResponeVO();
			hotResponeVO.setActionCode(actionCode);
			hotResponeVO.setProductList(goods);
			return hotResponeVO;
		} catch (AppException e) {
			throw e;
		} catch (Exception e) {
			log.error("getSaleGoods error ", e);
			throw new AppException(actionCode, "系统错误");
		}
	}

	@RequestMapping(value = "/type", method = RequestMethod.POST)
	@ResponseBody
	public GoodsTypeResponeVO getGoodsType(@RequestBody RequestVO requestVO) {
		long appid = requestVO.getAppid();
		int actionCode = requestVO.getActionCode();
		log.debug("getGoodsType --> appid:{},actionCode:{}", appid, actionCode);
		try {
			List<GoodsTypeEntity> typeEntities = goodsTypeService.getGoodsType(appid);
			GoodsTypeResponeVO responeVO = new GoodsTypeResponeVO();
			responeVO.setActionCode(actionCode);
			responeVO.setTypeList(typeEntities);
			return responeVO;
		} catch (AppException e) {
			throw e;
		} catch (Exception e) {
			log.error("getGoodsType error ", e);
			throw new AppException(actionCode, "系统错误");
		}

	}

	@RequestMapping(value = "/typelist", method = RequestMethod.POST)
	@ResponseBody
	public GoodsResponeVO getGoodsTypeList(@RequestBody TypeListRequest requestVO) {
		long appid = requestVO.getAppid();
		int actionCode = requestVO.getActionCode();
		int pageNum = requestVO.getPageNum();
		int pageCount = requestVO.getPageCount();
		int type = requestVO.getType();
		log.debug("getGoodsTypeList --> appid:{},actionCode:{},type:{},pageNum:{},pageCount:{}", appid, actionCode, type, pageNum, pageCount);
		try {
			if (pageCount <= 0 || pageNum <= 0) {
				throw new AppException(actionCode, "分页参数错误");
			}
			List<GoodsEntity> typeEntities = goodsTypeService.getGoodsByType(appid, type, pageNum, pageCount);
			GoodsResponeVO responeVO = new GoodsResponeVO();
			responeVO.setActionCode(actionCode);
			responeVO.setProductList(typeEntities);
			return responeVO;
		} catch (AppException e) {
			throw e;
		} catch (Exception e) {
			log.error("getGoodsTypeList error ", e);
			throw new AppException(actionCode, "系统错误");
		}

	}

	public static void main(String[] args) {
		RequestVO requestVO = new RequestVO();
		requestVO.setDeviceId("999988889");
		requestVO.setActionCode(10001);
		requestVO.setAppid(1L);
		requestVO.setAppVersionCode("1.0.1");
		requestVO.setChannel("腾讯平台");
		requestVO.setSystemName("IOS");
		requestVO.setDeviceModel("IPHONE6");
		requestVO.setSystemVersion("IOS6");

		System.out.println(JSON.toJSON(requestVO));
	}
}
