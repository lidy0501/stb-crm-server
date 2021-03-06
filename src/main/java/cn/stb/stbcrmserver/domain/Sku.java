package cn.stb.stbcrmserver.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

/**
 * 商品SKU bean
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Sku {
    private String skuId;//SKU ID
    private String skuCode;//SKU编码
    private String skuUnit;//SKU单位
    private String skuColor;//SKU花色
    private String skuState;//SKU状态 1有效 0无效
    private String operatorId;//创建人
    private DateTime createTime;//创建时间
    private DateTime updateTime;//更新时间
    private String remark;//备注
}
