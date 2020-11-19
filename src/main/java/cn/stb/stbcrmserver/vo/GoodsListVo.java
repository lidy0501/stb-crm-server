package cn.stb.stbcrmserver.vo;

import cn.stb.stbcrmserver.domain.Goods;
import cn.stb.stbcrmserver.domain.Sku;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsListVo {
	private String goodsId;//商品ID
	private String goodsCode;//商品编码
	private String goodsName;//商品名称
	private int goodsPrice;//商品价格: 单位 分`
	private String skuInfo;//商品对应的sku信息
	private String remark;//备注

	public static GoodsListVo convert(Goods goods) {
		return GoodsListVo.builder()
				.goodsId(goods.getGoodsId())
				.goodsCode(goods.getGoodsCode())
				.goodsName(goods.getGoodsName())
				.goodsPrice(goods.getGoodsPrice())
				.skuInfo("编码:" + goods.getSkuCode() + " 单位:" + goods.getSkuUnit() + " 花色:" + goods.getSkuColor())
				.remark(goods.getRemark())
				.build();
	}
}
