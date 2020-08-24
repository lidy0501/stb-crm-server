package cn.stb.stbcrmserver.controller;

import cn.stb.stbcrmserver.base.*;
import cn.stb.stbcrmserver.domain.User;
import cn.stb.stbcrmserver.service.UserService;
import cn.stb.stbcrmserver.vo.ListReq;
import cn.stb.stbcrmserver.vo.ListVo;
import cn.stb.stbcrmserver.vo.UserReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.print.DocFlavor;
import java.util.List;

import static cn.stb.stbcrmserver.base.RightType.CRM_客户管理;

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
    @Right(CRM_客户管理)
    public ListVo<User> queryPrivateUser(@RequestBody ListReq req) {
        return userService.queryUsersByStaffType("1", req);
    }

    /**
     * 显示公共区域客户
     * @return
     */
    @RequestMapping("/queryPublicAreaUser")
    @Right(CRM_客户管理)
    public ListVo<User> queryPublicAreaUser(@RequestBody ListReq req) { // userType : 0公共客户, 1 私有客户
        return userService.queryUsersByStaffType("0", req);
    }

    /**
     * 新增客户
     * @param user
     * @return
     */
    @RequestMapping("/addUser")
    @Right(CRM_客户管理)
    public RespResult addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    /**
     * 通过userId查询客户详情
     * @param userId
     * @return
     */
    @RequestMapping("/selectUserByUserId/{userId}")
    @Right(CRM_客户管理)
    public User selectUserByUserId(@PathVariable String userId){
        return userService.selectUserByUserId(userId);
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
     * 软删除
     * @param userId
     * @return
     */
    @RequestMapping("/deleteUserById/{userId}")
    @Right(CRM_客户管理)
    public RespResult deleteUserById(@PathVariable String userId){
        return userService.deleteUserById(userId);
    }

    /**
     * 模糊查询客户
     * @param s
     * @return
     */
    @RequestMapping("/selectUserByLike/{s}")
    @Right(CRM_客户管理)
    public List<User> selectUserByLike(@PathVariable String s){
        return userService.selectUserByLike(s);
    }

    @RequestMapping("/changeUserType/{userId}")
    @Right(CRM_客户管理)
    public RespResult changeUserType(@PathVariable String userId){
        return userService.changeUserType(userId);
    }

    /**
     * 从公海认领客户
     * @param userId
     * @return
     */
    @RequestMapping("/receiveUser/{userId}")
    @LoginIgnore
    public RespResult receiveUser(@PathVariable String userId){
        return userService.receiveUser(userId);
    }

    /**
     * 管理员从公海分配客户
     * @param req
     * @return
     */
    @RequestMapping("/distributionUser")
    @LoginIgnore
    public RespResult distributionUser(@RequestBody UserReq req){
        return userService.distributionUser(req);
    }
}
