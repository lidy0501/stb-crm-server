package cn.stb.stbcrmserver.controller;

import cn.stb.stbcrmserver.base.LoginIgnore;
import cn.stb.stbcrmserver.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/TemplateController")
public class TemplateController {
	@Autowired
	private TemplateService templateService;

	@RequestMapping("/downLoadTest")
	@LoginIgnore
	public void downLoad(@RequestParam String template, HttpServletResponse response) {
		try {
			//templateService.downLoad(response);
			//templateService.buildExcelDocument(response);
			templateService.downloadTemp(template, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
