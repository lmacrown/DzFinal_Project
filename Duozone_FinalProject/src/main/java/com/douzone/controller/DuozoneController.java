package com.douzone.controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douzone.entity.DouzoneVO;
import com.douzone.service.DouzoneService;

@Controller
@RequestMapping("/")
public class DuozoneController {

	@Autowired
	DouzoneService douzoneService;
	
//	@GetMapping("/")
//	@ResponseBody
//	public String home() {
//		System.out.println("Hello World");
//		return "Hello Boot!!!";
//	}
	
	
	@RequestMapping(value="/hello.do", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> hello(Locale locale, Model model,
			@RequestBody HashMap<String, String> map, HttpSession session) {
		Map<String, Object> result = new HashMap();

		DouzoneVO member = douzoneService.login(map.get("MEMBER_ID"), map.get("MEMBER_PW"));
		
		if(member != null) {
			result.put("url","/");
			session.setAttribute("isLogOn", true);
			session.setAttribute("member", member);
		}else {
			session.setAttribute("isLogOn", false);
		}
		
		return result;
	}
	
}
