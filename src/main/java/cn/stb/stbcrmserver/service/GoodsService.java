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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GoodsService {
    @Autowired
    private GoodsDao goodsDao;

    public List<Goods> queryAllGoods() {
        return goodsDao.queryAllGoods();
    }

    public RespResult addGoods(Goods goods) {
        String goodsCode = goods.getGoodsCode();
        Goods goodsByCode = goodsDao.findGoodsByCode(goodsCode);
        if (goodsByCode != null) {
            return RespResult.fail("该商品编码已存在，请重新填写！");
        }
        String operatorId = AcContext.getStaffId();
        goods.setGoodsId(UUIDUtil.getNumId());
        goods.setOperatorId(operatorId);
        int effectedNum = goodsDao.addGoods(goods);
        if (effectedNum > 0) {
            return RespResult.ok("新增商品成功!");
        } else {
            return RespResult.fail("新增商品失败!");
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
        if (goods == null){
            String operatorId = AcContext.getStaffId();
            Map<String, String> map = new HashMap<>();
            map.put("operatorId", operatorId);
            map.put("skuId", skuId);
            goodsDao.deleteSkuById(map);
            return RespResult.ok("删除成功!");
        }
        return RespResult.fail("该SKU正在用于商品" + goods.getGoodsName() + "，不能删除");
    }

    /** SKU列表 */
	public List<Sku> querySkuList() {
	    return goodsDao.querySkuList();
	}
}