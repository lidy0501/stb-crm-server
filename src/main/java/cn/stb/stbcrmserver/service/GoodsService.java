package cn.stb.stbcrmserver.service;

import cn.stb.stbcrmserver.base.RespResult;
import cn.stb.stbcrmserver.dao.GoodsDao;
import cn.stb.stbcrmserver.domain.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class GoodsService {
    @Autowired
    private GoodsDao goodsDao;

    public List<Goods> queryAllGoods() {
        return goodsDao.queryAllGoods();
    }

    public RespResult addGoods(Goods goods) {
        if (!StringUtils.isEmpty(goods.getGoodsName())) {
            int effectedNum = goodsDao.addGoods(goods);
            if (effectedNum > 0) {
                return RespResult.ok("新增商品成功!");
            } else {
                return RespResult.fail("新增商品失败!");
            }
        }else {
            return RespResult.fail("商品名称不能为空!");
        }
    }
    public RespResult deleteGoodsById (String goodsId){
        int effectedNum = goodsDao.deleteGoodsById(goodsId);
        if (effectedNum > 0) {
            return RespResult.ok("删除成功!");
        } else {
            return RespResult.fail("删除失败");
        }
    }
}