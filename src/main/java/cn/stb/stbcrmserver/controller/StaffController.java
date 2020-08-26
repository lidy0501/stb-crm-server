package cn.stb.stbcrmserver.controller;

import cn.stb.stbcrmserver.base.RespResult;
import cn.stb.stbcrmserver.base.Right;
import cn.stb.stbcrmserver.domain.Staff;
import cn.stb.stbcrmserver.domain.StaffRight;
import cn.stb.stbcrmserver.service.RightService;
import cn.stb.stbcrmserver.service.StaffService;
import cn.stb.stbcrmserver.vo.AddStaffReq;
import cn.stb.stbcrmserver.vo.RightVo;
import cn.stb.stbcrmserver.vo.StaffListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cn.stb.stbcrmserver.base.RightType.CRM_员工管理;

@RestController
@RequestMapping("/StaffController")
public class StaffController {
    @Autowired
    private StaffService staffService;

    @Autowired
    private RightService rightService;

    @RequestMapping("/queryAllStaff")
    @Right({CRM_员工管理, CRM_员工管理})
    public List<StaffListVo> queryAllStaff (){
        return staffService.queryAllStaff();
    }

    @RequestMapping("/saveStaff")
    @Right(CRM_员工管理)
    public RespResult saveStaff(@RequestBody AddStaffReq req){
        return staffService.saveStaff(req);
    }

    @RequestMapping("/deleteStaffById/{staffId}")
    @Right(CRM_员工管理)
    public RespResult deleteStaffById(@PathVariable String staffId){
        return staffService.deleteStaffById(staffId);
    }

    @RequestMapping("/modifyStaffStateById")
    @Right(CRM_员工管理)
    public RespResult modifyStaffStateById(Staff staff){
        return staffService.modifyStaffStateById(staff);
    }

    @RequestMapping("/findStaffInfoById/{staffId}")
    @Right(CRM_员工管理)
    public AddStaffReq findStaffInfoById(@PathVariable String staffId) {
        if ("0".equals(staffId)) { // 新增
            return AddStaffReq.builder().rightVoList(rightService.getAll()).build();
        } else { // 编辑
            Staff staff = staffService.findStaffByStaffId(staffId);
            // 查出所有权限
            List<RightVo> rightVos = rightService.getAll();
            // 查出员工的权限
            List<StaffRight> staffRights = rightService.queryStaffRightByStaffId(staffId);
            for (StaffRight staffRight : staffRights) {
                for (RightVo rightVo : rightVos) {
                    if (rightVo.getRightId().equals(staffRight.getRightId())) {
                        rightVo.setSelected(true);
                    }
                }
            }
            return AddStaffReq.convert(staff, rightVos);
        }
    }


}
