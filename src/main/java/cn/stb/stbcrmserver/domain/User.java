package cn.stb.stbcrmserver.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import java.sql.Date;


/**
 * 客户信息表bean
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String userId;//客户ID
    private String userCode;//客户编码
    private String userName;//客户姓名
    private String nation;//客户国家
    private String userPhone;//客户电话
    private String userTelephone;//客户固定电话
    private String post;// 公司职位
    private String company;//客户公司名称
    private String companyAddress;//客户公司地址
    private String companyWeb;//客户公司网址
    private String userEmail;//客户邮箱
    private String userType;//客户类型：0公共区域、1私有区域
    private String userState;//客户状态：0无效 1有效
    private String operatorId;//创建人
    private DateTime createTime;//创建时间
    private DateTime updateTime;//更新时间
    private String followTime;//创建时间
    private String subtotal;//跟进小计
    private String remark;//备注
    private String region;//客户所在地区

}
