package cn.stb.stbcrmserver.dao;

import cn.stb.stbcrmserver.domain.Staff;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StaffDao {
    List<Staff> queryAllStaff();

    int addStaff(Staff staff);

    int deleteStaffById(String staffId);

    int modifyStaffStateById(Staff staff);

    Staff findStaffByStaffCode(String staffCode);

    List<String> queryStaffRightIds(String staffId);
}
