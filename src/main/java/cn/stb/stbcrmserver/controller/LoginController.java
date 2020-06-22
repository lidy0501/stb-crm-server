package cn.stb.stbcrmserver.controller;

import cn.stb.stbcrmserver.base.RespResult;
import cn.stb.stbcrmserver.service.LoginService;
import cn.stb.stbcrmserver.vo.LoginReq;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/LoginController")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping("/login")
    public RespResult login(HttpServletRequest request, HttpServletResponse response, @RequestBody LoginReq loginReq) {
//        val resp = loginService.login(loginReq);
//        return RespResult.ok("登录成功");
        return loginService.login(loginReq);
    }

}
