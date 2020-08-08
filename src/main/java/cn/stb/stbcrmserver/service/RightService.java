package cn.stb.stbcrmserver.service;

import cn.stb.stbcrmserver.context.AcContext;
import cn.stb.stbcrmserver.dao.RightDao;
import cn.stb.stbcrmserver.domain.Staff;
import cn.stb.stbcrmserver.vo.RightVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RightService {

    @Autowired
    private RightDao rightDao;

    public List<String> queryRightIdsByRightCodes(List<String> rightCodes) {
        List<String> rightIds = rightDao.queryRightIdsByRightCodes(rightCodes);
        return rightIds;
    }

    public List<RightVo> getAll() {
        Staff staff = AcContext.getStaff();
        List<RightVo> rightVos = rightDao.getAll();
        if (!"0".equals(staff.getStaffType())) { // 不是老板，不能展示财务管理的选项
            // 不是老板，不能展示财务管理 和工管理的选项
            rightVos = rightVos.stream().filter(x -> !"财务管理".equals(x.getRightName()) && !"员工管理".equals(x.getRightName())).collect(Collectors.toList());

        }
        return rightVos;
    }
}
