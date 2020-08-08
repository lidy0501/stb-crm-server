package cn.stb.stbcrmserver.service;

import cn.stb.stbcrmserver.base.RespResult;
import cn.stb.stbcrmserver.context.AcContext;
import cn.stb.stbcrmserver.dao.RightDao;
import cn.stb.stbcrmserver.dao.StaffDao;
import cn.stb.stbcrmserver.domain.Staff;
import cn.stb.stbcrmserver.domain.StaffRight;
import cn.stb.stbcrmserver.vo.AddStaffReq;
import cn.stb.stbcrmserver.vo.StaffListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StaffService {
    @Autowired
    private StaffDao staffDao;
    @Autowired
    private RightDao rightDao;


    public List<StaffListVo> queryAllStaff() {
        Staff staff = AcContext.getStaff();
        Map<String, String> map = new HashMap();
        map.put("staffType", staff.getStaffType());
        map.put("operatorId", staff.getStaffId());
        return staffDao.queryAllStaff(map);
    }

    @Transactional
    public RespResult addStaff(AddStaffReq req) {

        if (!StringUtils.isEmpty(req.getStaffName()) && !StringUtils.isEmpty(req.getStaffPhone()) &&
                !StringUtils.isEmpty(req.getStaffCode()) && !StringUtils.isEmpty(req.getPassWord())
                && !CollectionUtils.isEmpty(req.getRightVoList())) {
            //校验staffCode是否存在
            String staffCode = req.getStaffCode();
            Staff checkStaff = staffDao.findStaffByStaffCode(staffCode);
            if (checkStaff != null) return RespResult.fail("该账号已经存在!");
            // 落crm_staff表
            Staff staff = Staff.convert(req);
            int effectNum1 = staffDao.addStaff(staff);
            // 落 crm_staff_right表
            List<StaffRight> staffRightList = req.getRightVoList().stream().map(x ->
                StaffRight.convert(staff.getStaffId(), x.getRightId())).collect(Collectors.toList());
            int effectNum2 = rightDao.addStaffRight(staffRightList);


            if (effectNum1 > 0 && effectNum2 > 0) {
                return RespResult.ok("新增员工成功!");
            } else {
                return RespResult.fail("新增员工失败!");
            }
        } else {
            return RespResult.fail("员工信息不可为空!");
        }
    }

    @Transactional
    public RespResult deleteStaffById(String staffId) {
        //获取当前操作员信息
        Staff staff = AcContext.getStaff();
        //获取传入ID员工信息
        Staff staff1 = staffDao.findStaffById(staffId);
        if (staff1 == null) return  RespResult.fail("该员工不存在!");
        if (staffId.equals(staff.getStaffId()) || "0".equals(staff1.getStaffType())) { // 不能删除自己和系统管理员
            return RespResult.fail("删除失败!");
        }
        // 删除员工信息
        int effectNum1 = staffDao.deleteStaffById(staffId);
        // 删除员工的权限
        int effectNum2 = rightDao.deleteStaffRightsByStaffId(staffId);

        if (effectNum1 > 0 && effectNum2 > 0) return RespResult.ok("删除成功!");
        return RespResult.fail("删除失败!");
    }

    public RespResult modifyStaffStateById(Staff staff) {
        int effectNum = staffDao.modifyStaffStateById(staff);
        if (effectNum > 0) {
            return RespResult.ok("修改成功!");
        } else {
            return RespResult.fail("修改失败!");
        }
    }

    /**
     * 查询员工的权限id
     */

    public List<String> queryStaffRightIds(String staffId) {
        return staffDao.queryStaffRightIds(staffId);
    }

    /**
     * 获取所有员工信息
     */
    public List<Staff> queryAllStaffIgnoreState() {
        return staffDao.queryAllStaffIgnoreState();
    }


}
