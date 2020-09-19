package cn.stb.stbcrmserver.vo;

import cn.stb.stbcrmserver.domain.Goods;
import cn.stb.stbcrmserver.domain.Order;
import cn.stb.stbcrmserver.domain.OrderGoods;
import cn.stb.stbcrmserver.domain.Staff;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StaffFinanceVo {
    private String staffCode;//员工代码
    private String staffName;//员工姓名
    private String orderCode;//订单代码
    private int totalFee;//订单总金额
    private String goodsName;//商品名称
    private int amount;//商品个数
    private int staffFinance;//绩效
    private String operatorId;//员工ID

    public static StaffFinanceVo convert(Staff staff, Order order, int staffFinance, OrderGoodsItem orderGoodsItem){
        return StaffFinanceVo.builder()
                .staffCode(staff.getStaffCode())
                .staffName(staff.getStaffName())
                .orderCode(order.getOrderCode())
                .totalFee(order.getTotalFee())
                .goodsName(orderGoodsItem.getGoodsName())
                .amount(orderGoodsItem.getAmount())
                .operatorId(order.getOperatorId())
                .staffFinance(staffFinance)
                .build();
    }
}
