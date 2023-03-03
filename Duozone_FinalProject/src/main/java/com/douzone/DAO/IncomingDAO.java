package com.douzone.DAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.douzone.entity.IncomingVO;

@Mapper
public interface IncomingDAO {
	IncomingVO select(HashMap<String, Object> map);

	void regist(HashMap<String, Object> map);

	Map<String, Object> test1(HashMap<String, Object> map);

	Map<String, Object> searchearner(HashMap<String, Object> map);
}
