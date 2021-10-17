package com.jsv.member.dao;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.jsv.member.dto.MemberDTO;


public interface MemberDAO {
	
	//회원가입시 ID중복 검사 
	public int idCheck(MemberDTO memberDto) throws DataAccessException;

	//회원가입 처리
	public int insertUser(MemberDTO memberDto) throws DataAccessException;
	
	//로그인처리
	public MemberDTO  loginProc(MemberDTO memberDto) throws DataAccessException;
	
	//로그인 검사
	public MemberDTO login(String u_id) throws DataAccessException;

	//마이페이지 - 패스워드 체크
	public int pwdCheck(Map<String, Object> map) throws DataAccessException;
	
	//마이페이지 폼 보여주기
	public MemberDTO getMypageInfo(int u_no) throws DataAccessException;
	
	//마이페이지 수정처리
	public int setMypageInfo(MemberDTO memberDto) throws DataAccessException;

	//회원탈퇴
	public int withdrawal(int u_no) throws DataAccessException;

}
