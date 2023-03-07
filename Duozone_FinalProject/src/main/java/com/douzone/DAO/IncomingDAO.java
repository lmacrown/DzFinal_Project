package com.douzone.DAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.douzone.entity.IncomingVO;

@Mapper
public interface IncomingDAO {
	IncomingVO earner_list(HashMap<String, Object> map);
	
	IncomingVO income_category(HashMap<String, Object> map);

	void regist(HashMap<String, Object> map);

	Map<String, Object> test1(HashMap<String, Object> map);

	Map<String, Object> searchearner(HashMap<String, Object> map);

	void visible_update(HashMap<String, Object> map);
}
