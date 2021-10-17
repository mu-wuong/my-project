package com.jsv.giftShop.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.jsv.giftShop.dto.GoodsCartDTO;
import com.jsv.giftShop.service.CartService;
import com.jsv.member.dto.MemberDTO;


//장바구니 관련 컨트롤러 
@Controller("CartController")
public class CartControllerImpl implements CartController {
	
	@Autowired
	CartService cartService;
	
	//장바구니 담기
	@Override
	@ResponseBody
	@RequestMapping(value="/gift-shop/addCart.mo", method=RequestMethod.POST)
	public Object addCart(HttpServletRequest request,
			@RequestParam("goods_no") int goods_no,
			@RequestParam("cart_qty") int cart_qty) throws Exception {
		
		Map<String,String> data = new HashMap<String,String>();
		Gson gson = new Gson();

		MemberDTO user = (MemberDTO)request.getSession().getAttribute("authUser");
		if(user!=null) {
			int userno = user.getU_no();
			GoodsCartDTO cartDTO = new GoodsCartDTO(0,userno,goods_no,cart_qty);

			int result = cartService.addCart(cartDTO);
			
			if(result>0) {
				data.put("code","success");
			}else {
				data.put("code","failed");
			}
			return gson.toJson(data);
		}else {
			data.put("code", "noLogin");
			return gson.toJson(data);
		}
	}
	
	//장바구니 목록조회
	@Override
	@RequestMapping("/gift-shop/userCartList.mo")
	public ModelAndView getCartList(HttpServletRequest request) throws Exception{

		MemberDTO user = (MemberDTO)request.getSession().getAttribute("authUser");
		ModelAndView mv = new ModelAndView();
		if(user!=null) {
			int userno = user.getU_no();
			
			List<GoodsCartDTO> cartList = cartService.getCartList(userno);
			
			mv.addObject("cartList", cartList);
			mv.setViewName("/cart/cartList");
		}
		return mv;
	}
	
	//장바구니 수량변경
	@Override
	@RequestMapping(value="/gift-shop/setCartQty.mo", method=RequestMethod.POST)
	@ResponseBody
	public Object setCartQty(@RequestParam("cart_no") int goods_cart_no,
							 @RequestParam("qty") int goods_cart_qty) throws Exception{
		
		Map<String,Integer> cartMap = new HashMap<String,Integer>();
		cartMap.put("goods_cart_no", goods_cart_no);
		cartMap.put("goods_cart_qty",goods_cart_qty);
		
		int result = cartService.setCartQty(cartMap);
		
		Map<String,String> data = new HashMap<String,String>();
		
		if(result>0) {
			data.put("code","success");
		}else {
			data.put("code","failed");
		}
		
		Gson gson = new Gson();
		return gson.toJson(data);
	}
	
	//장바구니 체크박스 선택 삭제
	@Override
	@RequestMapping(value="/gift-shop/removeCheckedCart.mo", method=RequestMethod.POST)
	@ResponseBody
	public Object removeCheckedCart(@RequestParam("checkArr[]") List<Integer> checkArr) throws Exception{
		
		int result = cartService.removeCheckedCart(checkArr);
		
		Map<String,String> data = new HashMap<String,String>();
		if(checkArr.size() == result) {
			data.put("code", "success");
		}else {
			data.put("code", "failed");
		}
		
		Gson gson = new Gson();
		return gson.toJson(data);
	}
	
}

