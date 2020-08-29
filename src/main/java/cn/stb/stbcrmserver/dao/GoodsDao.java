package cn.stb.stbcrmserver.dao;

import cn.stb.stbcrmserver.domain.Goods;
import cn.stb.stbcrmserver.domain.Sku;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GoodsDao {

    List<Goods> queryAllGoods();

    Integer addGoods(Goods goods);

    Integer deleteGoodsById(String  goodsId);

    int addSku(Sku sku);

    int deleteSkuById(String skuId);

    int updateSku(Sku sku);

    Goods selectGoodsBySkuId(String skuId);
}
