package com.jsv.giftShop.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jsv.giftShop.dto.GoodsCateDTO;
import com.jsv.giftShop.dto.GoodsDTO;
import com.jsv.giftShop.service.GoodsService;

@Controller("GoodsController")
public class GoodsControllerImpl implements GoodsController {
	
	@Autowired
	GoodsService goodsService;
	
	//기프트샵 메인 - 상품 목록
	@Override
	@RequestMapping("/gift-shop.mo")
	public ModelAndView getIndexGoodsList(ModelAndView mv) throws Exception {
		
		Map<String, List<GoodsDTO>> goodsMap = goodsService.getGoodsList();
		List<GoodsCateDTO> catesList = goodsService.getGoodsCates();
		
		mv.addObject("catesList",catesList);
		mv.addObject("goodsMap", goodsMap);
		mv.setViewName("/giftShopMain");
		return mv;
	}
	
	//상품 상세조회
	@Override
	@RequestMapping("/gift-shop/goodsDetail.mo")
	public ModelAndView getGoodsDetail(@RequestParam("goods_no") int goods_no,
									   ModelAndView mv) throws Exception {
		Map<String, String> goodsMap = goodsService.getGoodsDetail(goods_no);
		mv.addObject("goodsMap", goodsMap);
		mv.setViewName("/goods/goodsDetail");
		return mv;
	}
	
	//카테고리별 상품조회
	@Override
	@RequestMapping("/gift-shop/getCateGoodsList.mo")
	@ResponseBody
	public ModelAndView getCateGoodsList(@RequestParam("goods_cate_no") int goods_cate_no,
									   ModelAndView mv) throws Exception {
		List<Object> goodsMap = goodsService.getCateGoodsList(goods_cate_no);
		List<GoodsCateDTO> cates = goodsService.getGoodsCates();
		mv.addObject("cates",cates);
		mv.addObject("goodsMap", goodsMap);
		mv.setViewName("/goods/goodsList");
		return mv;
	}
	
	
}
