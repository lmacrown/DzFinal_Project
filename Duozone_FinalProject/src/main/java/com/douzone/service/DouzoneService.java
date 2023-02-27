package com.douzone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.DAO.DouzoneDAO;
import com.douzone.entity.DouzoneVO;


@Service
public class DouzoneService {
	
	@Autowired
	DouzoneDAO douzoneDAO;
	
	public DouzoneVO login(String MEMBER_ID, String MEMBER_PW) {
		DouzoneVO douzoneVO = douzoneDAO.login(MEMBER_ID, MEMBER_PW);
		return douzoneVO;
	}


}
