package cn.stb.stbcrmserver.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 订单管理表bean
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private String OrderId;//订单ID
    private String UserId;//客户ID
    private String StaffId;//跟单人ID
    private String ProductName;//产品名称
    private String ProductSpe;//产品规格
    private int ProductNum;//产品数量
    private String OrderState;//订单状态0:未完成 1:已完成
    private String PayType;//支付方式
    private int TotalFee;//订单应付总金额
    private int DownPayFee;//首付款
    private int FinalPayFee;//尾款
    private String PayProgress;//付款进度
    private String DeliveryTime;//交期
    private String DeliveryNo;//物流单号
    private String PayRecord;//付款记录
    private String OrderDelete;//订单删除0:未删除 1:删除
    private Date CreateTime;//订单生成时间
    private Date UpdateTime;//更新时间，订单有变更时更新
    private String Remark;//备注




}
