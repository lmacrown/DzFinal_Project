package com.douzone.controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.douzone.entity.DouzoneVO;
import com.douzone.entity.IncomingVO;
import com.douzone.service.DouzoneService;
import com.douzone.service.IncomingService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class DouzoneController {

	@Autowired
	DouzoneService douzoneService;
	
	@Autowired
	IncomingService incomingService;
	
	@PostMapping(value="/hello.do")
	public Map<String, Object> hello(Locale locale, Model model,
			@RequestBody HashMap<String, String> map, HttpSession session) {
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
	
	@GetMapping(value="/checkinfo.do")
	public Map<String, Object> checkInfo(Locale locale, Model model,
			@RequestBody HashMap<String, Object> map, HttpSession session) {
		Map<String, Object> result = new HashMap<>();
		//데이터 6개를 보내야 함
		IncomingVO incoming = incomingService.select(map);
		result.put("earnerInfo", incoming);
		
		return result;
	}
	
	@GetMapping(value="/regist.do")
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
	
	@PostMapping(value="/searchearner.do")
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
