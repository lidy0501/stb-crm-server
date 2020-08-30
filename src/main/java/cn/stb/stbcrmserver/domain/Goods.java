package cn.stb.stbcrmserver.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;


/**
 * 商品表bean
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Goods {
    private String goodsId;//商品ID
    private String goodsCode;//商品编码
    private String goodsName;//商品名称
    private int goodsPrice;//商品价格: 单位 分`
    private String skuId;//商品对应的skuId
    private String goodsState;//商品状态,0已删除,1未删除
    private String operatorId;//操作员
    private DateTime createTime;//创建时间
    private DateTime updateTime;//更新时间
    private String remark;//备注


}
