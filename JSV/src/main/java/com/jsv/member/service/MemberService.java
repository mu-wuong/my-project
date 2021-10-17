package com.jsv.member.service;

import java.util.Map;


import org.springframework.dao.DataAccessException;

import com.jsv.member.dto.MemberDTO;

public interface MemberService {
		
	// 회원가입시 - 아이디중복체크
	public int idCheck(MemberDTO memberDto) throws Exception;
	
	//회원가입 처리
	public int insertUser(MemberDTO memberDto) throws Exception;
	
	//로그인처리
	public MemberDTO loginProc(MemberDTO memberdto) throws DataAccessException;
	
	//패스워드 체크
	public int pwdCheck(Map<String,Object> map) throws Exception;
	
	//마이페이지 폼 보여주기
	public MemberDTO getMypageInfo(int u_no) throws Exception;
	
	//마이페이지 수정처리
	public int setMypageInfo(MemberDTO memberDto) throws Exception;
	
	//회원탈퇴
	public int withdrawal(int u_no) throws Exception;

}
