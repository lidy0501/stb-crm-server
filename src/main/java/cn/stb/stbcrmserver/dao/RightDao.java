package cn.stb.stbcrmserver.dao;

import cn.stb.stbcrmserver.domain.Menu;
import cn.stb.stbcrmserver.domain.Right;
import cn.stb.stbcrmserver.domain.RightMenu;
import cn.stb.stbcrmserver.domain.StaffRight;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RightDao {
    List<StaffRight> queryStaffRightByStaffId(String staffId);

    List<Right> queryRightsByRightIds(List<String> rightIds);

    List<RightMenu> queryRightMenusByRightIds(List<String> rightIds);

    List<Menu> queryMenusByMenuIds(List<String> menuIds);
}
