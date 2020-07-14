package cn.stb.stbcrmserver.service;

import cn.stb.stbcrmserver.base.Page;
import cn.stb.stbcrmserver.base.RespResult;
import cn.stb.stbcrmserver.context.AcContext;
import cn.stb.stbcrmserver.dao.UserDao;
import cn.stb.stbcrmserver.domain.Staff;
import cn.stb.stbcrmserver.domain.User;
import cn.stb.stbcrmserver.utils.UUIDUtil;
import cn.stb.stbcrmserver.vo.ListReq;
import cn.stb.stbcrmserver.vo.ListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    // 新增客户,默认公司客户
    public RespResult addUser(User user) {
        user.setUserId(UUIDUtil.getNumId());
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

    /**
     * 软删除
     * @param userId
     * @return
     */
    public RespResult deleteUserById(String userId) {
        User user = userDao.findUserById(userId);
        if (user != null && "0".equals(user.getUserType()) ) { // 公共客户
            Staff staff = AcContext.getStaff();
            if (!"0".equals(staff.getStaffType())) {
                return RespResult.fail("删除失败!");
            }
        }
        String operatorId = AcContext.getStaffId();
        Map<String, String> map = new HashMap<>();
        map.put("userId", userId);
        map.put("operatorId", operatorId);
        int effectNum = userDao.deleteUserById(map);
        if (effectNum > 0 ) return RespResult.ok("删除成功!");
        return RespResult.fail("删除失败!");
    }

    // 查询客户,通过客户类型  userType : 0公共客户, 1 私有客户
    public ListVo<User> queryUsersByStaffType(String userType, ListReq req) {
        Page page = new Page();
        page.setStartIndex(req.getStartIndex());
        page.setPageRows(10);
        Staff staff = AcContext.getStaff();
        String operatorId = staff.getStaffId();
        String staffType = staff.getStaffType();
        Map map = new HashMap();
        map.put("staffType", staffType);
        map.put("operatorId", operatorId);
        map.put("userType", userType);
        map.put("searchValue", req.getSearchValue());
        List<User> users = userDao.queryUserByOperatorIdAndUserType(map);
        List<User> userList = users.stream().skip(page.getStartIndex()).limit(10).collect(Collectors.toList());
        page.setTotalRows(users.size());
        ListVo<User> listVo = new ListVo(userList, page);
        return listVo;
    }

    public List<User> selectUserByLike(String s) {
        return userDao.selectUserByLike(s);
    }

    public RespResult changeUserType(String userId) {
        String operatorId = AcContext.getStaffId();
        Map map = new HashMap();
        map.put("userId",userId);
        map.put("operatorId",operatorId);
        int effectNum = userDao.changeUserType(map);
        if (effectNum > 0 ) return RespResult.ok("操作成功!");
        return RespResult.fail("操作失败!");
    }
}
