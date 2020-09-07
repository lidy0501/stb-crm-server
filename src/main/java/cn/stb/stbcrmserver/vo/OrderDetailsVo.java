package cn.stb.stbcrmserver.vo;

import cn.stb.stbcrmserver.domain.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class OrderDetailsVo {
	private String orderId;//订单ID
	private String orderCode;//订单编码
	private String userName;//客户姓名
	private String company;//公司名称
	private String orderState;//订单状态0:未完成(默认) 1:已完成 9:已删除
	private String payType;//支付方式
	private int totalFee;//订单应付总金额，单位：分
	private int downPayFee;//已付金额，单位：分
	private int finalPayFee;//待付金额，单位：分
	private String deliveryTime;//交期
	private String deliveryNo;//物流单号
	private String payRecord;//付款记录
	private String remark;//备注
	private List<OrderGoodsItem> goodsList;

	public static OrderDetailsVo convert(Order order, List<OrderGoodsItem> goodsItemList) {
		return OrderDetailsVo.builder()
				.orderId(order.getOrderId())
				.orderCode(order.getOrderCode())
				.userName(order.getUserName())
				.company(order.getCompany())
				.orderState(order.getOrderState())
				.payType(order.getPayType())
				.totalFee(order.getTotalFee())
				.downPayFee(order.getDownPayFee())
				.finalPayFee(order.getFinalPayFee())
				.deliveryTime(order.getDeliveryTime())
				.deliveryNo(order.getDeliveryNo())
				.payRecord(order.getPayRecord())
				.remark(order.getRemark())
				.goodsList(goodsItemList)
				.build();
	}

}
