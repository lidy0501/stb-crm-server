package cn.stb.stbcrmserver.controller;

import cn.stb.stbcrmserver.base.*;
import cn.stb.stbcrmserver.domain.User;
import cn.stb.stbcrmserver.service.UserService;
import cn.stb.stbcrmserver.vo.ListReq;
import cn.stb.stbcrmserver.vo.ListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/UserController")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 显示公司区域客户
     * @return
     */
    @RequestMapping("/queryPrivateUser")
    @LoginIgnore
    public ListVo<User> queryPrivateUser(@RequestBody ListReq req) {
        return userService.queryUsersByStaffType("1", req);
    }

    /**
     * 显示公共区域客户
     * @return
     */
    @RequestMapping("/queryPublicAreaUser")
    @Right(RightType.CRM_员工管理)
    public ListVo<User> queryPublicAreaUser(@RequestBody ListReq req) { // userType : 0公共客户, 1 私有客户
        return userService.queryUsersByStaffType("0", req);
    }

    /**
     * 新增客户
     * @param user
     * @return
     */
    @RequestMapping("/addUser")
    @LoginIgnore
    public RespResult addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    /**
     * 通过客户ID修改状态(修改状态,软删除)
     * @param user
     * @return
     */
    @RequestMapping("/modifyUser")
    @LoginIgnore
    public RespResult modifyUser(@RequestBody User user){
        return userService.modifyUser(user);
    }

    /**
     * 硬删除
     * @param userId
     * @return
     */
    @RequestMapping("/deleteUserById/{userId}")
    @LoginIgnore
    public RespResult deleteUserById(@PathVariable String userId){
        return userService.deleteUserById(userId);
    }

    /**
     * 模糊查询客户
     * @param s
     * @return
     */
    @RequestMapping("/selectUserByLike/{s}")
    @LoginIgnore
    public List<User> selectUserByLike(@PathVariable String s){
        return userService.selectUserByLike(s);
    }

    @RequestMapping("/changeUserType/{userId}")
    @LoginIgnore
    public RespResult changeUserType(@PathVariable String userId){
        return userService.changeUserType(userId);
    }
}
