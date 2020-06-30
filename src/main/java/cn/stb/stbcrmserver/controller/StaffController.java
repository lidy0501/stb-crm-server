package cn.stb.stbcrmserver.controller;

import cn.stb.stbcrmserver.base.RespResult;
import cn.stb.stbcrmserver.domain.Staff;
import cn.stb.stbcrmserver.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/StaffController")
public class StaffController {
    @Autowired
    private StaffService staffService;

    @RequestMapping("/queryAllStaff")
    public List<Staff> queryAllStaff (){
        return staffService.queryAllStaff();
    }

    @RequestMapping("/addStaff")
    public RespResult addStaff(Staff staff){
        return staffService.addStaff(staff);
    }
    @RequestMapping("/deleteStaffById/{staffId}")
    public RespResult deleteStaffById(@PathVariable String staffId){
        return staffService.deleteStaffById(staffId);
    }

    @RequestMapping("/modifyStaffStateById")
    public RespResult modifyStaffStateById(Staff staff){
        return staffService.modifyStaffStateById(staff);
    }
}