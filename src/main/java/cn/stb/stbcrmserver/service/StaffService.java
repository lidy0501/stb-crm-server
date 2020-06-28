package cn.stb.stbcrmserver.service;

import cn.stb.stbcrmserver.base.RespResult;
import cn.stb.stbcrmserver.dao.StaffDao;
import cn.stb.stbcrmserver.domain.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class StaffService {
    @Autowired
    private StaffDao staffDao;


    public List<Staff> queryAllStaff() {
        return staffDao.queryAllStaff();
    }

    @Transactional
    public RespResult addStaff(Staff staff) {
        if(!StringUtils.isEmpty(staff.getStaffName()) &&
                !StringUtils.isEmpty(staff.getStaffType()) &&
                !StringUtils.isEmpty(staff.getStaffState()) &&
                !StringUtils.isEmpty(staff.getOperatorId())) {
            // todo 逻辑
                int effectNum = staffDao.addStaff(staff);
                if (effectNum>0){
                    return RespResult.ok("新增员工成功!");
                }else {
                    return RespResult.fail("新增员工失败!");
                }
        }else {
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
