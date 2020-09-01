package cn.stb.stbcrmserver.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderGoodsVo {
    private String goodsId;//商品ID
    private String goodsName;//商品名称
    private String goodsPrice;//商品价格
    private String skuUnit;//SKU单位
    private String skuColor;//SKU花色
}
