package cn.stb.stbcrmserver.service;

import cn.stb.stbcrmserver.base.RespResult;
import cn.stb.stbcrmserver.context.AcContext;
import cn.stb.stbcrmserver.dao.RightDao;
import cn.stb.stbcrmserver.dao.StaffDao;
import cn.stb.stbcrmserver.domain.*;
import cn.stb.stbcrmserver.vo.LeftRightVo;
import cn.stb.stbcrmserver.vo.LoginReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LoginService {

    @Autowired
    private StaffDao staffDao;

    @Autowired
    protected RightDao rightDao;

    public RespResult login(LoginReq loginReq) {
        log.info(loginReq.toString());
        Staff staff = staffDao.findStaffByStaffCode(loginReq.getStaffCode());
        if (staff == null || !staff.stateIsOk()) return RespResult.fail("该账号不是系统员工");

        if (!staff.getPassWord().equals(loginReq.getPassword())) return RespResult.fail("密码错误");
        System.out.println("staffId ==== " + AcContext.getStaffId());

        return RespResult.ok("验证通过", staff);
    }

    // 根据员工的id获取员工权限
    public List<LeftRightVo> queryRightsByStaffId(String staffId) {
        List<StaffRight> staffRightList = rightDao.queryStaffRightByStaffId(staffId);
        List<String> rightIds = staffRightList.stream().map(StaffRight::getRightId).collect(Collectors.toList());
        List<Right> rightList = rightDao.queryRightsByRightIds(rightIds);
        List<RightMenu> rightMenuList = rightDao.queryRightMenusByRightIds(rightIds);
        List<String> menuIds = rightMenuList.stream().map(RightMenu::getMenuId).collect(Collectors.toList());
        Map<String, List<RightMenu>> rightMenuMap = rightMenuList.stream().collect(Collectors.groupingBy(RightMenu::getRightId));
        List<Menu> menuList = rightDao.queryMenusByMenuIds(menuIds);
        Map<String, List<Menu>> menuListMap = rightIds.stream().collect(Collectors.toMap(x->x, x-> {
            List<RightMenu> rms = rightMenuMap.get(x);
            List<String> meIds = rms.stream().map(RightMenu::getMenuId).collect(Collectors.toList());
            List<Menu> menus = menuList.stream().filter(y -> meIds.contains(y.getMenuId())).collect(Collectors.toList());
            return menus;
        }));

        List<LeftRightVo> rightVoList = rightList.stream().map(x ->
            LeftRightVo.builder()
                    .rightId(x.getRightId())
                    .rightName(x.getRightName())
                    .menus(menuListMap.get(x.getRightId()))
                    .build()
        ).collect(Collectors.toList());
        return rightVoList;
    }
}
