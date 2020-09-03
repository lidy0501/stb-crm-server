package cn.stb.stbcrmserver.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class OrderGoodsItem {
	private String orderId; // 订单id
	private String goodsName; // 商品名称
	private int amount; // 商品数量
	private String skuUnit;//SKU单位
	private String skuColor;//SKU花色
}
