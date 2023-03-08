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
	
	public IncomingVO select(HashMap<String, Object> map) {
		IncomingVO incomingVO = incomingDAO.select(map);
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



}
