package cn.stb.stbcrmserver.dao;

import cn.stb.stbcrmserver.domain.Staff;
import cn.stb.stbcrmserver.vo.StaffListVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface StaffDao {
    List<StaffListVo> queryAllStaff(Map<String, String> map);

    int addStaff(Staff staff);

    int deleteStaffById(String staffId);

    int modifyStaffStateById(Staff staff);

    Staff findStaffByStaffCode(String staffCode);

    List<String> queryStaffRightIds(String staffId);

    Staff findStaffById(String staffId);

    List<Staff> queryAllStaffIgnoreState();

    int updateStaff(Staff staff);

    int changeStaffState(Map<String, String> map);

    List<Staff> queryStaffsByIds(List<String> operatorIds);

    Staff findStaffByNameOrCode(String searchValue);
}
