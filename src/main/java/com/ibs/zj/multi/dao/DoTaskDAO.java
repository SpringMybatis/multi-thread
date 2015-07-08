package com.ibs.zj.multi.dao;

import java.util.List;

import com.ibs.common.core.mybatis.dao.BaseDAO;
import com.ibs.zj.multi.model.UserGoods;

public interface DoTaskDAO extends BaseDAO {

	public List<UserGoods> queryUserGoodsByName(String userName);
	
}
