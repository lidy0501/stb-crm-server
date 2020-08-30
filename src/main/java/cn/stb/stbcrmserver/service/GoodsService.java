package cn.stb.stbcrmserver.service;

import cn.stb.stbcrmserver.base.RespResult;
import cn.stb.stbcrmserver.context.AcContext;
import cn.stb.stbcrmserver.dao.GoodsDao;
import cn.stb.stbcrmserver.domain.Goods;
import cn.stb.stbcrmserver.domain.Sku;
import cn.stb.stbcrmserver.utils.UUIDUtil;
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
        String operatorId = AcContext.getStaffId();
        if (!StringUtils.isEmpty(goods.getGoodsName())) {
            goods.setGoodsId(UUIDUtil.getNumId());
            goods.setGoodsState("0");
            goods.setOperatorId(operatorId);
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
        String staffType = AcContext.getStaff().getStaffType();
        if ("0".equals(staffType) || "1".equals(staffType)){
            int effectedNum = goodsDao.deleteGoodsById(goodsId);
            if (effectedNum > 0) return RespResult.ok("删除成功!");
            return RespResult.fail("删除失败");
        }
        return RespResult.fail("无此权限!");
    }

    public RespResult addSku(List<Sku> skuList) {
        String operatorId = AcContext.getStaffId();
        if (skuList != null && skuList.size() > 0) {
            skuList.forEach(sku -> {
                sku.setOperatorId(operatorId);
                sku.setSkuId(UUIDUtil.getNumId());
            });
            goodsDao.addSku(skuList);
            return RespResult.ok("保存成功");
        }
        return RespResult.fail("保存失败");
    }

    public RespResult deleteSkuById(String skuId) {
        Goods goods = goodsDao.selectGoodsBySkuId(skuId);
        if (StringUtils.isEmpty(goods.getGoodsId())){
            goodsDao.deleteSkuById(skuId);
            return RespResult.ok("删除成功!");
        }
        return RespResult.fail("删除失败");
    }

    /** SKU列表 */
	public List<Sku> querySkuList() {
	    return goodsDao.querySkuList();
	}
}