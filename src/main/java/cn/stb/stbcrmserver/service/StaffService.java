package cn.stb.stbcrmserver.service;

import cn.stb.stbcrmserver.base.RespResult;
import cn.stb.stbcrmserver.dao.RightDao;
import cn.stb.stbcrmserver.dao.StaffDao;
import cn.stb.stbcrmserver.domain.Staff;
import cn.stb.stbcrmserver.domain.StaffRight;
import cn.stb.stbcrmserver.vo.AddStaffReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StaffService {
    @Autowired
    private StaffDao staffDao;
    @Autowired
    private RightDao rightDao;


    public List<Staff> queryAllStaff() {
        return staffDao.queryAllStaff();
    }

    @Transactional
    public RespResult addStaff(AddStaffReq req) {

        if (!StringUtils.isEmpty(req.getStaffName()) && !StringUtils.isEmpty(req.getStaffPhone()) &&
                !StringUtils.isEmpty(req.getStaffCode()) && !StringUtils.isEmpty(req.getPassWord())
                && !CollectionUtils.isEmpty(req.getRightVoList())) {
            //校验staffCode是否存在
            String staffCode = req.getStaffCode();
            String effectNum = staffDao.getAllStaffCode(staffCode);
            if (effectNum != null) return RespResult.fail("该用户代码已经存在!");
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

    public RespResult deleteStaffById(String staffId) {
        int effectNum = staffDao.deleteStaffById(staffId);
        if (effectNum > 0) {
            return RespResult.ok("删除成功!");
        } else {
            return RespResult.fail("删除失败!");
        }
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


}
