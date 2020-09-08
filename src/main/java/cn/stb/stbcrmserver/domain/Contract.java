package cn.stb.stbcrmserver.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

/**
 * 合同表bean
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Contract {
    private String contractId;//合同ID
    private String contractCode;//合同编号
    private String companyA;//甲方公司
    private String companyB;//乙方公司
    private String orderId;//订单ID:前端显示订单编号
    private int orderPrice;//订单价格 单位  分
    private String signingDate;//合同签订日期
    private String deadline;//合同截止日期
    private String operatorId;//操作员
    private DateTime createTime;//创建时间
    private DateTime updateTime;//更新时间
    private String remark;//备注
}
