package com.douzone.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.DAO.IncomingDAO;
import com.douzone.entity.IncomingVO;

@Service
public class IncomingService {
	
	@Autowired
	IncomingDAO incomingDAO;
	
	public IncomingVO earner_list(HashMap<String, Object> map) {
		IncomingVO incomingVO = incomingDAO.earner_list(map);
		return incomingVO;
	}
	
	public IncomingVO income_category(HashMap<String, Object> map) {
		IncomingVO incomingVO = incomingDAO.income_category(map);
		return incomingVO;
	}

	public void regist(HashMap<String, Object> map) {
		incomingDAO.regist(map);
	}

	public Map<String, Object> test1(HashMap<String, Object> map) {
		return incomingDAO.test1(map);
	}

	public Map<String, Object> searchearner(HashMap<String, Object> map) {
		System.out.println(map);
		return incomingDAO.searchearner(map);
	}

	public void visible_update(HashMap<String, Object> map) {
		incomingDAO.visible_update(map);
	}



}
