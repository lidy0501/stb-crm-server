package cn.stb.stbcrmserver.domain;

import cn.stb.stbcrmserver.context.AcContext;
import cn.stb.stbcrmserver.utils.UUIDUtil;
import cn.stb.stbcrmserver.vo.AddOrderReq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;


/**
 * 订单管理表bean
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private String orderId;//订单ID
    private String orderCode;//订单编码
    private String userId;//客户ID
    private String userName;//客户姓名
    private String company;//公司名称
    private String operatorId;//跟单人ID
    private String orderState;//订单状态0:未完成(默认) 1:已完成 9:已删除
    private String payType;//支付方式
    private int totalFee;//订单应付总金额，单位：分
    private int downPayFee;//已付金额，单位：分
    private int finalPayFee;//待付金额，单位：分
    private String deliveryTime;//交期
    private String deliveryNo;//物流单号
    private String payRecord;//付款记录
    private DateTime createTime;//创建时间
    private DateTime updateTime;//更新时间
    private String remark;//备注

    public static Order convert(AddOrderReq req) {
        return Order.builder()
                .orderId(UUIDUtil.getNumId())
                .orderCode(req.getOrderCode())
                .userId(req.getUserId())
                .userName(req.getUserName())
                .company(req.getCompany())
                .operatorId(AcContext.getStaffId())
                .orderState("0")
                .payType(req.getPayType())
                .totalFee(req.getTotalFee())
                .downPayFee(req.getDownPayFee())
                .finalPayFee(req.getFinalPayFee())
                .deliveryTime(req.getDeliveryTime())
                .deliveryNo(req.getDeliveryNo())
                .payRecord(req.getPayRecord())
                .remark(req.getRemark())
                .build();
    }
}
