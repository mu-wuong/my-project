package com.jsv.admin.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.jsv.admin.service.AdminGoodsService;
import com.jsv.common.base.BaseController;
import com.jsv.giftShop.dto.GoodsCateDTO;
import com.jsv.giftShop.util.Pagination;

//기프트샵 관련 관리자 컨트롤러 
@Controller("AdminGoodsController")
public class AdminGoodsControllerImpl extends BaseController implements AdminGoodsController {
	
	
	@Autowired
	AdminGoodsService adminGoodsService;
	
	//(관리자)관리자 페이지 - 상품 목록조회
	@Override
	@RequestMapping("/admin/gift-shop.mo")
	public ModelAndView adminIndex(Pagination paging, ModelAndView mv, 
			@RequestParam(value="nowPage", required=false)String nowPage,
			@RequestParam(value="cntPerPage", required=false)String cntPerPage) throws Exception {
		
		int total = adminGoodsService.countGoods();
		if (nowPage == null && cntPerPage == null) {
			nowPage = "1";
			cntPerPage = "10";
		} else if (nowPage == null) {
			nowPage = "1";
		} else if (cntPerPage == null) { 
			cntPerPage = "10";
		}
		
		paging = new Pagination(total, Integer.parseInt(nowPage), Integer.parseInt(cntPerPage));
		mv.addObject("paging", paging);
		
		List<Map<String,Object>> goodsList  = adminGoodsService.getGoodsList(paging);
		mv.addObject("goodsList", goodsList);
		mv.setViewName("/admin/giftShop/main");
		return mv;
	}
		
	//(관리자)상품등록 폼 보여주기
	@Override
	@RequestMapping("/admin/gift-shop/newGoodsForm.mo")
	public ModelAndView newGoodsForm(ModelAndView mv) throws Exception {
		List<GoodsCateDTO> cateDTO = adminGoodsService.getGoodsCates();
		mv.addObject("cateDTO",cateDTO);
		mv.setViewName("/admin/giftShop/newGoodsForm");
		return mv;
	}
	
	//(관리자)상품등록 처리
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@RequestMapping(value="/admin/gift-shop/newGoods.mo", method=RequestMethod.POST)
	public ResponseEntity newGoods(MultipartHttpServletRequest multiRequest,
			RedirectAttributes rAttr) throws Exception {
		Map<String, Object> newGoodsMap = new HashMap<String,Object>();
		
		Enumeration eur = multiRequest.getParameterNames();
		while(eur.hasMoreElements()) {
			String name = (String) eur.nextElement();
				String value = multiRequest.getParameter(name);
				newGoodsMap.put(name, value);
		}
		
		String goods_img = uploadGoodsImg(multiRequest);
		if(goods_img!=null && goods_img.length()!=0) {
			newGoodsMap.put("goods_img",goods_img);
		}
		
		String msg = null; //view 전달내용
		ResponseEntity resEntity = null;	//응답시 클라이언트에게 보낼 내용
		HttpHeaders responseHerders = new HttpHeaders(); 
		responseHerders.add("Content-Type", "text/html; charset=UTF-8");//응답시 헤더에 추가
		
		int goods_no = adminGoodsService.newGoods(newGoodsMap);
		
		try {
			if(goods_img!=null && goods_img.length()!=0) {
				uploadGoodsImgMove(goods_img, goods_no); //폴더 이동 모듈로 뺌
			}
			msg = "<script>";
			msg += "alert('상품이 등록되었습니다');";		
			msg += "location.href='"+multiRequest.getContextPath()+"/admin/gift-shop.mo'";
			msg += "</script>";
		}catch(Exception e){
			msg =  "<sctipt>";
			msg += "alert('오류 발생으로 상품 등록이 실패 되었습니다');";
			msg += "location.href='"+multiRequest.getContextPath()+"/admin/gift-shop.mo'";
			msg += "</script>";
			e.printStackTrace();
		}
		resEntity = new ResponseEntity(msg,responseHerders,HttpStatus.OK);
		
		return resEntity;
	}
	
	//(관리자)상품수정 폼 보여주기 
	@Override
	@RequestMapping("/admin/gift-shop/setGoodsForm.mo")
	public ModelAndView setGoodsForm(ModelAndView mv,
			@RequestParam("goods_no") int goods_no) throws Exception {
		Map<String, Object> goodsMap = adminGoodsService.setGoodsForm(goods_no);
		mv.addObject("goodsMap", goodsMap);
		mv.setViewName("/admin/giftShop/setGoodsForm");
		return mv;
	}

	//(관리자) 상품수정처리
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@RequestMapping(value = "/admin/gift-shop/setGoods.mo", method=RequestMethod.POST)
	public ResponseEntity setGoods(MultipartHttpServletRequest multiRequest, 
			RedirectAttributes rAttr,ModelAndView mv) throws Exception {
		Map<String,String> goodsMap = new HashMap<String,String>();
		
		Enumeration names = multiRequest.getParameterNames();
		
		while(names.hasMoreElements()) {
			String name = (String) names.nextElement();
			String value = multiRequest.getParameter(name);
			goodsMap.put(name, value);
		}
		
		String msg = null; //view 전달내용
		String goods_img = null;
		int goods_no = 0;
		ResponseEntity resEntity = null;	//응답시 클라이언트에게 보낼 내용
		HttpHeaders responseHerders = new HttpHeaders(); 
		responseHerders.add("Content-Type", "text/html; charset=UTF-8");
		
			if(!multiRequest.getFile("goods_img").isEmpty()) {
				goods_no = Integer.parseInt(goodsMap.get("goods_no"));
				removeGoodsImgFolder(goods_no); //폴더 삭제
				goods_img = uploadGoodsImg(multiRequest); //업로드
				uploadGoodsImgMove(goods_img,goods_no);	//폴더이동
				goodsMap.put("goods_img", goods_img);
				adminGoodsService.setGoods(goodsMap);
				
				msg = "<script>";
				msg += "alert('상품이 수정되었습니다');";		
				msg += "location.href='"+multiRequest.getContextPath()+"/admin/gift-shop.mo'";
				msg += "</script>";
			}else if(multiRequest.getFile("goods_img").isEmpty()){
				adminGoodsService.setGoods(goodsMap);
				msg = "<script>";
				msg += "alert('상품이 수정되었습니다');";		
				msg += "location.href='"+multiRequest.getContextPath()+"/admin/gift-shop.mo'";
				msg += "</script>";
			}else {
				msg =  "<sctipt>";
				msg += "alert('오류 발생으로 상품 수정이 실패 되었습니다');";
				msg += "location.href='"+multiRequest.getContextPath()+"/admin/gift-shop/setGoodsForm.mo?'"+goods_no+"'";
				msg += "</script>";
			}
			
			resEntity = new ResponseEntity(msg,responseHerders,HttpStatus.OK);
		return resEntity;
	}
	
	//(관리자) 상품삭제
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@RequestMapping("/admin/gift-shop/removeGoods.mo")
	public ResponseEntity removeGoods(HttpServletRequest request,
			@RequestParam("goods_no") int goods_no) throws Exception {
		
		int result = adminGoodsService.removeGoods(goods_no);
		
		String msg = null; 
		ResponseEntity resEntity = null;
		HttpHeaders responseHerders = new HttpHeaders(); 
		responseHerders.add("Content-Type", "text/html; charset=UTF-8");
		
		try {
			if(result>0) {
				removeGoodsImgFolder(goods_no);
				
				msg = "<script>";
				msg += "alert('상품이 삭제되었습니다');";		
				msg += "location.href='"+request.getContextPath()+"/admin/gift-shop.mo'";
				msg += "</script>";
			}//if
		}catch(Exception e) {
			msg = "<script>";
			msg += "alert('오류로 인해 상품 삭제가 실패되었습니다');";		
			msg += "location.href='"+request.getContextPath()+"/admin/gift-shop.mo'";
			msg += "</script>";
			e.printStackTrace();
		}
		
		resEntity = new ResponseEntity(msg,responseHerders,HttpStatus.OK);
		return resEntity;
	}
	
	//(관리자) 체크박스 상품 삭제
	@Override
	@ResponseBody
	@RequestMapping("/admin/gift-shop/removeCheckedGoods.mo")
	public Object removeCheckedGoods(@RequestParam("checkArr[]") List<Integer> checkArr) throws Exception {
	
		Map<String,Object> data = new HashMap<String,Object>();
		
		int result = adminGoodsService.removeCheckedGoods(checkArr);
		
		if(result>0) {
			for(Integer goods_no : checkArr){	
				removeGoodsImgFolder(goods_no);//이미지 삭제
			}
			data.put("code","success");
		}else {
			data.put("code","failed");
		}
		Gson gson = new Gson();
		return gson.toJson(data);
	}
	
	
	//(관리자) 연매출조회 폼 보여주기
	@Override
	@RequestMapping("/admin/gift-shop/goodsSales.mo")
	public ModelAndView goodsSales(ModelAndView mv) throws Exception {
		mv.setViewName("/admin/giftShop/goodsYearSales");
		return mv;
	}
	
	//(관리자) 연매출 조회 
	@Override
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/admin/gift-shop/getGoodsSales.mo")
	public JSONArray getGoodsSales() throws Exception {
		
		List<Map<String, String>> listMap = adminGoodsService.getGoodsYearSales();
		
		JSONArray jsonArray = new JSONArray();
		JSONArray colNameArray = new JSONArray(); // 컬 타이틀 설정
		colNameArray.add("연도");
		colNameArray.add("매출");
		jsonArray.add(colNameArray);
		
		for(int i=0;i<listMap.size();i++) {
			JSONArray rowArray = new JSONArray();
			rowArray.add(listMap.get(i).get("year"));
			rowArray.add(listMap.get(i).get("total"));
			jsonArray.add(rowArray);
		}
		return jsonArray;
	}
	
	//(관리자) 월 매출 조회
	@Override
	@RequestMapping("/admin/gift-shop/getGoodsMonthlySales.mo")
	public ModelAndView getGoodsMonthlySales(ModelAndView mv) throws Exception {
		
		List<Map<String,Object>> listMap = adminGoodsService.getGoodsMonthlySales();
		mv.addObject("listMap", listMap);
		mv.setViewName("/admin/giftShop/goodsMonthlySales");
		return mv;
	}
	
	//(관리자) 월별  일매출 조회
	@Override
	@RequestMapping("/admin/gift-shop/getGoodsDailySales.mo")
	public ModelAndView getGoodsDailySales(ModelAndView mv,
								@RequestParam("year") int year,
								@RequestParam("month") int month) throws Exception {
		
		Map<String,Object> yearAndMonthMap = new HashMap<String,Object>();
		yearAndMonthMap.put("year", year);
		yearAndMonthMap.put("month", month);
		
		List<Map<String,Object>> listMap = adminGoodsService.getGoodsDailySales(year,month);
		
		mv.addObject("dateInfo", yearAndMonthMap);
		mv.addObject("listMap", listMap);
		mv.setViewName("/admin/giftShop/goodsDailySales");
		return mv;
	}
	
}
