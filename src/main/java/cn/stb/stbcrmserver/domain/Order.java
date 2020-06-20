package cn.stb.stbcrmserver.domain;

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
    private String userId;//客户ID
    private String operatorId;//跟单人ID
    private String productName;//产品名称
    private String productSpe;//产品规格
    private int productNum;//产品数量
    private String orderState;//订单状态0:未完成 1:已完成
    private String payType;//支付方式
    private int totalFee;//订单应付总金额
    private int downPayFee;//首付款
    private int finalPayFee;//尾款
    private String payProgress;//付款进度
    private String deliveryTime;//交期
    private String deliveryNo;//物流单号
    private String payRecord;//付款记录
    private String orderDelete;//订单删除0:未删除 1:删除
    private DateTime createTime;//创建时间
    private DateTime updateTime;//更新时间
    private String remark;//备注




}
