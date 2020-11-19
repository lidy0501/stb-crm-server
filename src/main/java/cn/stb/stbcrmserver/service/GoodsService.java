package cn.stb.stbcrmserver.service;

import cn.stb.stbcrmserver.base.Page;
import cn.stb.stbcrmserver.base.RespResult;
import cn.stb.stbcrmserver.context.AcContext;
import cn.stb.stbcrmserver.dao.GoodsDao;
import cn.stb.stbcrmserver.domain.Goods;
import cn.stb.stbcrmserver.domain.OrderGoods;
import cn.stb.stbcrmserver.domain.Sku;
import cn.stb.stbcrmserver.utils.UUIDUtil;
import cn.stb.stbcrmserver.vo.GoodsListVo;
import cn.stb.stbcrmserver.vo.ListReq;
import cn.stb.stbcrmserver.vo.ListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GoodsService {
    @Autowired
    private GoodsDao goodsDao;

    public ListVo<GoodsListVo> queryAllGoods(ListReq req) {
        int startIndex = req.getStartIndex();
        Page page = new Page(startIndex, 10);
        List<Goods> goodsList =  goodsDao.queryAllGoods();
        page.setTotalRows(goodsList.size());
        if (goodsList.size() == 0) {
            return new ListVo<>(new ArrayList<GoodsListVo>(), page);
        }
        goodsList = goodsList.stream().skip(startIndex).limit(10).collect(Collectors.toList());
        //List<String> skuIds = goodsList.stream().map(Goods::getSkuId).collect(Collectors.toList());
        //List<Sku> skuList = goodsDao.querySkuListByIds(skuIds);
        //Map<String, Sku> skuMap = skuList.stream().collect(Collectors.toMap(Sku::getSkuId, x -> x));
        List<GoodsListVo> goodsListVos = goodsList.stream().map(goods -> GoodsListVo.convert(goods)).collect(Collectors.toList());
        ListVo<GoodsListVo> data = new ListVo<>(goodsListVos, page);
        return data;
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
        // todo 校验该商品是否已经存在订单中，订单中存在的商品不能删
        List<OrderGoods> orderGoodsList = goodsDao.queryOrderGoodsByGoodsId(goodsId);
        if (orderGoodsList.size() > 0) {
            return RespResult.fail("该商品已经存在订单中，不能删除");
        }
        String operatorId = AcContext.getStaffId();
        Map<String, String> map = new HashMap<>();
        map.put("operatorId", operatorId);
        map.put("goodsId", goodsId);
        int effectedNum = goodsDao.deleteGoodsById(map);
        if (effectedNum > 0) return RespResult.ok("删除成功!");
        return RespResult.fail("删除失败");
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
        List<Goods> goodsList = goodsDao.selectGoodsBySkuId(skuId);
        if (goodsList == null || goodsList.isEmpty()){
            String operatorId = AcContext.getStaffId();
            Map<String, String> map = new HashMap<>();
            map.put("operatorId", operatorId);
            map.put("skuId", skuId);
            goodsDao.deleteSkuById(map);
            return RespResult.ok("删除成功!");
        }
        return RespResult.fail("该SKU正在用于商品" + goodsList.get(0).getGoodsName() + "，不能删除");
    }

    /** SKU列表 */
	public List<Sku> querySkuList() {
	    return goodsDao.querySkuList();
	}

	public List<Goods> queryGoodsByIds(List<String> goodsIds) {
	    return goodsDao.queryGoodsByGoodsIds(goodsIds);
    }
}