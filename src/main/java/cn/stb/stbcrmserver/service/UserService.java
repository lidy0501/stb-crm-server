package cn.stb.stbcrmserver.service;

import cn.stb.stbcrmserver.base.Page;
import cn.stb.stbcrmserver.base.RespResult;
import cn.stb.stbcrmserver.context.AcContext;
import cn.stb.stbcrmserver.dao.StaffDao;
import cn.stb.stbcrmserver.dao.UserDao;
import cn.stb.stbcrmserver.domain.Staff;
import cn.stb.stbcrmserver.domain.User;
import cn.stb.stbcrmserver.utils.UUIDUtil;
import cn.stb.stbcrmserver.vo.ListReq;
import cn.stb.stbcrmserver.vo.ListVo;
import cn.stb.stbcrmserver.vo.UserListVo;
import cn.stb.stbcrmserver.vo.UserReq;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private StaffDao staffDao;

    // 新增客户,默认公司客户
    public RespResult addUser(User user) {
        if (!StringUtils.isEmpty(user.getUserId())) { // 编辑客户备注
            int row = userDao.updateUserRemark(user);
            return RespResult.ok("保存成功");
        }
        String userCode = user.getUserCode();
        User checkUser = userDao.findUserByUserCode(userCode);
        if (checkUser != null && !user.getUserId().equals(checkUser.getUserId())) return RespResult.fail("该账号已经存在!");
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
    public ListVo<UserListVo> queryUsersByStaffType(String userType, ListReq req) {
        Page page = new Page();
        page.setStartIndex(req.getStartIndex());
        page.setPageRows(10);
        Staff staff = AcContext.getStaff();
        String operatorId = staff.getStaffId();
        String staffType = staff.getStaffType();
        Map<String, String> map = new HashMap();
        map.put("staffType", staffType);
        map.put("operatorId", operatorId);
        map.put("userType", userType);
        map.put("searchValue", req.getSearchValue());
        List<User> users = userDao.queryUserByOperatorIdAndUserType(map);
        if (CollectionUtils.isEmpty(users)) {
            return new ListVo(new ArrayList(), page);
        }
        log.info("user--------------, {}", users.get(0));
        List<User> userList = users.stream().skip(page.getStartIndex()).limit(10).collect(Collectors.toList());
        List<String> operatorIds = userList.stream().map(User::getOperatorId).collect(Collectors.toList());
        Map<String, String> nameMap = getOperatorNameMap(operatorIds);
        List<UserListVo> userListVos = userList.stream().map(user -> UserListVo.convert(user, nameMap.get(user.getOperatorId()))).collect(Collectors.toList());

        page.setTotalRows(users.size());
        ListVo<UserListVo> listVo = new ListVo(userListVos, page);
        return listVo;
    }

    /**
     * 获取跟进客户的员工姓名
     * @param operatorIds
     * @return
     */
    public Map<String, String> getOperatorNameMap(List<String> operatorIds) {
        List<Staff> staffList = staffDao.queryStaffsByIds(operatorIds);
        Map<String, String> staffMap = staffList.stream().collect(Collectors.toMap(Staff::getStaffId, Staff::getStaffName));
        return staffMap;
    }







    public List<User> selectUserByLike(String s) {
        return userDao.selectUserByLike(s);
    }

    public RespResult changeUserType(String userId) {
        String operatorId = AcContext.getStaffId();
        Map<String, String> map = new HashMap();
        map.put("userId",userId);
        map.put("operatorId",operatorId);
        int effectNum = userDao.changeUserType(map);
        if (effectNum > 0 ) return RespResult.ok("操作成功!");
        return RespResult.fail("操作失败!");
    }

    public RespResult receiveUser(String userId) {
        String operatorId = AcContext.getStaffId();
        Map<String , String> map = new HashMap<>();
        map.put("userId",userId);
        map.put("operatorId",operatorId);
        int effectNum = userDao.receiveUser(map);
        if (effectNum > 0 ) return  RespResult.ok("认领成功!");
        return RespResult.fail("认领失败!");
    }

    public RespResult distributionUser(UserReq req) {
        String staffType = AcContext.getStaff().getStaffType();
        if ("0".equals(staffType) || "1".equals(staffType)){
            Map<String , String> map = new HashMap<>();
            map.put("userId",req.getUserId());
            map.put("operatorId",req.getStaffId());
            int effectNum = userDao.receiveUser(map);
            if (effectNum > 0 ) return  RespResult.ok("分配成功!");
            return RespResult.fail("分配失败!");
        }
        return RespResult.fail("无此权限!");
    }

    public User selectUserByUserId(String userId) {
        return userDao.selectUserByUserId(userId);
    }
}
