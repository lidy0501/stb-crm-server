package cn.stb.stbcrmserver.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinanceOrderItem {
    private String orderId; //订单ID
    private String orderCode; //订单编号
    private int totalFee; //订单应付总金额
    private int goodsFee; // 该订单的成本
}
