package cn.stb.stbcrmserver.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class GoodsVo {
	private String goodsId; // 商品id
	private int amount; // 商品数量
}
