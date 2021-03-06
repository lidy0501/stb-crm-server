package cn.stb.stbcrmserver.domain;

import cn.stb.stbcrmserver.context.AcContext;
import cn.stb.stbcrmserver.vo.GoodsVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderGoods {
    private String orderId;//订单ID
    private String goodsId;//商品ID
    private int amount;//商品数量 默认0
    private String state;//状态 1有效 0无效
    private String operatorId;//操作员
    private DateTime createTime;//创建时间
    private DateTime updateTime;//更新时间

    public static OrderGoods convert(String orderId, GoodsVo goodsVo) {
        return OrderGoods.builder()
                .orderId(orderId)
                .goodsId(goodsVo.getGoodsId())
                .amount(goodsVo.getAmount())
                .state("1")
                .operatorId(AcContext.getStaffId())
                .build();
    }

}
