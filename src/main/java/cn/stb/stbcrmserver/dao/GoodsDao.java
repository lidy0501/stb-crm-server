package cn.stb.stbcrmserver.dao;

import cn.stb.stbcrmserver.domain.Goods;
import cn.stb.stbcrmserver.domain.OrderGoods;
import cn.stb.stbcrmserver.domain.Sku;
import cn.stb.stbcrmserver.vo.SelectGoodsVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface GoodsDao {

    List<Goods> queryAllGoods();

    Integer addGoods(Goods goods);

    int deleteGoodsById(Map<String, String>  map);

    int addSku(List<Sku> skuList);

    int deleteSkuById(Map<String, String> map);

    int updateSku(Sku sku);

    List<Goods> selectGoodsBySkuId(String skuId);

	List<Sku> querySkuList();

	Goods findGoodsByCode(String goodsCode);

	List<Sku> querySkuListByIds(List<String> skuIds);

	List<SelectGoodsVo> queryAllSelectGoodsVo();

	List<OrderGoods> queryOrderGoodsByGoodsId(String goodsId);

	List<Goods> queryGoodsByGoodsIds(List<String> goodsIds);
}
