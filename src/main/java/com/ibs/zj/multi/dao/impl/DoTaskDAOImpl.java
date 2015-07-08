package com.ibs.zj.multi.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ibs.common.core.mybatis.dao.impl.BaseDAOImpl;
import com.ibs.zj.multi.dao.DoTaskDAO;
import com.ibs.zj.multi.model.UserGoods;

@Repository
public class DoTaskDAOImpl extends BaseDAOImpl implements DoTaskDAO{

	private static final String NAMESPACE = "com.ibs.zj.multi.ThreadMapper";
	
	public List<UserGoods> queryUserGoodsByName(String userName) {
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("userName", userName);
		return this.selectList(NAMESPACE+".queryUserGoodsByName", dataMap);
	}

}
