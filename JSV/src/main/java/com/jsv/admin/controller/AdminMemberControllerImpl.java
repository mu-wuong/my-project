package com.jsv.admin.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jsv.admin.service.AdminMemberService;
import com.jsv.giftShop.util.Pagination;

@Controller("AdminMemberController")
public class AdminMemberControllerImpl implements AdminMemberController {
	
	@Autowired
	private AdminMemberService adminMemberService;
	
	//회원 목록 조회 
	@Override
	@RequestMapping("/admin/member/memberList.mo")
	public ModelAndView getMemberList(ModelAndView mv, Pagination paging,
			@RequestParam(value="nowPage", required=false)String nowPage,
			@RequestParam(value="cntPerPage", required=false)String cntPerPage) throws Exception {
		
		int total = adminMemberService.countMember();
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
		
		List<Map<String,Object>> memberListMap = adminMemberService.getMemberList(paging);
		mv.addObject("memberListMap",memberListMap);
		mv.setViewName("/admin/member/memberList");
		return mv;
	}
	
	//회원 활동정지
	@RequestMapping("/admin/member/memberStop.mo")
	public String memberStop(RedirectAttributes rAttr,@RequestParam("uno") int u_no)throws Exception {
		
		int result = adminMemberService.memberStop(u_no);
		if(result>0) {
			rAttr.addAttribute("result", "stopSuccess");
			return "redirect:/admin/member/memberList.mo";
		}else {
			rAttr.addAttribute("result", "stopFailed");
			return "redirect:/admin/member/memberList.mo";
		}
	}
	
	//회원 활동정지 해제 
	@RequestMapping("/admin/member/memberRelease.mo")
	public String memberRelease(RedirectAttributes rAttr,@RequestParam("uno") int u_no)throws Exception {
		
		int result = adminMemberService.memberRelease(u_no);
		if(result>0) {
			rAttr.addAttribute("result", "releaseSuccess");
			return "redirect:/admin/member/memberList.mo";
		}else {
			rAttr.addAttribute("result", "releaseFailed");
			return "redirect:/admin/member/memberList.mo";
		}
	}

}
