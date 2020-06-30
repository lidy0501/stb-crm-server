package cn.stb.stbcrmserver.service;

import cn.stb.stbcrmserver.dao.RightDao;
import cn.stb.stbcrmserver.vo.RightVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RightService {

    @Autowired
    private RightDao rightDao;

    public List<String> queryRightIdsByRightCodes(List<String> rightCodes) {
        List<String> rightIds = rightDao.queryRightIdsByRightCodes(rightCodes);
        return rightIds;
    }

    public List<RightVo> getAll() {
        return rightDao.getAll();
    }
}
