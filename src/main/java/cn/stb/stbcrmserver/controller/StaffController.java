package cn.stb.stbcrmserver.controller;

import cn.stb.stbcrmserver.base.LoginIgnore;
import cn.stb.stbcrmserver.base.RespResult;
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

@RestController
@RequestMapping("/StaffController")
public class StaffController {
    @Autowired
    private StaffService staffService;

    @RequestMapping("/queryAllStaff")
    @LoginIgnore
    public List<StaffListVo> queryAllStaff (){
        return staffService.queryAllStaff();
    }

    @RequestMapping("/addStaff")
    @LoginIgnore
    public RespResult addStaff(@RequestBody AddStaffReq req){
        return staffService.addStaff(req);
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
