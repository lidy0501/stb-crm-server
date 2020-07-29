package cn.stb.stbcrmserver.controller;

import cn.stb.stbcrmserver.base.LoginIgnore;
import cn.stb.stbcrmserver.base.RespResult;
import cn.stb.stbcrmserver.base.Right;
import cn.stb.stbcrmserver.domain.Staff;
import cn.stb.stbcrmserver.service.StaffService;
import cn.stb.stbcrmserver.vo.AddStaffReq;
import cn.stb.stbcrmserver.vo.StaffListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static cn.stb.stbcrmserver.base.RightType.CRM_员工管理;

@RestController
@RequestMapping("/StaffController")
public class StaffController {
    @Autowired
    private StaffService staffService;

    @RequestMapping("/queryAllStaff")
    @Right(CRM_员工管理)
    public List<StaffListVo> queryAllStaff (){
        return staffService.queryAllStaff();
    }

    @RequestMapping("/addStaff")
    @Right(CRM_员工管理)
    public RespResult addStaff(@RequestBody AddStaffReq req){
        return staffService.addStaff(req);
    }

    @RequestMapping("/deleteStaffById/{staffId}")
    @LoginIgnore
    public RespResult deleteStaffById(@PathVariable String staffId){
        return staffService.deleteStaffById(staffId);
    }

    @RequestMapping("/modifyStaffStateById")
    @Right(CRM_员工管理)
    public RespResult modifyStaffStateById(Staff staff){
        return staffService.modifyStaffStateById(staff);
    }
}
