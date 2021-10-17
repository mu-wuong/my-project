package com.jsv.giftShop.controller;

import java.util.ArrayList;
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
import com.jsv.giftShop.dto.GoodsOrderDTO;
import com.jsv.giftShop.dto.GoodsOrderDetailDTO;
import com.jsv.giftShop.service.OrderService;
import com.jsv.giftShop.util.OrderBaseController;
import com.jsv.member.dto.MemberDTO;

@Controller("PaymentController")
public class OrderControllerImpl extends OrderBaseController implements OrderController {
	
	@Autowired
	OrderService orderService;
	
	//결제 폼 보여주기 - 장바구니 구매시
	@Override
	@RequestMapping(value="/gift-shop/goodsOrdersForm.mo", method=RequestMethod.POST)
	public ModelAndView goodsOrdersForm(ModelAndView mv,
			@RequestParam("checkArr") List<Integer> checkArr) throws Exception {
		
		List<Map<String, Object>> listMap = orderService.getCartList(checkArr);
		Map<String, Object> cartMap = listMap.get(0);
		
		Map<String, Object> memberInfoMap = orderService.getMemberInfo((Integer)cartMap.get("u_no"));
		
		mv.addObject("listMap", listMap);
		mv.addObject("memberInfoMap", memberInfoMap);
		mv.setViewName("/order/goodsOrderForm");
		
		return mv;
	}
	
	//결제 폼 보여주기 - 개별상품 구매시
	@Override
	@RequestMapping("/gift-shop/goodsOrderForm.mo")
	public ModelAndView goodsOrderForm(HttpServletRequest request,HttpServletRequest response,
										ModelAndView mv,
										@RequestParam("goods_no") int goods_no,
										@RequestParam("qty") int qty) throws Exception {
		
		MemberDTO user = (MemberDTO)request.getSession().getAttribute("authUser");
		int u_no = user.getU_no();

		Map<String, Object> memberInfoMap = orderService.getMemberInfo(u_no);
		List<Map<String, Object>> listMap = orderService.getOrderGoods(goods_no);
		
		mv.addObject("qty",qty);						//수량
		mv.addObject("listMap", listMap);				//상품상세내용
		mv.addObject("memberInfoMap", memberInfoMap);	//구매자 정보
		mv.addObject("result", "one");
		mv.setViewName("/order/goodsOrderForm");
		return mv;
	}
	
	//결제 처리
	@Override
	@ResponseBody
	@RequestMapping(value="/gift-shop/goodsOrder.mo",method=RequestMethod.POST)
	public Object newGoodsOrder (HttpServletRequest request,
								@RequestParam("goods_qty[]") List<Integer> goods_qty,
								@RequestParam("goods_no[]") List<Integer> goods_no,
								@RequestParam("goods_price[]") List<Integer> goods_price,
								@RequestParam("user_ph") int user_ph,
								@RequestParam("sum") int sum) throws Exception{
		
		MemberDTO user = (MemberDTO)request.getSession().getAttribute("authUser");
		Map<String,Integer> data = new HashMap<String,Integer>();
		
		//주문테이블값
		if(user!=null) {
			int u_no = user.getU_no();
			String orderNo = getOrderNo();	//주문번호 생성
			GoodsOrderDTO goodsOrderDTO = new GoodsOrderDTO(orderNo,u_no,user_ph,sum);	
			
			List<GoodsOrderDetailDTO> list = new ArrayList<GoodsOrderDetailDTO>();
			GoodsOrderDetailDTO orderDetailDTO = null;
			
			for(int i=0;i<goods_no.size();i++) {	//주문 상세 테이블 값
				orderDetailDTO = new GoodsOrderDetailDTO(orderNo,u_no,goods_no.get(i),
						goods_price.get(i),goods_qty.get(i),"card",getGiftconeNum());
				list.add(orderDetailDTO);
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", list);
			
			List<Map<String,String>> giftcones = orderService.newGoodsOrder(goodsOrderDTO,map);	//주문 테이블
			
			String text = "JSV 기프티콘 \n";
			if(giftcones!=null) {
				for(Map<String, String> giftcon:giftcones) {
					text += giftcon.get("goods_name")+" : "+giftcon.get("goods_giftcone_code")+"\n";
				}
				sendsms(String.valueOf("0"+user_ph), text);
				data.put("code", 200);
			}
		}else {
			data.put("code", 404);
		}
		
		Gson gson = new Gson();
		return gson.toJson(data);
	}
	
	//주문 - 성공,실패 여부 확인 후 페이지 리턴
	@Override
	@RequestMapping("/gift-shop/isOrderComplete.mo")
	public ModelAndView isOrderComplete(ModelAndView mv,
										@RequestParam("code") int code) throws Exception{
		
		if(code==200) {
			mv.setViewName("redirect:successOrderComplete.mo");
		}else {
			mv.setViewName("redirect:failedOrderComplete.mo?code=404");
		}
		
		return mv;
	}
	
	//주문 - 성공시 
	@Override
	@RequestMapping("/gift-shop/successOrderComplete.mo")
	public ModelAndView successOrderComplete(ModelAndView mv) throws Exception{

		mv.addObject("result", "complete");
		mv.setViewName("/order/isCompleteForm");
		return mv;
	}
	
	//주문 - 실패시
	@Override
	@RequestMapping("/gift-shop/failedOrderComplete.mo")
	public ModelAndView failedOrderComplete(ModelAndView mv, @RequestParam("code") int code) throws Exception{
		
		if(code==404) {
			mv.addObject("result", "failed_404");
			mv.setViewName("/order/isCompleteForm");
		}
		return mv;
	}
	
	
	//주문내역 조회
	@Override
	@RequestMapping("/gift-shop/getOrderList.mo")
	public ModelAndView getOrderList(HttpServletRequest request,
									 ModelAndView mv) throws Exception{

		
		MemberDTO user = (MemberDTO)request.getSession().getAttribute("authUser");
		int u_no = user.getU_no();
		List<Map<String,Object>> orderListMap = orderService.getOrderList(u_no);
		
		mv.addObject("orderListMap", orderListMap);
		mv.setViewName("/order/goodsOrderList");
		return mv;
	}
	
	//주문 상세내역 조회
	@Override
	@RequestMapping("/gift-shop/getOrderDetail.mo")
	public ModelAndView getOrderDetail(ModelAndView mv,
			@RequestParam("order_no") String goods_order_no) throws Exception{

		List<Map<String,Object>> detailListMap = orderService.getOrderDetail(goods_order_no);
		
		mv.addObject("detailListMap",detailListMap);
		mv.addObject("goods_order_no",goods_order_no);
		mv.setViewName("/order/goodsOrderDetail");
		return mv;
	}
	
	
	
}
