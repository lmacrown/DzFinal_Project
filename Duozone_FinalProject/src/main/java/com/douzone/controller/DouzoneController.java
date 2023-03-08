package com.douzone.controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.douzone.Aes;
import com.douzone.entity.DouzoneVO;
import com.douzone.entity.IncomingVO;
import com.douzone.service.DouzoneService;
import com.douzone.service.IncomingService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin("*")
public class DouzoneController {

	@Autowired
	DouzoneService douzoneService;
	
	@Autowired
	IncomingService incomingService;
	//로그인
	@PostMapping(value="/login")
	public Map<String, Object> hello(Locale locale, Model model,
			@RequestBody HashMap<String, String> map, HttpSession session) throws Exception {
		Map<String, Object> result = new HashMap<>();

		DouzoneVO member = douzoneService.login(map.get("MEMBER_ID"), map.get("MEMBER_PW"));
		if(member != null) {
			result.put("member",member);
			session.setAttribute("isLogOn", true);
			session.setAttribute("member", member);
		}else {
			session.setAttribute("isLogOn", false);
		}
		
		return result;
	}
	//소득자별조회
	@GetMapping(value="/earner_list")
	public Map<String, Object> earner_list(Locale locale, Model model,
			@RequestBody HashMap<String, Object> map, HttpSession session) {
		Map<String, Object> result = new HashMap<>();
		//데이터 5개를 보내야 함
		DouzoneVO douzoneVo=(DouzoneVO) session.getAttribute("member");
		map.put("worker_id", "W001");
		IncomingVO incoming = incomingService.earner_list(map);
		
		result.put("earnerInfo", incoming);
		
		return result;
	}
	//사업소득자 비가시화
	@GetMapping(value="/reg/visible_update")
	public Map<String, Object> visible_update(Locale locale, Model model,
			@RequestBody HashMap<String, Object> map, HttpSession session) {
		Map<String, Object> result = new HashMap<>();
		//데이터 5개를 보내야 함
		DouzoneVO douzoneVo=(DouzoneVO) session.getAttribute("member");
		map.put("worker_id", "W202200001");
		HashMap<String, Object> test_map = new HashMap<String, Object>();
		test_map.put("map",map);
		try {
		incomingService.visible_update(test_map);
		result.put("visible_true", true);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
//	//소득구분별조회
//	@GetMapping(value="/earner_list/income_category")
//	public Map<String, Object> income_category(Locale locale, Model model,
//			@RequestBody HashMap<String, Object> map, HttpSession session) {
//		Map<String, Object> result = new HashMap<>();
//		//데이터 5개를 보내야 함
//		DouzoneVO douzoneVo=(DouzoneVO) session.getAttribute("member");
//		map.put("worker_id", douzoneVo.getWorker_id());
//		IncomingVO incoming = incomingService.income_category(map);
//		
//		result.put("earnerInfo", incoming);
//		
//		return result;
//	}
	//소득자료등록
	@GetMapping(value="/regist")
	public Map<String, Object> insertinfo(Locale locale, Model model,
			@RequestBody HashMap<String, Object> map, HttpSession session) {
		Map<String, Object> result = new HashMap<>();
		try {
			incomingService.regist(map);
			result.put("status", true);
		}catch(Exception e) {
			result.put("status", false);
		}
		return result;
	}
	//소득자료조회
	@PostMapping(value="/searchearner")
	public Map<String, Object> searchearner(Locale locale, Model model,
			@RequestBody HashMap<String, Object> map, HttpSession session) {
		Map<String, Object> result = new HashMap<>();
		HashMap<String, Object> test_map = new HashMap<String, Object>();
		test_map.put("map",map);
		
		result.put("searchearner",incomingService.searchearner(test_map));
		return result;
	}
	
	@GetMapping(value="/test1.do")
	public Map<String, Object> test1(Locale locale, Model model,
			@RequestBody HashMap<String, Object> map, HttpSession session) {
		Map<String, Object> result = new HashMap<>();
		
		result.put("test1",incomingService.test1(map));
		return result;
	}
}
