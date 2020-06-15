package cn.stb.stbcrmserver.service;

import cn.stb.stbcrmserver.dao.TestDao;
import cn.stb.stbcrmserver.domain.Test;
import cn.stb.stbcrmserver.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {

	@Autowired
	private TestDao testDao;
	@Autowired
	private RedisUtil redisUtil;

	public List<Test> queryAllTest(){
		redisUtil.set("name", "小明");
		String name = redisUtil.get("name").toString();
		System.out.println("name==" + name);
		return testDao.queryTest();
	}
}
