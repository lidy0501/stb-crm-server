package cn.stb.stbcrmserver.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class AddOrderReq {
	private List<GoodsVo> goodsList; // 商品集合
 	private String orderCode; // 订单编码
	private String userId; // 客户ID
	private String userName; // 客户姓名
	private String company; // 公司名称
	private String deliveryNo;
	private int totalFee; // 名单总额 单位  分
	private int downPayFee; // 已付款
	private int finalPayFee; // 未付款
	private String deliveryTime; // 交期
	private String payType; // 付款方式
	private String payRecord; // 付款记录
	private String remark; // 备注
}
