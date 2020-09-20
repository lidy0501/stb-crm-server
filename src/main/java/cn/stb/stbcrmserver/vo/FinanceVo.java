package cn.stb.stbcrmserver.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinanceVo {
    private String staffCode; //员工代码
    private String staffName; //员工姓名
    private int gross; // 所有订单金额总和
    private int cost; // 所有订单的成本

    List<FinanceOrderItem> orderItems; // 订单集合
}
