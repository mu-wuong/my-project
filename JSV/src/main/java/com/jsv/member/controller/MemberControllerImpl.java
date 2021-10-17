package com.jsv.member.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jsv.member.dto.MemberDTO;
import com.jsv.member.service.MemberService;

@Controller("MemberController")
public class MemberControllerImpl implements MemberController {
	
	@Autowired
	MemberService memberService;
	
	//아이디중복체크
	@Override
	@RequestMapping(value="/member/idChk.mo", method = RequestMethod.POST)
	@ResponseBody
	public int idCheck(MemberDTO memberDto) throws Exception{
		return memberService.idCheck(memberDto);
	}
		
	//회원가입 폼 보여주기
	@Override
	@RequestMapping(value = "/member/signupForm.mo", method = RequestMethod.GET) 
	public String signupForm(Model model) throws Exception { 
		return "/member/register";
	}

	//회원가입처리
	@Override
	@RequestMapping(value = "/member/signup.mo", method = RequestMethod.POST)
	public String insertUser(@ModelAttribute("memberDto") MemberDTO memberDto , 
			RedirectAttributes rttr) throws Exception { 
		
		int result = memberService.insertUser(memberDto); 
		
		if(result!=0) {
			rttr.addAttribute("result", "joinSuccess");
			return "redirect:/member/loginForm.mo"; 
		}else {
			rttr.addAttribute("result", "joinFailed");
			return "redirect:/member/signupForm.mo"; 
		}
	}
		
		
	//로그인폼 보여주기
	@Override
	@RequestMapping("/member/loginForm.mo")
	public String loginForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/member/login";
	}
	
	//로그인처리
	@Override
	@RequestMapping(value="/member/login.mo", method = RequestMethod.POST)
	public ModelAndView loginProc(HttpServletRequest request, 
			HttpServletResponse response,
			RedirectAttributes rAttr) throws Exception {
		
		String u_id = request.getParameter("u_id");
		String u_password = request.getParameter("u_password");
		
		MemberDTO memberDto = new MemberDTO();
		memberDto.setU_id(u_id);
		memberDto.setU_password(u_password);
		
		memberDto = memberService.loginProc(memberDto);
		
		ModelAndView mv = new ModelAndView();
		
		if(memberDto!=null && !memberDto.getIs_connection().equals("n")) { 
			HttpSession session = request.getSession();
			session.setAttribute("authUser",memberDto); 
			session.setAttribute("isLogOn",true); 
			mv.setViewName("redirect:/main.mo");	
		}else if(memberDto!=null && memberDto.getIs_connection().equals("n")) {
			rAttr.addAttribute("result", "withdrawal");
			mv.setViewName("redirect:/member/loginForm.mo"); 
		}else if(memberDto!=null && memberDto.getIs_connection().equals("s")) {
			rAttr.addAttribute("result", "stop");
			mv.setViewName("redirect:/member/loginForm.mo");
		}else{ //로그인실패시
			rAttr.addAttribute("result", "loginFailed");
			mv.setViewName("redirect:/member/loginForm.mo"); 
		}
		return mv;
	}
	
	//로그아웃
	@Override
	@RequestMapping("/member/logout.mo")
	public ModelAndView logout(HttpServletRequest request, 
			HttpServletResponse response, ModelAndView mv) throws Exception {
		HttpSession session = request.getSession();
		session.removeAttribute("authUser");
		session.removeAttribute("isLogOn");

		mv.setViewName("redirect:/main.mo");
		return mv;
	}
	
	//마이페이지 비밀번호 입력 폼
	@Override
	@RequestMapping(value = "/member/mypagePwdCheck.mo")
	public String mypagePwdCheckForm(HttpServletRequest request) throws Exception{
		return "/member/mypagePwdCheckForm";
	}
	
	//마이페이지 - 패스워드 체크
	@Override
	@ResponseBody
	@RequestMapping(value="/member/pwdCheck.mo", method=RequestMethod.POST)
	public int pwdCheck(HttpServletRequest request,
						@RequestParam("u_password") String u_password)throws Exception{
		
		Map<String,Object> map = new HashMap<String,Object>();
		MemberDTO user = (MemberDTO)request.getSession().getAttribute("authUser");
		
		map.put("u_no", user.getU_no());
		map.put("u_password",u_password);
		
		int result = memberService.pwdCheck(map);
		return result;
	}
	
	//마이페이지 폼 보여주기 
	@Override
	@RequestMapping("/member/mypage.mo")
	public ModelAndView mypageForm(HttpServletRequest request, ModelAndView mv) throws Exception {
		MemberDTO user = (MemberDTO)request.getSession().getAttribute("authUser");
		int u_no = user.getU_no();
		MemberDTO memberDTO = memberService.getMypageInfo(u_no);
		
		mv.addObject("memberDTO", memberDTO);
		mv.setViewName("/member/mypage");
		return mv;
	}
	
	//마이페이지 수정처리
	@Override
	@RequestMapping(value = "/member/setMypageInfo.mo", method = RequestMethod.POST)
	@ResponseBody
	public Integer update_mypage(@ModelAttribute MemberDTO memberDto, HttpSession session, RedirectAttributes rttr) throws Exception{
		
		MemberDTO user = (MemberDTO)session.getAttribute("authUser");
		int u_no = user.getU_no();
		memberDto.setU_no(u_no);
		System.out.println(memberDto);
		int result = memberService.setMypageInfo(memberDto);
		
		if(result>0) {
			return 1;
		}else {
			return 0;
		}
	}
		
	//회원탈퇴
	@Override
	@RequestMapping(value = "/member/withdrawal.mo")
	@ResponseBody
	public Integer membershipWithdrawal(HttpSession session) throws Exception{
		MemberDTO user = (MemberDTO)session.getAttribute("authUser");
		int u_no = user.getU_no();
		
		int result = memberService.withdrawal(u_no);
		
		if(result>0) {
			session.removeAttribute("authUser");
			return 1;
		}else {
			return 0;
		}
	}

}
