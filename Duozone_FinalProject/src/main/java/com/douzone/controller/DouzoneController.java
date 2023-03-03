package com.douzone.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douzone.entity.DouzoneVO;
import com.douzone.entity.IncomingVO;
import com.douzone.service.DouzoneService;
import com.douzone.service.IncomingService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class DouzoneController {

	@Autowired
	DouzoneService douzoneService;
	
	@Autowired
	IncomingService incomingService;
	
	
	
	@RequestMapping(value="/hello.do", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> hello(Locale locale, Model model,
			@RequestBody HashMap<String, String> map, HttpSession session) {
		Map<String, Object> result = new HashMap();

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
	
	@RequestMapping(value="/checkinfo.do", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> checkInfo(Locale locale, Model model,
			@RequestBody HashMap<String, Object> map, HttpSession session) {
		Map<String, Object> result = new HashMap();
		//데이터 6개를 보내야 함
		IncomingVO incoming = incomingService.select(map);
		result.put("earnerInfo", incoming);
		
		return result;
	}
	
	@RequestMapping(value="/regist.do", method=RequestMethod.GET)
	@ResponseBody
	public void insertinfo(Locale locale, Model model,
			@RequestBody HashMap<String, Object> map, HttpSession session) {
		Map<String, Object> result = new HashMap();
		//데이터 4개를 보내야 함
		incomingService.regist(map);
	}
	
	@RequestMapping(value="/searchearner.do", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> searchearner(Locale locale, Model model,
			@RequestBody HashMap<String, Object> map, HttpSession session) {
		Map<String, Object> result = new HashMap();
		HashMap<String, Object> test_map = new HashMap<String, Object>();
		test_map.put("map",map);
		
		result.put("searchearner",incomingService.searchearner(test_map));
		return result;
	}
	
	@RequestMapping(value="/test1.do", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> test1(Locale locale, Model model,
			@RequestBody HashMap<String, Object> map, HttpSession session) {
		Map<String, Object> result = new HashMap();
		
		result.put("test1",incomingService.test1(map));
		return result;
	}
}
