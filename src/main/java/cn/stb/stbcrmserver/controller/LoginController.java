package cn.stb.stbcrmserver.controller;

import cn.stb.stbcrmserver.base.RespResult;
import cn.stb.stbcrmserver.context.AcContext;
import cn.stb.stbcrmserver.domain.Staff;
import cn.stb.stbcrmserver.service.LoginService;
import cn.stb.stbcrmserver.utils.CookieUtils;
import cn.stb.stbcrmserver.utils.SessionUtils;
import cn.stb.stbcrmserver.vo.LeftRightVo;
import cn.stb.stbcrmserver.vo.LoginReq;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/LoginController")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping("/login")
    public RespResult login(HttpServletRequest request, HttpServletResponse response, @RequestBody LoginReq loginReq) {
        val result = loginService.login(loginReq);
        if (result.isNotOk()) return result;
        // 缓存登录人信息
        Staff staff = (Staff)result.getData();
        SessionUtils.cacheStaffSession(request, staff);
        CookieUtils.cacheStaffInfo(response, staff);
        return RespResult.ok("登录成功", staff);
    }

    @RequestMapping("/getRight")
    public List<LeftRightVo> getRight() {
        String staffId = AcContext.getStaffId();
        return loginService.queryRightsByStaffId(staffId);
    }

    @RequestMapping("/loginOut")
    public RespResult loginOut(HttpServletRequest request,HttpServletResponse response) {
        return loginService.loginOut(request, response);
    }
}
