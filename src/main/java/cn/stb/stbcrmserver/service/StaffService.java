package cn.stb.stbcrmserver.service;

import cn.stb.stbcrmserver.base.RespResult;
import cn.stb.stbcrmserver.context.AcContext;
import cn.stb.stbcrmserver.dao.RightDao;
import cn.stb.stbcrmserver.dao.StaffDao;
import cn.stb.stbcrmserver.domain.Staff;
import cn.stb.stbcrmserver.domain.StaffRight;
import cn.stb.stbcrmserver.vo.AddStaffReq;
import cn.stb.stbcrmserver.vo.RightVo;
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
        List<StaffListVo> staffList = staffDao.queryAllStaff(map);
        staffList.forEach(x -> {
            if ("A000".equals(x.getStaffCode())) {
                x.setPassword("******");
            }
        });
        return staffList;
    }

    @Transactional
    public RespResult saveStaff(AddStaffReq req) {
        if (!StringUtils.isEmpty(req.getStaffName()) && !StringUtils.isEmpty(req.getStaffPhone()) &&
                !StringUtils.isEmpty(req.getStaffCode()) && !StringUtils.isEmpty(req.getPassWord())
                && !CollectionUtils.isEmpty(req.getRightVoList())) {
            //校验staffCode是否存在
            String staffCode = req.getStaffCode();
            Staff checkStaff = staffDao.findStaffByStaffCode(staffCode);
            if (checkStaff != null && !checkStaff.getStaffId().equals(req.getStaffId())) return RespResult.fail("该账号已经存在!");

            req.setRightVoList(getRightSelected(req.getRightVoList()));
            if ("0".equals(req.getStaffId())) { // 新增
                return addStaff(req);
            } else { // 编辑
                return editStaff(req);
            }
        }
        return RespResult.fail("员工信息不可为空!");


    }

    /** 新增员工 */
    @Transactional
    public RespResult addStaff (AddStaffReq req) {
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
    }

    /** 编辑员工 */
    @Transactional
    public RespResult editStaff (AddStaffReq req) {
        // 注意：编辑时不要变更操作人的id(否则会找不到真正的创建人)
        Staff staff = Staff.convert(req);
        // 更新员工基本信息
        int effectNum1 = staffDao.updateStaff(staff);

        // 先删除之前的权限，在插入新的权限
        int effectNum2 = rightDao.deleteStaffRightsByStaffId(staff.getStaffId());
        List<StaffRight> staffRightList = req.getRightVoList().stream().map(x ->
                StaffRight.convert(staff.getStaffId(), x.getRightId())).collect(Collectors.toList());
        int effectNum3 = rightDao.addStaffRight(staffRightList);
        if (effectNum1 > 0 && effectNum2 > 0 && effectNum3 > 0) {
            return RespResult.ok("编辑员工成功!");
        } else {
            return RespResult.fail("编辑员工失败!");
        }
    }

    /** 从前台传来的right集合里取出勾选的员工权限 */
    public List<RightVo> getRightSelected(List<RightVo> rightVos) {
        return rightVos.stream().filter(x ->x.isSelected()).collect(Collectors.toList());
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
        // 删除员工信息(软删)
        Map<String, String> map = new HashMap<>();
        map.put("staffId", staffId);
        map.put("staffState", "9");
        int effectNum1 = staffDao.changeStaffState(map);
        // 删除员工的权限(软删)
        int effectNum2 = rightDao.nullifyRightsByStaffId(staffId);
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
     * 获取员工信息
     */
    public Staff findStaffByStaffId(String staffId) {
        return staffDao.findStaffById(staffId);
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


	public Staff findStaffById(String staffId) {
        return staffDao.findStaffById(staffId);
	}
}
