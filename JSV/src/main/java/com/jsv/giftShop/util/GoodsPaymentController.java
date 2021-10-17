package com.jsv.giftShop.util;

//상품 구매 결제 검증 관련
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

@Controller("PaymentsController")
public class GoodsPaymentController {
	
	//결제 검증
	@ResponseBody
	@RequestMapping(value="/verifyIamport.mo", method=RequestMethod.POST, produces = "application/json")
	public Object paymentsComplete(HttpServletRequest request, HttpServletResponse response,
			 @RequestParam(value= "imp_uid") String imp_uid) throws Exception{
		
		IamportClient client  = new IamportClient("9687728674866571",
				"91b5205883d34b20e4f8086d799ad84953e8aa9dafad85de719ea2e73bf500274f94c2634bcdb59e");
		
		IamportResponse<Payment> payment =  client.paymentByImpUid(imp_uid);
		
		String uid = payment.getResponse().getImpUid();
		
		Map<String,Integer> data = new HashMap<String,Integer>();
		if(uid!=imp_uid) {
			data.put("code", 200);
		}
		
		Gson gson = new Gson();
		return gson.toJson(data);
	}
	
	
	

}