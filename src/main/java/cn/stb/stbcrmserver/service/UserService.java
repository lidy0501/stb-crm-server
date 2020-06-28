package cn.stb.stbcrmserver.service;

import cn.stb.stbcrmserver.base.RespResult;
import cn.stb.stbcrmserver.context.AcContext;
import cn.stb.stbcrmserver.dao.UserDao;
import cn.stb.stbcrmserver.domain.Staff;
import cn.stb.stbcrmserver.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    // 新增客户,默认公司客户
    public RespResult addUser(User user) {
        String operatorId = AcContext.getStaffId();
        user.setUserType("1"); //私有
        user.setUserState("1"); // 有效
        user.setOperatorId(operatorId);
        int effectNum = userDao.addUser(user);
        if(effectNum > 0 ) return RespResult.ok("新增客户成功!");
        return RespResult.fail("新增客户失败!");
    }

    public RespResult modifyUser(User user) {
        int effectNum = userDao.modifyUser(user);
        if (effectNum > 0) return RespResult.ok("修改成功!");
        return RespResult.fail("修改失败!");
    }

    public RespResult deleteUserById(String userId) {
        int effectNum = userDao.deleteUserById(userId);
        if (effectNum > 0 ) return RespResult.ok("删除成功!");
        return RespResult.fail("删除失败!");
    }

    // 查询客户,通过客户类型  userType : 0公共客户, 1 私有客户
    public List<User> queryUsersByStaffType(String userType) {
        Staff staff = AcContext.getStaff();
        String operatorId = staff.getStaffId();
        String staffType = staff.getStaffType();
        Map map = new HashMap();
        map.put("staffType", staffType);
        map.put("operatorId", operatorId);
        map.put("userType", userType);
        List<User> userList = userDao.queryUserByOperatorIdAndUserType(map);
        return userList;
    }

    public List<User> selectUserByLike(String s) {
        return userDao.selectUserByLike(s);
    }
}
