package cn.stb.stbcrmserver.dao;

import cn.stb.stbcrmserver.domain.Test;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TestDao {

	List<Test> queryTest();

	Test findTestById();
}
