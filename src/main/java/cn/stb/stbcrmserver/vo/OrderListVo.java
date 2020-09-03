package cn.stb.stbcrmserver.vo;

import cn.stb.stbcrmserver.domain.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderListVo {
    private String orderId;//订单ID
    private String orderCode;//订单编号
    private String userName;//客户姓名
    private String company;//公司名称
    private String operatorName;//跟单人姓名
    private String orderState;//订单状态0:未完成 1:已完成 9:已删除
    private int totalFee;//订单应付总金额
    private List<OrderGoodsItem> goodsItemList;

    public static OrderListVo convert(Order order, String operatorName, List<OrderGoodsItem> goodsItemList) {
        return OrderListVo.builder()
                .orderId(order.getOrderId())
                .orderCode(order.getOrderCode())
                .userName(order.getUserName())
                .company(order.getCompany())
                .operatorName(operatorName)
                .orderState(order.getOrderState())
                .totalFee(order.getTotalFee())
                .goodsItemList(goodsItemList)
                .build();
    }
}
