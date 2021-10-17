package com.jsv.reserve.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.apache.xerces.impl.dv.DatatypeValidator;
import org.dom4j.datatype.DatatypeAttribute;
import org.json.simple.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonatype.aether.SessionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.jsv.reserve.dto.MovieDTO;
import com.jsv.reserve.dto.PayDTO;
import com.jsv.reserve.dto.ReserveDTO;
import com.jsv.reserve.dto.TheaterDTO;
import com.jsv.reserve.dto.memberDTO.MemberDTO;
import com.jsv.reserve.service.PayService;
import com.jsv.reserve.service.ReserveService;

@Controller
public class ReserveController {
	
	@Autowired
	ReserveService reserveService;
	
	@Autowired
	PayService payService;
	
	
	//영화 조회 컨트롤러
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/movieList", produces = "application/json")
	@ResponseBody
	public List<MovieDTO> movieList(Model model) throws Exception{
		System.out.println("ReserveController : movieList");
		List<MovieDTO> movieList = reserveService.getMovieList();
		model.addAttribute("movieList", movieList);
		JSONArray array = new JSONArray();
		
		return movieList;
	}
	
	//영화관 조회 컨트롤러
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/theaterList", produces = "application/json")
	@ResponseBody
	public List<TheaterDTO> theaterList(Model model, @RequestParam("location") String location) throws Exception {
		
		System.out.println("ReserveController : theaterList");
		List<TheaterDTO> theaterList = reserveService.getTheaterList(location);
		System.out.println("return값 :"+theaterList);
		model.addAttribute("theaterList", theaterList);
		JSONArray array = new JSONArray();

		return theaterList;
	}
	
	//예약 컨트롤러
	private static Logger logger = LoggerFactory.getLogger(ReserveController.class);
	@RequestMapping(value="moveReserve.mo", method= {RequestMethod.GET, RequestMethod.POST})
	public String moveReserve() {
		logger.info("moveReserve");
		return "reserve";
	}
	
	//좌석지정 컨트롤러
	@RequestMapping(value="moveSeat.mo", method= {RequestMethod.GET, RequestMethod.POST})
	public String moveSeat(Model model, ReserveDTO dto) {
		System.out.println(dto.toString());
		logger.info("moveSeat");

		model.addAttribute("reserve", dto);
		
		return "seat";
	}
	
	@Autowired
	SqlSession session;
	
//	@RequestMapping(value="/test", method=RequestMethod.GET)
//	public String test(Locale locale, Model model, ModelAndView mv) {
//		ReserveDTO dto = new ReserveDTO("leaid", "테스트영화명", "2:15", "2021.09.30", 23, "C12", "테스트관", "12세 관람가");
//		
//		int result = session.insert("test.test1", dto);
//		System.out.println("result="+result);
//		
//		return "test";
//	}
	
	
	//결제 컨트롤러
	@RequestMapping(value="moveKakao.mo", method = {RequestMethod.GET, RequestMethod.POST})
	public String moveKakao(Locale locale, Model model, ModelAndView mv) {
		logger.info("moveKakao");
		System.out.println("moveKakao");
		
		//임시세션부여
		ReserveDTO dto = new ReserveDTO("jung8", "테스트영화명", "2:15", "2021.09.30", 23, "C12", "테스트관", "12세 관람가");
		PayDTO payDto = new PayDTO("hongid1", 124, "09.25", "23000");
		System.out.println("toString : " + dto.toString());
		
		//int result = session.insert("reserveSpace.Reserve", dto);
		//System.out.println("result="+result);
		
		//dto.setId(login.getId());
		boolean isSuccess = reserveService.Reserve(dto);
		System.out.println("moveKakao isSuccess : " + isSuccess);
		model.addAttribute("reserve", dto);
		model.addAttribute("pay", payDto);
		if(isSuccess == false) {
			System.out.println("오류");
			return "redirect:/moveMain.mo";
		}
		return "kakao";
	}

	//결제 컨트롤러
	/*@RequestMapping(value="moveKakao.mo", method = {RequestMethod.GET, RequestMethod.POST})
	public String moveKakao(Locale locale, Model model, ModelAndView mv, ReserveDTO dto, PayDTO payDto, HttpSession session) {
		System.out.println(dto.toString());
		logger.info("moveKakao");
		MemberDTO login = (MemberDTO)session.getAttribute("login");
		System.out.println("moveKakao");
		
		//임시세션부여
		PayDTO payDto1 = new PayDTO("hongid1", 124, "09.25", "23000");
		ReserveDTO dto1 = new ReserveDTO(
				"hongid",123,"테스트영화","2:15","09.24","09.25",123,"C12","김포","12세");
				//"hongid",123,"테스트영화","2:15","09.24","09.25",123,"C12","김포","12세", payDto1);
		
		
		System.out.println("toString : "+dto1.toString());
		//dto.setId(login.getId());
		boolean isSuccess = reserveService.Reserve(dto1);
		System.out.println("moveKakao isSuccess : " + isSuccess);
		model.addAttribute("reserve", dto1);
		model.addAttribute("pay", payDto1);
		if(isSuccess == false) {
			System.out.println("오류");
			return "redirect:/moveMain.mo";
		}
		return "kakao";
	}*/
	
	
	@RequestMapping(value="payKakao.mo", method = {RequestMethod.GET, RequestMethod.POST})
	public String payKakao(Model model, ReserveDTO dto, PayDTO payDto, HttpSession session) {
		logger.info("moveKakao");
		MemberDTO login = (MemberDTO)session.getAttribute("login");
		
		//dto.setId(login.getId());
		System.out.println(dto.toString());
		List<ReserveDTO> list = reserveService.getReserve(dto);
		
		payDto.setId(list.get(0).getId());
		payDto.setReserveSequence(list.get(0).getReserveSequence());
		System.out.println(payDto.toString());
		boolean isSuccess = payService.payTicket(payDto);
		System.out.println(isSuccess);
		if(isSuccess == false) {
			System.out.println("오류");
			return "redirect:/moveMain.mo";
		}
		model.addAttribute("type","reserve");
		model.addAttribute("isSuccess",isSuccess);
		
		return "process";
	}
	
	@RequestMapping(value="paySuccess.mo", method= {RequestMethod.GET, RequestMethod.POST})
	public String paySuccess(Model model, ReserveDTO reserveDTO, PayDTO payDTO, HttpSession session) {
		List<ReserveDTO> list = reserveService.getPayJoinTable(reserveDTO);
		
		if(list != null) {
			for(ReserveDTO dto : list) {
				System.out.println(dto.toString());
				model.addAttribute("reserveList", list);
			}
		}else {
			System.out.println("오류");
		}
		return "paySuccess";
	}
}
