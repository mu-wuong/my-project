package com.jsv.admin.controller;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jsv.giftShop.util.Pagination;

public interface AdminMemberController {
	
	//(관리자) 회원 목록 조회 
	public ModelAndView getMemberList(ModelAndView mv,Pagination paging,
			@RequestParam(value="nowPage", required=false)String nowPage,
			@RequestParam(value="cntPerPage", required=false)String cntPerPage) throws Exception;
	
}
