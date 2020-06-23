package cn.stb.stbcrmserver.service;

import cn.stb.stbcrmserver.base.RespResult;
import cn.stb.stbcrmserver.dao.StaffDao;
import cn.stb.stbcrmserver.domain.Staff;
import cn.stb.stbcrmserver.vo.LoginReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LoginService {

    @Autowired
    private StaffDao staffDao;

    public RespResult login(LoginReq loginReq) {
        log.info(loginReq.toString());
        Staff staff = staffDao.findStaffByStaffCode(loginReq.getStaffCode());
        if (staff == null || !staff.stateIsOk()) return RespResult.fail("该账号不是系统员工");

        if (!staff.getPassWord().equals(loginReq.getPassword())) return RespResult.fail("密码错误");

        return RespResult.ok("验证通过", staff);
    }
}
