package cn.stb.stbcrmserver.controller;

import cn.stb.stbcrmserver.domain.Test;
import cn.stb.stbcrmserver.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("/TestController")
public class TestController {

	@Autowired
	private TestService testService;


	@RequestMapping("/queryAllTest")
	public List<Test> queryAllTest () {
		return testService.queryAllTest();
	}

	@RequestMapping("/findTestById/{id}")
	public Test findTestById(@PathVariable String id) {
		return testService.findTestById(id);
	}

}
